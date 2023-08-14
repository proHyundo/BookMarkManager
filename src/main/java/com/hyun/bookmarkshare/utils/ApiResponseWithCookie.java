package com.hyun.bookmarkshare.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import java.time.LocalDateTime;

@Getter
public class ApiResponseWithCookie<T> {


    private final LocalDateTime timestamp = LocalDateTime.now();
    private int code;
    private HttpStatus status;
    private String message;
    private T data;
    private ResponseCookie cookie;

    public ApiResponseWithCookie(HttpStatus status, String message, T data, ResponseCookie cookie) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
        this.cookie = cookie;
    }

    public static <T> ApiResponseWithCookie<T> withCookieOf(HttpStatus status, String message, T data, String refreshToken) {
        ResponseCookie refreshTokenCookie = ResponseCookie.from("userRefreshToken", refreshToken)
                .domain("localhost")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60 * 24 * 3) // 3일
                .build();
        return new ApiResponseWithCookie<>(status, message, data, refreshTokenCookie);
    }
}
