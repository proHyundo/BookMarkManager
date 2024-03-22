package com.hyun.bookmarkshare.social.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocialLoginException extends RuntimeException{

    private final SocialLoginErrorCode socialLoginErrorCode;
    private final String message;
}
