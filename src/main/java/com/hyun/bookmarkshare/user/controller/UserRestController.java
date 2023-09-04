package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.user.service.UserService;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.user.service.response.UserSignoutResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@Slf4j
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/v1/user/info")
    public ApiResponse<UserResponse> getUserInfoRequest(@RequestHeader(value = "Authorization") String accessToken){
        return ApiResponse.of(HttpStatus.OK, userService.getUserInfo(accessToken));
    }

    @GetMapping("/api/test/user/info")
    public ApiResponse<String> getUserInfoWithPrinciple(Principal principal){
        log.info("principal.getName() : {}", principal.getName());
        log.info("SecurityContextHolder.getContext().getAuthentication().getName() : {}", SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("SecurityContextHolder.getContext().getAuthentication().getPrincipal() : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        LoginInfoDto loginInfoDto = (LoginInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("loginInfoDto.toString() : {}", loginInfoDto.toString());
        return ApiResponse.of(HttpStatus.OK, principal.toString());
    }

    @DeleteMapping("/api/v1/user/sign/out")
    public ApiResponse<UserSignoutResponse> signOutRequest(@RequestHeader("Authorization") String accessToken, @RequestBody String userEmail){
        return ApiResponse.of(HttpStatus.OK, "회원탈퇴 성공", userService.signOut(accessToken, userEmail));
    }

}
