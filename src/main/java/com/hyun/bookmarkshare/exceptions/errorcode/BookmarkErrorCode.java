package com.hyun.bookmarkshare.exceptions.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements CustomErrorCode{

    // common
    BOOKMARK_URL_PARSE_FAIL(HttpStatus.ACCEPTED, "Bookmark url parse fail."),
    BOOKMARK_NOT_FOUND(HttpStatus.BAD_REQUEST, "Bookmark not found."),
    BOOKMARK_NO_AUTH(HttpStatus.BAD_REQUEST, "Bookmark no auth to access."),
    BOOKMARK_INVALID_INPUT(HttpStatus.BAD_REQUEST, "Bookmark invalid input."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
