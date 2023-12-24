package com.hyun.bookmarkshare.manage.bookmark.service.request;

import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkCreateServiceRequestDto {

    private Long userId;
    private Long folderSeq;

    private String bookmarkTitle;
    private String bookmarkCaption;

    private String bookmarkScheme;
    private String bookmarkHost;
    private String bookmarkDomain;
    private String bookmarkPort;
    private String bookmarkPath;

    private String bookmarkUrl;

    @Builder
    public BookmarkCreateServiceRequestDto(Long userId, Long folderSeq, String bookmarkTitle, String bookmarkCaption, String bookmarkScheme, String bookmarkHost, String bookmarkDomain, String bookmarkPort, String bookmarkPath, String bookmarkUrl) {
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkTitle = bookmarkTitle;
        this.bookmarkCaption = bookmarkCaption;
        this.bookmarkScheme = bookmarkScheme;
        this.bookmarkHost = bookmarkHost;
        this.bookmarkDomain = bookmarkDomain;
        this.bookmarkPort = bookmarkPort;
        this.bookmarkPath = bookmarkPath;
        this.bookmarkUrl = bookmarkUrl;
    }

    public Bookmark toBookmarkEntity(){
        return Bookmark.builder()
                .bookmarkSeq(null)
                .userId(userId)
                .folderSeq(folderSeq)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption(bookmarkCaption)
                .bookmarkScheme(bookmarkScheme)
                .bookmarkHost(bookmarkHost)
                .bookmarkDomain(bookmarkDomain)
                .bookmarkPort(bookmarkPort)
                .bookmarkPath(bookmarkPath)
                .bookmarkUrl(bookmarkUrl)
                .build();
    }
}
