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

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseEntity> loginRequest(@RequestBody @Valid LoginRequestDto loginRequestDto){
        User user = userService.loginProcess(loginRequestDto);
        return LoginResponseEntity.toResponseEntity(user);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logoutRequest(@RequestHeader("Authorization") String token, @RequestBody String refreshToken) {
        // token repository 에서 refresh Token 에 해당하는 값을 삭제한다.
        userService.logoutProcess(token, refreshToken);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseEntity> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        return SignUpResponseEntity.toResponseEntity(userService.signUp(signUpRequestDto));
    }

    @PostMapping("/refresh/token")
    public ResponseEntity refreshTokenRequest(){
        return null;
    }


}
