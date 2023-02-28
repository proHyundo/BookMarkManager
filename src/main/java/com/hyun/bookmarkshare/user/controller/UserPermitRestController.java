package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.LoginResponseEntity;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserPermitRestController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseEntity> loginRequest(@RequestBody @Valid LoginRequestDto loginRequestDto){
        User user = loginService.loginProcess(loginRequestDto);
        return LoginResponseEntity.toResponseEntity(user);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        // token repository에서 refresh Token에 해당하는 값을 삭제한다.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
