package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.user.service.UserService;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.user.service.response.UserSignoutResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/v1/user/info")
    public ApiResponse<UserResponse> getUserInfoRequest(@AuthenticationPrincipal LoginInfoDto loginInfoDto){
        return ApiResponse.of(HttpStatus.OK, userService.getUserInfo(loginInfoDto.getUserId()));
    }

    @DeleteMapping("/api/v1/user/sign/out")
    public ApiResponse<UserSignoutResponse> signOutRequest(@RequestHeader("Authorization") String accessToken, @RequestBody String userEmail){
        return ApiResponse.of(HttpStatus.OK, "회원탈퇴 성공", userService.signOut(accessToken, userEmail));
    }

}
