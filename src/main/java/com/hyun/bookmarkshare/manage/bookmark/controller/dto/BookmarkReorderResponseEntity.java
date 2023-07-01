package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderResponseEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BookmarkReorderResponseEntity {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final List<Long> bookmarkSeqList;

    public static ResponseEntity<BookmarkReorderResponseEntity> toResponseEntity(List<Long> bookmarkSeqListParam){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BookmarkReorderResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("북마크 순서 변경 요청 성공")
                        .bookmarkSeqList(bookmarkSeqListParam)
                        .build());
    }
    
}
