package com.hyun.bookmarkshare.social.exception;

import com.hyun.bookmarkshare.exceptions.errorcode.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SocialLoginErrorCode implements CustomErrorCode {

    KAKAO_LOGIN_NO_EMAIL(HttpStatus.ACCEPTED, "카카오 로그인에 실패했습니다. 이메일을 제공하지 않았습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
