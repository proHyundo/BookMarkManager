package com.hyun.bookmarkshare.exceptions.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements CustomErrorCode {

    // (1) 로그인 요청 시, 아이디 또는 비밀번호가 잘못된 경우
    // (2) 로그인 요청 시, 요청 시도가 n 번 초과된 경우
    // (3) 로그아웃 요청 시, 이미 로그아웃된 경우
    // (4) 회원가입 요청 시, 이메일 인증발송 횟수를 n 번 초과한 경우
    // (5) 회원가입 요청 시, 이미 존재하는 사용자 Email 인 경우
    // (6) 특정 요청 시, 존재하지 않는 또는 회원탈퇴한 사용자인 경우
    // (7) 특정 요청 시, 요청한 사용자가 해당 작업을 수행할 권한이 없는 경우

    // login
    USER_LOGIN_INVALID_INPUT(HttpStatus.UNAUTHORIZED, "User invalid input"),
    USER_LOGIN_WRONG_ID_OR_PWD(HttpStatus.UNAUTHORIZED, "User wrong id or pwd"),
    USER_LOGIN_OVER_ATTEMPT(HttpStatus.TOO_MANY_REQUESTS, "User over attempt"),

    // logout
    USER_LOGOUT_ALREADY_LOGOUT(HttpStatus.UNAUTHORIZED, "User already logout"),

    // sign in
    USER_SIGNIN_INVALID_INPUT(HttpStatus.UNAUTHORIZED, "User invalid input"),
    USER_SIGNIN_ALREADY_EXIST(HttpStatus.UNAUTHORIZED, "User duplicated"),

    // common
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "User not found"),
    USER_NO_AUTH(HttpStatus.BAD_REQUEST, "User no auth to access"),

    // DB
    USER_DB_RESULT_WRONG(HttpStatus.INTERNAL_SERVER_ERROR, "User DB affected not intend"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
