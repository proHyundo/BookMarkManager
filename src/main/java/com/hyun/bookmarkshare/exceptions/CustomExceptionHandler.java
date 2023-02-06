package com.hyun.bookmarkshare.exceptions;

import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import com.hyun.bookmarkshare.user.controller.dto.LoginErrorResponseEntity;
import com.hyun.bookmarkshare.user.exceptions.LoginInputValidateFailException;
import com.hyun.bookmarkshare.user.exceptions.LoginProcessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode.NOT_FOUND_USER;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(LoginInputValidateFailException.class)
    protected ResponseEntity<LoginErrorResponseEntity> handleLoginInputValidateFailException(LoginInputValidateFailException e){
        log.info("LoginInputValidateFailException getMessage() >> "+e.getMessage());
        log.info("LoginInputValidateFailException getLoginExceptionErrorCode() >> "+e.getLoginExceptionErrorCode());

        return LoginErrorResponseEntity.toResponseEntity(e.getLoginExceptionErrorCode());
    }

    @ExceptionHandler(LoginProcessException.class)
    protected ResponseEntity<LoginErrorResponseEntity> handleLoginProcessException(LoginProcessException e){
        return LoginErrorResponseEntity.toResponseEntity(NOT_FOUND_USER);
    }

    @ExceptionHandler(FolderRequestException.class)
    protected ResponseEntity<CustomErrorResponseEntity> handleFolderProcessException(FolderRequestException e){
        return CustomErrorResponseEntity.toResponseEntity(e.getFolderExceptionErrorCode());
    }

}
