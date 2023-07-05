package com.hyun.bookmarkshare.manage.bookmark.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkUpdateServiceRequestDto {

    private Long bookmarkSeq;
    private Long userId;
    private String bookmarkCaption;
    private String bookmarkUrl;

    @Builder
    public BookmarkUpdateServiceRequestDto(Long bookmarkSeq, Long userId, String bookmarkCaption, String bookmarkUrl) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.bookmarkCaption = bookmarkCaption;
        this.bookmarkUrl = bookmarkUrl;
    }
}
