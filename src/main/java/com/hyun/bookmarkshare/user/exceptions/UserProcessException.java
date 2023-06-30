package com.hyun.bookmarkshare.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProcessException extends RuntimeException{

    private final UserExceptionErrorCode userExceptionErrorCode;

}
