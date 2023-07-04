package com.hyun.bookmarkshare.manage.bookmark.entity;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Bookmark {

    private Long bookmarkSeq;
    private Long userId;
    private Long folderSeq;
    private String bookmarkTitle;
    private String bookmarkCaption;
    private String bookmarkScheme;
    private String bookmarkHost;
    private String bookmarkPort;
    private String bookmarkDomain;
    private String bookmarkPath;
    private String bookmarkUrl;
    private Long bookmarkOrder;
    private Date bookmarkRegDate;
    private Date bookmarkModDate;
    private String bookmarkDelFlag;

    @Builder
    public Bookmark(Long bookmarkSeq, Long userId, Long folderSeq, String bookmarkTitle, String bookmarkCaption, String bookmarkScheme, String bookmarkHost, String bookmarkPort, String bookmarkDomain, String bookmarkPath, String bookmarkUrl, Long bookmarkOrder, Date bookmarkRegDate, Date bookmarkModDate, String bookmarkDelFlag) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkTitle = bookmarkTitle;
        this.bookmarkCaption = bookmarkCaption;
        this.bookmarkScheme = bookmarkScheme;
        this.bookmarkHost = bookmarkHost;
        this.bookmarkPort = bookmarkPort;
        this.bookmarkDomain = bookmarkDomain;
        this.bookmarkPath = bookmarkPath;
        this.bookmarkUrl = bookmarkUrl;
        this.bookmarkOrder = bookmarkOrder;
        this.bookmarkRegDate = bookmarkRegDate;
        this.bookmarkModDate = bookmarkModDate;
        this.bookmarkDelFlag = bookmarkDelFlag;
    }

    public BookmarkResponseDto toBookmarkResponseDto() {
        return BookmarkResponseDto.builder()
                .bookmarkSeq(this.bookmarkSeq)
                .bookmarkTitle(this.bookmarkTitle)
                .bookmarkCaption(this.bookmarkCaption)
                .bookmarkUrl(this.bookmarkUrl)
                .build();
    }


}
