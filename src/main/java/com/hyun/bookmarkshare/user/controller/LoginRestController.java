package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.LoginResponseEntity;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginRestController {

    private final LoginService loginService;

    @PostMapping("/login/request")
    public ResponseEntity<LoginResponseEntity> loginRequest(@RequestBody LoginRequestDto loginRequestDto){
        User user = loginService.loginProcess(loginRequestDto);
        return LoginResponseEntity.toResponseEntity(user);
    }
}
