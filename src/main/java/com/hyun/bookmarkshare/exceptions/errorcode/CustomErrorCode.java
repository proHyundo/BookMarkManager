package com.hyun.bookmarkshare.exceptions.errorcode;

import org.springframework.http.HttpStatus;

public interface CustomErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();
}
