package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LoginInputValidateFailException;

import java.util.regex.Pattern;

public interface LoginService {

    /**
     * @implSpec Login Request Input data Validation & call DB Sql to find User with Id and Pwd.
     * @param loginRequestDto Login request Form data. Include Id, Pwd.
     * @return User Entity from DB SELECT Sql result.
     * */
    User loginProcess(LoginRequestDto loginRequestDto);


}
