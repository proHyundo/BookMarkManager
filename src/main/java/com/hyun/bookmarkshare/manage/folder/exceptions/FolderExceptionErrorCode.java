package com.hyun.bookmarkshare.manage.folder.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FolderExceptionErrorCode {

    /* 404 NOTFOUND */
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "Not Found Folder"),

    /* 409 CONFLICT */
    CREATE_FOLDER_FAIL(HttpStatus.CONFLICT, "Create Folder Fail"),
    DELETE_FOLDER_FAIL(HttpStatus.CONFLICT, "Delete Folder Fail"),
    UPDATE_FOLDER_FAIL(HttpStatus.CONFLICT, "Update Folder Fail"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
