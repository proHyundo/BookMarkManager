package com.hyun.bookmarkshare.manage.bookmark.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    private LocalDateTime bookmarkRegDate;
    private LocalDateTime bookmarkModDate;
    private String bookmarkDelFlag;

    @Builder
    public Bookmark(Long bookmarkSeq, Long userId, Long folderSeq, String bookmarkTitle, String bookmarkCaption, String bookmarkScheme, String bookmarkHost, String bookmarkPort, String bookmarkDomain, String bookmarkPath, String bookmarkUrl, Long bookmarkOrder, LocalDateTime bookmarkRegDate, LocalDateTime bookmarkModDate, String bookmarkDelFlag) {
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


    public Bookmark updateEntityBy(Bookmark requestBookmark) {
        this.bookmarkTitle = requestBookmark.getBookmarkTitle();
        this.bookmarkCaption = requestBookmark.getBookmarkCaption();
        this.bookmarkScheme = requestBookmark.getBookmarkScheme();
        this.bookmarkHost = requestBookmark.getBookmarkHost();
        this.bookmarkPort = requestBookmark.getBookmarkPort();
        this.bookmarkDomain = requestBookmark.getBookmarkDomain();
        this.bookmarkPath = requestBookmark.getBookmarkPath();
        this.bookmarkUrl = requestBookmark.getBookmarkUrl();
        return this;
    }


}
