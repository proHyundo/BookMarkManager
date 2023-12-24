package com.hyun.bookmarkshare.manage.folder.exceptions;

import com.hyun.bookmarkshare.exceptions.errorcode.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FolderExceptionErrorCode implements CustomErrorCode{

    /* 404 NOTFOUND */
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "Not Found Folder"),

    /* 409 CONFLICT */
    GET_FOLDER_FAIL(HttpStatus.CONFLICT, "Get Folder Fail"),
    CREATE_FOLDER_FAIL(HttpStatus.CONFLICT, "Create Folder Fail"),
    DELETE_FOLDER_FAIL(HttpStatus.CONFLICT, "Delete Folder Fail"),
    UPDATE_FOLDER_FAIL(HttpStatus.CONFLICT, "Update Folder Fail"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

//    @Override
//    public HttpStatus getHttpStatus() {
//        return this.httpStatus;
//    }
//
//    @Override
//    public String getMessage() {
//        return this.message;
//    }
}
