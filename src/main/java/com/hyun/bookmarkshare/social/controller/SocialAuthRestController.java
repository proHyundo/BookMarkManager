package com.hyun.bookmarkshare.social.controller;

import com.hyun.bookmarkshare.social.service.SocialLoginService;
import com.hyun.bookmarkshare.social.service.request.KakaoUser;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SocialAuthRestController {

    private final SocialLoginService socialLoginService;

//    @GetMapping("/api/v1/social/kakao/callback")
//    public ApiResponse<String> kakaoAuthCodeRedirectRequest(@NotNull @RequestParam HashMap<String, String> map){
//        String kakaoAuthCode = map.get("code");
//        throwExceptionWhenKakaoAuthCodeIsNotProvided(kakaoAuthCode);
//        return ApiResponse.ok(kakaoAuthCode);
//    }

    @PostMapping("/api/v1/social/kakao/login")
    public ApiResponse<UserLoginResponse> kakaoLoginRequest(@RequestBody HashMap<String, String> map){
        log.info("/api/v1/social/kakao/login 요청");
        String kakaoAuthCode = map.get("kakaoAuthCode");
        throwExceptionWhenKakaoAuthCodeIsNotProvided(kakaoAuthCode);
        KakaoUser kakaoUserInfo = socialLoginService.getKakaoUserInfo(socialLoginService.getKakaoAccessTokenBy(kakaoAuthCode));
        return ApiResponse.ok(socialLoginService.loginByKakao(kakaoUserInfo));
    }

    @PostMapping("/api/v1/social/kakao/signup")
    public ApiResponse<UserResponse> kakaoSignUpRequest(@RequestBody HashMap<String, String> map){
        log.info("/api/v1/social/kakao/signup 요청");
        String kakaoAuthCode = map.get("kakaoAuthCode");
        throwExceptionWhenKakaoAuthCodeIsNotProvided(kakaoAuthCode);
        KakaoUser kakaoUserInfo = socialLoginService.getKakaoUserInfo(socialLoginService.getKakaoAccessTokenBy(kakaoAuthCode));
        return ApiResponse.ok(socialLoginService.signUpByKakao(kakaoUserInfo));
    }

    private void throwExceptionWhenKakaoAuthCodeIsNotProvided(String kakaoAuthCode) {
        if(kakaoAuthCode == null || kakaoAuthCode.isEmpty()){
            throw new IllegalArgumentException("Failed to get authorization kakao code");
        }
    }

}
