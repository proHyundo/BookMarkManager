package com.hyun.bookmarkshare.exceptions;

import com.hyun.bookmarkshare.exceptions.domain.manage.BookmarkException;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import com.hyun.bookmarkshare.smtp.exception.EmailProcessException;
import com.hyun.bookmarkshare.exceptions.domain.user.LoginInputValidateFailException;
import com.hyun.bookmarkshare.exceptions.domain.user.UserLoginException;
import com.hyun.bookmarkshare.utils.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    // Deprecated since 2023-04-25 : InputValidate not used. Because of using @Valid
    @ExceptionHandler(LoginInputValidateFailException.class)
    protected ApiErrorResponse handleLoginInputValidateFailException(LoginInputValidateFailException e){
        log.info("LoginInputValidateFailException getMessage() >> "+e.getMessage());
        log.info("LoginInputValidateFailException getLoginExceptionErrorCode() >> "+e.getUserErrorCode());
        return ApiErrorResponse.of(e.getUserErrorCode());
    }

    @ExceptionHandler(UserLoginException.class)
    protected ApiErrorResponse handleLoginProcessException(UserLoginException e){
        return ApiErrorResponse.of(e.getUserErrorCode());
    }

    @ExceptionHandler(FolderRequestException.class)
    protected ApiErrorResponse handleFolderProcessException(FolderRequestException e){
        log.info("FolderRequestException getMessage() >> "+e.getMessage());
        log.info("FolderRequestException getFolderExceptionErrorCode() >> "+e.getFolderExceptionErrorCode());
        return ApiErrorResponse.of(e.getFolderExceptionErrorCode());
    }

    @ExceptionHandler(EmailProcessException.class)
    protected ApiErrorResponse handleEmailProcessException(EmailProcessException e){
        log.info("EmailRequestException getMessage() >> "+e.getMessage());
        log.info("EmailRequestException getEmailExceptionErrorCode() >> "+e.getEmailExceptionErrorCode());
        return ApiErrorResponse.of(e.getEmailExceptionErrorCode());
    }

    @ExceptionHandler(BookmarkException.class)
    protected ApiErrorResponse handleBookmarkException(BookmarkException e){
        log.info("BookmarkException getMessage() >> "+e.getMessage());
        log.info("BookmarkException getBookmarkErrorCode() >> "+e.getBookmarkErrorCode());
        return ApiErrorResponse.of(e.getBookmarkErrorCode());
    }

}
