package com.hyun.bookmarkshare.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutProcessException extends RuntimeException{

    private final LogoutExceptionErrorCode logoutExceptionErrorCode;

}
