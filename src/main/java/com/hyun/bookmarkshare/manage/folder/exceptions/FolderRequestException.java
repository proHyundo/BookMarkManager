package com.hyun.bookmarkshare.manage.folder.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FolderRequestException extends RuntimeException{

    private final FolderExceptionErrorCode folderExceptionErrorCode;
    private final String message;
}
