package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.*;
import com.hyun.bookmarkshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

//signup", "/login", "/refresh/login-state"
@RestController
@RequiredArgsConstructor
public class UserPermitRestController {

    private final UserService userService;

    /**
     * 로그인 요청을 처리한다.
     * */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseEntity> loginRequest(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return LoginResponseEntity.toResponseEntity(userService.loginProcess(loginRequestDto));
    }

    /**
     * 회원가입 요청을 처리한다.
     * */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseEntity> signUpRequest(@RequestBody @Valid SignUpRequestDto signUpRequestDto){
        return SignUpResponseEntity.toResponseEntity(userService.signUp(signUpRequestDto));
    }

//    /**
//     * RefreshToken 을 이용하여 AccessToken 을 재발급한다.
//     * @param map RefreshToken 을 담은 Map
//     * @return 재발급된 AccessToken 과 RefreshToken 을 담은 ResponseEntity
//    */
//    @PostMapping("/refresh/login-state")
//    public ResponseEntity<RefreshResponseEntity> refreshRequest(@RequestBody HashMap<String, String> map) {
//        // TODO: Http body에 담긴 refreshToken이 아닌, Http header에 Http-only 쿠키에 저장된 refreshToken을 사용하도록 변경해야 함.
//        return RefreshResponseEntity.toResponseEntity(userService.extendLoginState(map.get("refreshToken")));
//    }

    @PostMapping("/refresh/login-state")
    public ResponseEntity<RefreshResponseEntity> refreshRequest(HttpServletRequest request) {
        // Cookie 에 담긴 refreshToken 을 가져온다.
        Optional<Cookie> refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("userRefreshToken"))
                .findFirst();

        String refreshToken = refreshTokenCookie.map(Cookie::getValue).orElseThrow(() -> {
            throw new IllegalArgumentException("RefreshToken 이 존재하지 않습니다.");
        });
        return RefreshResponseEntity.toResponseEntity(userService.extendLoginState(refreshToken));
    }

    /**
     * 중복 이메일 검사 요청 처리.
     * @param map 중복 검사를 요청한 이메일을 담은 Map
     */
    @PostMapping("/signup/check/duplicate")
    public ResponseEntity checkEmailRequest(@RequestBody HashMap<String, String> map) {
        boolean isDuplicate = userService.checkDuplicateEmail(map.get("userEmail"));
        HttpStatus status = isDuplicate ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        String bodyMessage = isDuplicate ? "중복된 이메일 입니다." : "사용 가능한 이메일 입니다.";
        return ResponseEntity.status(status).body(bodyMessage);
    }

}
