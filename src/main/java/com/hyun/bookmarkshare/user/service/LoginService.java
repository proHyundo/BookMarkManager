package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;

public interface LoginService {
    void loginProcess(LoginRequestDto loginRequestDto);

    default boolean loginInputValidation(LoginRequestDto loginRequestDto) {
        String[] arr = loginRequestDto.getEmail().split("@");
        if(arr[0].length()<=1 || arr[0].length()>15){
            // 길이 조건 불만족

        }

        return false;
    }
}
