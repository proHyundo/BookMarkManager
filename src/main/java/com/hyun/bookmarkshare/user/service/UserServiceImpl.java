package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LoginProcessException;
import com.hyun.bookmarkshare.user.exceptions.LogoutExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LogoutProcessException;
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
        int insertedRows = userRepository.saveUserRefreshToken(resultUser.getUSER_SEQ(), resultUser.getUSER_REFRESH_TOKEN());
        if(insertedRows != 1){
            throw new LoginProcessException(LoginExceptionErrorCode.INSERT_TOKEN_ERROR);
        }

        return resultUser;
    }

    @Transactional
    @Override
    public void logoutProcess(String refreshToken) {
        // db에서 refreshToken 존재 여부 확인
        Long userSeq = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> {
            throw new LogoutProcessException(LogoutExceptionErrorCode.NO_SUCH_REFRESH_TOKEN);
        });

        int deletedRows = userRepository.deleteRefreshTokenByUserSeq(userSeq);
        if(deletedRows != 1){
            throw new LogoutProcessException(LogoutExceptionErrorCode.DB_RESULT_WRONG);
        }
    }

    @Override
    public void findRefreshToken(String refreshToken) {

    }


}
