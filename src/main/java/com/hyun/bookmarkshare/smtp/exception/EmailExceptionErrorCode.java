package com.hyun.bookmarkshare.smtp.exception;

import com.hyun.bookmarkshare.exceptions.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmailExceptionErrorCode implements CustomErrorCode {

    // 403 Forbidden : 서버가 요청을 이해했지만 요청자가 권한이 없어 승인을 거부.
    EMAIL_NOT_VALID(HttpStatus.FORBIDDEN, "Email Not Valid"),
    // 404 Not Found : 요청한 자원을 찾을 수 없음.
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "Email Not Found"),
    // 409 Conflict : 요청이 현재 서버의 상태와 충돌.
    ALREADY_USER_EXIST(HttpStatus.CONFLICT, "Email Already Exist"),
    ;

    private final HttpStatus httpStatus;
    private final String message;


}
