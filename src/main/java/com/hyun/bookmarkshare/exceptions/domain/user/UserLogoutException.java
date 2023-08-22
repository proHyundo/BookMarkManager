package com.hyun.bookmarkshare.exceptions.domain.user;

import com.hyun.bookmarkshare.exceptions.errorcode.RefreshTokenErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLogoutException extends RuntimeException{

    private final RefreshTokenErrorCode userErrorCode;

}
