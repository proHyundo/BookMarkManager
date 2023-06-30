package com.hyun.bookmarkshare.user.exceptions;

import com.hyun.bookmarkshare.exceptions.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionErrorCode implements CustomErrorCode {

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "Not Found User"),
    DELETE_USER_ERROR(HttpStatus.BAD_REQUEST, "Delete User Error"),
    INVALID_USER(HttpStatus.BAD_REQUEST, "Invalid User"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
