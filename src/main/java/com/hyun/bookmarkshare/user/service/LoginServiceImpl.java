package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.dao.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginRepository loginRepository;

    @Override
    public void loginProcess(LoginRequestDto loginRequestDto) {
        // 1. 문자열 검증
        loginInputValidation(loginRequestDto);

        // 2. DB 조회
        loginRepository.findByLoginRequestDto(loginRequestDto);

        // 3. 결과 반환
    }

}
