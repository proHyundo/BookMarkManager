package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.LoginResponseEntity;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.SignUpResponseEntity;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserPermitRestController {

    //signup", "/login", "/users/refresh
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseEntity> loginRequest(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return LoginResponseEntity.toResponseEntity(userService.loginProcess(loginRequestDto));
    }


    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseEntity> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        return SignUpResponseEntity.toResponseEntity(userService.signUp(signUpRequestDto));
    }

    @PostMapping("/extension/login")
    public ResponseEntity refreshTokenRequest(){
        return null;
    }


}
