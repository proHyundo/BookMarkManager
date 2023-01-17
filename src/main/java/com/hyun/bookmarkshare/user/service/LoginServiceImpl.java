package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.dao.LoginRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LoginProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginRepository loginRepository;
    private final InputValidator validator;
    private final PwdEncoder pwdEncoder;


    @Override
    public User loginProcess(LoginRequestDto loginRequestDto) {
        // 1. input validate
        validator.emailMeter(loginRequestDto.getEmail());
        validator.pwdMeter(loginRequestDto.getPwd());

        // 2. pwd encode
        try {
            loginRequestDto.setPwd(pwdEncoder.encode(loginRequestDto.getPwd()));
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
        }

        // 3. DB select & return User Entity
        return loginRepository.findByLoginRequestDto(loginRequestDto)
                .orElseThrow(()-> new LoginProcessException(LoginExceptionErrorCode.NOT_FOUND_USER));
    }

}
