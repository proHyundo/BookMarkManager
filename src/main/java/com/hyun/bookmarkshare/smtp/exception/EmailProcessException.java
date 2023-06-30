package com.hyun.bookmarkshare.smtp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailProcessException extends RuntimeException{

    private final EmailExceptionErrorCode emailExceptionErrorCode;
}
