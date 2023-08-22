package com.hyun.bookmarkshare.exceptions.domain.user;

import com.hyun.bookmarkshare.exceptions.errorcode.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginProcessException extends RuntimeException {

    private final UserErrorCode userErrorCode;
    private final String message;

}
