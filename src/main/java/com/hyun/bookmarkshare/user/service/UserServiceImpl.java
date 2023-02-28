package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LoginProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public User loginProcess(LoginRequestDto loginRequestDto) {

        /**
         * Depreciated
         * Add Validation annotation at LoginRequestDto
         * */
//         1. input validate
//        validator.emailMeter(loginRequestDto.getEmail());
//        validator.pwdMeter(loginRequestDto.getPwd());

        // 1. pwd encode
        try {
            loginRequestDto.setPwd(pwdEncoder.encode(loginRequestDto.getPwd()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 3. DB select & return User Entity
        User resultUser = userRepository.findByLoginRequestDto(loginRequestDto)
                .orElseThrow(() -> new LoginProcessException(LoginExceptionErrorCode.NOT_FOUND_USER));

        // jwt 라이브러리를 사용하여 access, refresh token 생성
        resultUser.setUSER_ACCESS_TOKEN(jwtTokenizer.createAccessToken(
                resultUser.getUSER_SEQ(),
                resultUser.getUSER_EMAIL(),
                List.of(resultUser.getUSER_ROLE()))
        );
        resultUser.setUSER_FRESH_TOKEN(jwtTokenizer.createRefreshToken(
                resultUser.getUSER_SEQ(),
                resultUser.getUSER_EMAIL(),
                List.of(resultUser.getUSER_ROLE()))
        );

        return resultUser;
    }

    @Override
    public void logoutProcess(String token, String refreshToken) {
        // db에서 refreshToken 삭제

    }

}
