package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.*;
import com.hyun.bookmarkshare.user.service.UserService;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import com.hyun.bookmarkshare.utils.ApiResponseWithCookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserPermitRestController {

    private final UserService userService;

    /**
     * 로그인 요청을 처리한다.
     * */
    @PostMapping("/api/v1/user/login")
    public ApiResponseWithCookie<UserLoginResponse> loginRequest(@RequestBody @Valid LoginRequestDto loginRequestDto){
        UserLoginResponse resultUser = userService.loginProcess(loginRequestDto.toServiceDto());
        return ApiResponseWithCookie.withCookieOf(HttpStatus.OK, "로그인 성공", resultUser, resultUser.getUserRefreshToken());
    }

    /**
     * 회원가입 요청을 처리한다.
     * */
    @PostMapping("/api/v1/user/signup")
    public ApiResponse<UserResponse> signUpRequest(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto){
        return ApiResponse.of(HttpStatus.OK, "회원가입 성공", userService.signUp(userSignUpRequestDto.toServiceDto()));
    }

    @PostMapping("/api/v1/user/refresh")
    public ApiResponse<String> refreshRequest(HttpServletRequest request){
        Optional<Cookie> refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("userRefreshToken"))
                .findFirst();
        String refreshToken = refreshTokenCookie.map(Cookie::getValue).orElseThrow(() -> {
            throw new IllegalArgumentException("RefreshToken 이 존재하지 않습니다.");
        });
        return ApiResponse.of(HttpStatus.OK, "로그인 연장 성공", userService.extendLoginState(refreshToken));
    }


    /**
     * 중복 이메일 검사 요청 처리.
     * @param map 중복 검사를 요청한 이메일을 담은 Map
     */
    @PostMapping("/api/v1/user/email/check")
    public ApiResponse<String> checkRegisteredEmailRequest(@RequestBody HashMap<String, String> map){
        boolean isDuplicate = userService.checkDuplicateEmail(map.get("userEmail"));
        HttpStatus status = isDuplicate ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        String message = isDuplicate ? "중복된 이메일입니다." : "사용 가능한 이메일입니다.";
        String data = isDuplicate ? "duplicate" : map.get("userEmail");
        return ApiResponse.of(status, message, data);
    }

}
