package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.user.controller.dto.LoginRefreshResponseDto;
import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LoginProcessException;
import com.hyun.bookmarkshare.user.exceptions.LogoutExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LogoutProcessException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PwdEncoder pwdEncoder;
    private final JwtTokenizer jwtTokenizer;

    @Override
    public User signUp(SignUpRequestDto signUpRequestDto) {
        return null;
    }

    @Transactional
    @Override
    public User loginProcess(LoginRequestDto loginRequestDto) {

        /**
         *  will depreciate as switch to security pwd encode
         *  */
        try {
            loginRequestDto.setPwd(pwdEncoder.encode(loginRequestDto.getPwd()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 1. DB select & return User Entity
        User resultUser = userRepository.findByLoginRequestDto(loginRequestDto)
                .orElseThrow(() -> new LoginProcessException(LoginExceptionErrorCode.NOT_FOUND_USER));

        // 2. jwt 라이브러리를 사용하여 access, refresh token 생성
        resultUser.setUSER_ACCESS_TOKEN(jwtTokenizer.createAccessToken(
                resultUser.getUSER_SEQ(),
                resultUser.getUSER_EMAIL(),
                List.of(resultUser.getUSER_ROLE()))
        );
        resultUser.setUSER_REFRESH_TOKEN(jwtTokenizer.createRefreshToken(
                resultUser.getUSER_SEQ(),
                resultUser.getUSER_EMAIL(),
                List.of(resultUser.getUSER_ROLE()))
        );

        // 3. refresh token 저장
        int insertedRows = userRepository.saveUserRefreshToken(resultUser.getUSER_SEQ(),
                                                                resultUser.getUSER_REFRESH_TOKEN());
        if(insertedRows != 1){
            throw new LoginProcessException(LoginExceptionErrorCode.INSERT_TOKEN_ERROR);
        }

        return resultUser;
    }

    @Transactional
    @Override
    public void logoutProcess(String refreshToken) {
        // db에서 refreshToken 존재 여부 확인
        UserRefreshToken userRefreshToken = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> {
            throw new LogoutProcessException(LogoutExceptionErrorCode.NO_SUCH_REFRESH_TOKEN);
        });

        int deletedRows = userRepository.deleteRefreshTokenByUserSeq(userRefreshToken.getUSER_SEQ());
        if(deletedRows != 1){
            throw new LogoutProcessException(LogoutExceptionErrorCode.DB_RESULT_WRONG);
        }
    }

    @Override
    public LoginRefreshResponseDto extendLoginState(String refreshToken) {
        // db에 전달받은 refresh token 이 저장되어 있는지 확인. 존재하지 않다면 에러 + login page 로 리다이렉트
        UserRefreshToken userRefreshToken = userRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new IllegalArgumentException("Refresh token not found in Database")
        );

        // 토큰이 존재한다면, refresh token 을 jwtTokenizer 에게 전달하여 토큰 검증 + claims 를 반환받음.
        // 검증 실패 시 에러 + login page 로 리다이렉트.
        Claims claims = jwtTokenizer.parseRefreshToken(userRefreshToken.getREFRESH_TOKEN());
        System.out.println("리프래시 토큰 검증 성공");
        // 검증에 성공하였다면, 반환받은 claims 에서 사용자 식별번호를 꺼냄. JwtTokenizer 에서 userId 라고 key 값을 정했음.
        Long userId = Long.valueOf((Integer)claims.get("userId"));

        // 꺼내받은 식별번호로 사용자가 존재하는지 조회.
        User resultUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("Member not found")
        );

        // claims 에서 권한, 이메일을 꺼냄.
        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        // 사용자 식별번호, 권한, 이메일로 새로운 액세스 토큰을 생성.
        String accessToken = jwtTokenizer.createAccessToken(userId, email, roles);

        // 생성한 액세스 토큰을 사용자에게 응답.
        return LoginRefreshResponseDto.builder()
                .userId(userId)
                .userRole(roles)
                .userEmail(email)
                .userAccessToken(accessToken)
                .build();
    }


}
