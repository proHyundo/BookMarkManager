package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import com.hyun.bookmarkshare.smtp.entity.EmailEntity;
import com.hyun.bookmarkshare.smtp.exception.EmailExceptionErrorCode;
import com.hyun.bookmarkshare.smtp.exception.EmailProcessException;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import com.hyun.bookmarkshare.user.exceptions.*;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PwdEncoder pwdEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final EmailRepository emailRepository;

    @Transactional
    @Override
    public User signUp(SignUpRequestDto signUpRequestDto) {

        // 1. check if email is not valid
        EmailEntity targetEmail = emailRepository.findByEmail(signUpRequestDto.getUserEmail())
                .orElseThrow(() -> new EmailProcessException(EmailExceptionErrorCode.EMAIL_NOT_FOUND));
        if(targetEmail.getEmailValidFlag() != 1){
            throw new EmailProcessException(EmailExceptionErrorCode.EMAIL_NOT_VALID);
        }

        // 2. encode pwd
        try {
            String encodedPwd = pwdEncoder.encode(signUpRequestDto.getUserPwd());
            signUpRequestDto.setUserPwd(encodedPwd);
        } catch (NoSuchAlgorithmException e) {
            log.warn("[오류][UserServiceImpl.signUp().encodePwd]"+e.getMessage());
            throw new RuntimeException("Failed to encode password", e);
        }

        // 3. save user
        int resultRows = userRepository.saveBySignUpRequestDto(signUpRequestDto);
        if(resultRows != 1){
            throw new LoginProcessException(LoginExceptionErrorCode.INSERT_TOKEN_ERROR);
        }

        // 4. delete email validation
        emailRepository.deleteByEmail(signUpRequestDto.getUserEmail());

        // 5. return signUp success User Entity
        return userRepository.findByUserId(signUpRequestDto.getUserId())
                .orElseThrow(() -> new LoginProcessException(LoginExceptionErrorCode.NOT_FOUND_USER));
    }

    @Override
    public boolean checkDuplicateEmail(String userEmail) {
        // check if user already exist
        if(userRepository.countByUserEmail(userEmail) > 0){
            throw new LoginProcessException(LoginExceptionErrorCode.ALREADY_USER_EXIST);
        }
        return false;
    }

    @Transactional
    @Override
    public User loginProcess(LoginServiceRequestDto loginRequestDto) {

        // 0. encode pwd & set encoded pwd to loginRequestDto
        // -> will depreciate as switch to security pwd encode
        try {
            loginRequestDto.setPwd(pwdEncoder.encode(loginRequestDto.getPwd()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 1. DB select & return User Entity
        User resultUser = userRepository.findByLoginServiceRequestDto(loginRequestDto)
                .orElseThrow(() -> new LoginProcessException(LoginExceptionErrorCode.NOT_FOUND_USER));

        // 2. Generate access and refresh tokens by JWT library
        List<String> userRoles = List.of(resultUser.getUserRole());
        String accessToken = jwtTokenizer.createAccessToken(resultUser.getUserId(), resultUser.getUserEmail(), userRoles);
        String refreshToken = jwtTokenizer.createRefreshToken(resultUser.getUserId(), resultUser.getUserEmail(), userRoles);

        // 3. Set tokens to User Entity
        resultUser.setUserAccessToken(accessToken);
        resultUser.setUserRefreshToken(refreshToken);

        // 4. Save Refresh token to DB Whitelist
        int resultRows = userRepository.saveUserRefreshToken(resultUser.getUserId(), resultUser.getUserRefreshToken());
        if(resultRows != 1) throw new LoginProcessException(LoginExceptionErrorCode.INSERT_TOKEN_ERROR);

        return resultUser;
    }

    @Transactional
    @Override
    public void logoutProcess(String refreshToken) {
        // DB 에서 RefreshToken 존재 확인
        UserRefreshToken userRefreshToken = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> {throw new LogoutProcessException(LogoutExceptionErrorCode.NO_SUCH_REFRESH_TOKEN);});

        int deletedRows = userRepository.deleteRefreshTokenByUserId(userRefreshToken.getUserId());
        if(deletedRows != 1) throw new LogoutProcessException(LogoutExceptionErrorCode.DB_RESULT_WRONG);
    }

    @Override
    public String extendLoginState(String refreshToken) {
        // 전달받은 RefreshToken 이 DB에 저장되어 있는지 확인. 존재하지 않다면 에러 & login page 로 리다이렉트
        userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));

        // 토큰이 존재한다면, refresh token 을 jwtTokenizer 에게 전달하여 토큰 검증 + claims 를 반환받음. 검증 실패 시 에러 + login page 로 리다이렉트.
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        // 검증에 성공하였다면, 반환받은 claims 에서 사용자 식별번호, 권한리스트, 이메일을 꺼냄. JwtTokenizer 에서 userId 라고 key 값을 정했음.
        Long userId = claims.get("userId", Long.class);
        List roles = claims.get("roles", List.class);
        String email = claims.getSubject();

        // 꺼내받은 식별번호로 사용자가 존재하는지 조회.
        userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 사용자 식별번호, 권한, 이메일로 새로운 액세스 토큰을 생성.
        // 생성한 액세스 토큰을 사용자에게 응답.
        return jwtTokenizer.createAccessToken(userId, email, roles);
    }

    @Override
    public User getUserInfo(String token) {
        return userRepository.findByUserId(jwtTokenizer.getUserIdFromToken(token)).orElseThrow(
                () -> new UserProcessException(UserExceptionErrorCode.NOT_FOUND_USER)
        );
    }

    @Override
    public User signOut(String token) {
        Long userIdFromToken = jwtTokenizer.getUserIdFromToken(token);
        int resultRows = userRepository.deleteByUserId(userIdFromToken);
        if(resultRows != 1){
            throw new UserProcessException(UserExceptionErrorCode.DELETE_USER_ERROR);
        }
        return User.builder().userId(userIdFromToken).userState("e").build();
    }


}
