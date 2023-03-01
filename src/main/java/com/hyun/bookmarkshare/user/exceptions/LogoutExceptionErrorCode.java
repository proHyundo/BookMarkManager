package com.hyun.bookmarkshare.user.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum LogoutExceptionErrorCode {

    /* 404 NOTFOUND */
    NO_SUCH_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "Bad Refresh Token"),

    /* 409 CONFLICT*/
    DB_RESULT_WRONG(HttpStatus.CONFLICT, "DB Result Wrong"),
    LOGOUT_PROCESS_ERROR(HttpStatus.CONFLICT, "Logout Process Error");

    private final HttpStatus httpStatus;
    private final String message;
}
