package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkResponseDto {

    private Long bookmarkSeq;
    private Long userId;
    private Long folderSeq;
    private String bookmarkTitle;
    private String bookmarkCaption;
    private String bookmarkUrl;
    private Long bookmarkOrder;
    private LocalDateTime bookmarkRegDate;
    private LocalDateTime bookmarkModDate;
    private String bookmarkDelFlag;

    @Builder
    public BookmarkResponseDto(Long bookmarkSeq, Long userId, Long folderSeq, String bookmarkTitle, String bookmarkCaption, String bookmarkUrl, Long bookmarkOrder, LocalDateTime bookmarkRegDate, LocalDateTime bookmarkModDate, String bookmarkDelFlag) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkTitle = bookmarkTitle;
        this.bookmarkCaption = bookmarkCaption;
        this.bookmarkUrl = bookmarkUrl;
        this.bookmarkOrder = bookmarkOrder;
        this.bookmarkRegDate = bookmarkRegDate;
        this.bookmarkModDate = bookmarkModDate;
        this.bookmarkDelFlag = bookmarkDelFlag;
    }
}
