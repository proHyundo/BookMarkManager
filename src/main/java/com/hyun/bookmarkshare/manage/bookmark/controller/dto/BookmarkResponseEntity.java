package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
public class BookmarkResponseEntity {

    private final int statusCode;
    private final String statusDescription;
    private final String message;

    private final Long bookmarkSeq;
    private final String bookmarkCaption;
    private final String bookmarkUrl;

    public static ResponseEntity<BookmarkResponseEntity> toBookmarkResponseEntity(BookmarkResponseDto bookmarkResponseDto){
        log.info(String.valueOf(LocalDateTime.now()));
        return ResponseEntity.status(HttpStatus.OK).body(BookmarkResponseEntity.builder()
                                                .statusCode(HttpStatus.OK.value())
                                                .statusDescription(HttpStatus.OK.name())
                                                .message("북마크 관련 요청 성공")
                                                .bookmarkSeq(bookmarkResponseDto.getBookmarkSeq())
                                                .bookmarkCaption(bookmarkResponseDto.getBookmarkCaption())
                                                .bookmarkUrl(bookmarkResponseDto.getBookmarkUrl())
                                                .build());
    }
}
