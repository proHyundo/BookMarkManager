package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.LoginResponseEntity;
import com.hyun.bookmarkshare.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginRestController {

    private final LoginService loginService;

    @PostMapping("/login/request")
    public ResponseEntity<LoginResponseEntity> loginRequest(LoginRequestDto loginRequestDto){
        System.out.println("loginRequestDto.getEmail() = " + loginRequestDto.getEmail());
        loginService.loginProcess(loginRequestDto);
        return null;
    }
}
