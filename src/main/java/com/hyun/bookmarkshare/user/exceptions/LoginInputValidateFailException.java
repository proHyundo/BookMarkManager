package com.hyun.bookmarkshare.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginInputValidateFailException extends RuntimeException{

    private final LoginExceptionErrorCode loginExceptionErrorCode;
    private final String message;

}
