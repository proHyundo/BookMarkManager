package com.hyun.bookmarkshare.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum LoginExceptionErrorCode {

    /* 401 UNAUTHORIZED Login Input */
    BAD_INPUT_LENGTH(HttpStatus.UNAUTHORIZED, "Bad Input Length"),
    BAD_INPUT_EMAIL(HttpStatus.UNAUTHORIZED, "Bad Input Email"),
    BAD_INPUT_PWD(HttpStatus.UNAUTHORIZED, "Bad Input Pwd"),
    INVALID_EMAIL(HttpStatus.UNAUTHORIZED, "Email is not Valid"),

    /* 404 NOTFOUND */
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "Wrong Id or Pwd"),
    ALREADY_EXIST_USER(HttpStatus.UNAUTHORIZED, "Already Exist User"),

    /* 409 CONFLICT*/
    INSERT_TOKEN_ERROR(HttpStatus.CONFLICT, "Bad Token"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
