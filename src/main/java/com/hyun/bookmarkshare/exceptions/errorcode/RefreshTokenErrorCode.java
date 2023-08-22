package com.hyun.bookmarkshare.exceptions.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RefreshTokenErrorCode implements CustomErrorCode{

    // common
    RT_NOT_FOUND(HttpStatus.NOT_FOUND, "Refresh token not found"),

    // db
    RT_DB_RESULT_WRONG(HttpStatus.CONFLICT, "Refresh token db result wrong"),

    ;
    private final HttpStatus httpStatus;
    private final String message;
}
