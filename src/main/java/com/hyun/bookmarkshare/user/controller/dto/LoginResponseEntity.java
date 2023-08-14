package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final Long userId;
//    private final String userEmail;
    private final String userRole;
    private final String userAccessToken;
    private final String userRefreshToken;

    public static ResponseEntity<LoginResponseEntity> toResponseEntity(User paramUser){
        ResponseCookie refreshTokenCookie = ResponseCookie.from("userRefreshToken", paramUser.getUserRefreshToken())
                .domain("localhost")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60 * 24 * 3) // 3일
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(LoginResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("로그인 성공")
                        .userId(paramUser.getUserId())
//                        .userEmail(paramUser.getUSER_EMAIL())
                        .userRole(paramUser.getUserRole())
                        .userAccessToken(paramUser.getUserAccessToken())
                        .build());
    }



}
