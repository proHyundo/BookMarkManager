package com.hyun.bookmarkshare.exceptions;

import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import com.hyun.bookmarkshare.smtp.exception.EmailProcessException;
import com.hyun.bookmarkshare.exceptions.domain.user.LoginInputValidateFailException;
import com.hyun.bookmarkshare.exceptions.domain.user.LoginProcessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    // Deprecated since 2023-04-25 : InputValidate not used. Because of using @Valid
    @ExceptionHandler(LoginInputValidateFailException.class)
    protected ResponseEntity<CustomErrorResponseEntity> handleLoginInputValidateFailException(LoginInputValidateFailException e){
        log.info("LoginInputValidateFailException getMessage() >> "+e.getMessage());
        log.info("LoginInputValidateFailException getLoginExceptionErrorCode() >> "+e.getUserErrorCode());
        return CustomErrorResponseEntity.toResponseEntity(e.getUserErrorCode());
    }

    @ExceptionHandler(LoginProcessException.class)
    protected ResponseEntity<CustomErrorResponseEntity> handleLoginProcessException(LoginProcessException e){
        return CustomErrorResponseEntity.toResponseEntity(e.getUserErrorCode());
    }

    @ExceptionHandler(FolderRequestException.class)
    protected ResponseEntity<CustomErrorResponseEntity> handleFolderProcessException(FolderRequestException e){
        log.info("FolderRequestException getMessage() >> "+e.getMessage());
        log.info("FolderRequestException getFolderExceptionErrorCode() >> "+e.getFolderExceptionErrorCode());
        return CustomErrorResponseEntity.toResponseEntity(e.getFolderExceptionErrorCode());
    }

    @ExceptionHandler(EmailProcessException.class)
    protected ResponseEntity<CustomErrorResponseEntity> handleEmailProcessException(EmailProcessException e){
        log.info("EmailRequestException getMessage() >> "+e.getMessage());
        log.info("EmailRequestException getEmailExceptionErrorCode() >> "+e.getEmailExceptionErrorCode());
        return CustomErrorResponseEntity.toResponseEntity(e.getEmailExceptionErrorCode());
    }

}
