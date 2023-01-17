package com.hyun.bookmarkshare.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginProcessException extends RuntimeException {

    private final LoginExceptionErrorCode loginExceptionErrorCode;

}
