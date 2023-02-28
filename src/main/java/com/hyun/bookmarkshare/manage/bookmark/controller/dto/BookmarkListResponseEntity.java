package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class BookmarkListResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final List<Bookmark> bookmarkList;

    public static ResponseEntity<BookmarkListResponseEntity> toBookmarkListResponseEntity(List<Bookmark> paramBookmarkList){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BookmarkListResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("북마크 리스트 요청 성공")
                        .bookmarkList(paramBookmarkList)
                        .build());
    }
}
