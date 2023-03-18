package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.LogoutResponseEntity;
import com.hyun.bookmarkshare.user.controller.dto.RefreshResponseEntity;
import com.hyun.bookmarkshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @DeleteMapping("/logout")
    public ResponseEntity<LogoutResponseEntity> logoutRequest(@RequestHeader("Authorization") String token,
                                                              @RequestBody @NotEmpty String refreshToken,
                                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return LogoutResponseEntity.toResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // token repository 에서 refresh Token 에 해당하는 값을 삭제한다.
        userService.logoutProcess(refreshToken);
        return LogoutResponseEntity.toResponseEntity(HttpStatus.OK);
    }




}
