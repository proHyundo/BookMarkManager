package com.hyun.bookmarkshare.social.exception;

import com.hyun.bookmarkshare.exceptions.domain.user.LoginInputValidateFailException;
import com.hyun.bookmarkshare.utils.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SocialLoginExceptionHandler {

    @ExceptionHandler(SocialLoginException.class)
    protected ApiErrorResponse handleSocialLoginException(SocialLoginException e){
        log.info("SocialLoginException getMessage() >> "+e.getMessage());
        log.info("SocialLoginException getSocialLoginErrorCode() >> "+e.getSocialLoginErrorCode());
        return ApiErrorResponse.of(e.getSocialLoginErrorCode());
    }
}
