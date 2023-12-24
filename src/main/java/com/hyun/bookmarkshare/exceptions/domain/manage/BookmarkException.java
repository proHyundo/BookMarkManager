package com.hyun.bookmarkshare.exceptions.domain.manage;

import com.hyun.bookmarkshare.exceptions.errorcode.BookmarkErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkException extends RuntimeException{

        private final BookmarkErrorCode bookmarkErrorCode;
        private final String detailErrorMessage;


}
