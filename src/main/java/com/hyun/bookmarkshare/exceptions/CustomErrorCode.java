package com.hyun.bookmarkshare.exceptions;

import org.springframework.http.HttpStatus;

public interface CustomErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();
}
