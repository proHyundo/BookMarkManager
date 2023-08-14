package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.UserResponseEntity;
import com.hyun.bookmarkshare.user.service.UserService;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/v1/user/info")
    public ApiResponse<UserResponse> getUserInfoRequest(@RequestHeader(value = "Authorization") String accessToken){
        return ApiResponse.of(HttpStatus.OK, userService.getUserInfo(accessToken));
    }

    @DeleteMapping("/api/v1/user/sign/out")
    public ApiResponse<UserResponse> signOutRequest(@RequestHeader("Authorization") String accessToken, @RequestBody String userEmail){
        return ApiResponse.of(HttpStatus.OK, "회원탈퇴 성공", userService.signOut(accessToken, userEmail));
    }

}
