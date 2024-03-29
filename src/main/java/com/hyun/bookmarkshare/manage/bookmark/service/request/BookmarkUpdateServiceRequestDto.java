package com.hyun.bookmarkshare.manage.bookmark.service.request;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.Builder;
import lombok.Getter;

@JsonFilter("BookmarkUpdateServiceRequestDto")
@Getter
public class BookmarkUpdateServiceRequestDto {

    private Long bookmarkSeq;
    private Long userId;
    private Long folderSeq;
    private String bookmarkTitle;
    private String bookmarkCaption;
    private String bookmarkUrl;

//    @JsonIgnore
    private String bookmarkScheme;
//    @JsonIgnore
    private String bookmarkHost;
//    @JsonIgnore
    private String bookmarkDomain;
//    @JsonIgnore
    private String bookmarkPort;
//    @JsonIgnore
    private String bookmarkPath;

    @Builder
    public BookmarkUpdateServiceRequestDto(Long bookmarkSeq, Long userId, Long folderSeq, String bookmarkTitle, String bookmarkCaption, String bookmarkUrl) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkTitle = bookmarkTitle;
        this.bookmarkCaption = bookmarkCaption;
        this.bookmarkUrl = bookmarkUrl;
    }

    public Bookmark toEntity(){
        return Bookmark.builder()
                .bookmarkSeq(bookmarkSeq)
                .userId(userId)
                .folderSeq(folderSeq)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption(bookmarkCaption)
                .bookmarkUrl(bookmarkUrl)
                .bookmarkScheme(bookmarkScheme)
                .bookmarkHost(bookmarkHost)
                .bookmarkDomain(bookmarkDomain)
                .bookmarkPort(bookmarkPort)
                .bookmarkPath(bookmarkPath)
                .build();
    }

    public void setBookmarkScheme(String bookmarkScheme) {
        this.bookmarkScheme = bookmarkScheme;
    }

    public void setBookmarkHost(String bookmarkHost) {
        this.bookmarkHost = bookmarkHost;
    }

    public void setBookmarkDomain(String bookmarkDomain) {
        this.bookmarkDomain = bookmarkDomain;
    }

    public void setBookmarkPort(String bookmarkPort) {
        this.bookmarkPort = bookmarkPort;
    }

    public void setBookmarkPath(String bookmarkPath) {
        this.bookmarkPath = bookmarkPath;
    }
}
