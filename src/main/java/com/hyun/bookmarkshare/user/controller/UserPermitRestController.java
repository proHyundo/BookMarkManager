package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.*;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

//signup", "/login", "/refresh/login-state"
@RestController
@RequiredArgsConstructor
public class UserPermitRestController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseEntity> loginRequest(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return LoginResponseEntity.toResponseEntity(userService.loginProcess(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseEntity> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        return SignUpResponseEntity.toResponseEntity(userService.signUp(signUpRequestDto));
    }

    /*
    1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다.
    2. RefreshToken이 유효한지 체크한다.
    3. AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
     */
    @PostMapping("/refresh/login-state")
    public ResponseEntity<RefreshResponseEntity> refreshRequest(@RequestBody HashMap<String, String> map) {
        return RefreshResponseEntity.toResponseEntity(userService.extendLoginState(map.get("refreshToken")));
    }


}
