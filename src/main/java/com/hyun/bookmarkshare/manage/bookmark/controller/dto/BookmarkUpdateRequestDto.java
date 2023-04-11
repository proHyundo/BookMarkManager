package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkUpdateRequestDto {

    private Long bookmarkSeq;
    private Long userId;
    private String bookmarkCaption;
    private String bookmarkScheme;
    private String bookmarkHost;
    private String bookmarkPort;
    private String bookmarkDomain;
    private String bookmarkPath;
    private String bookmarkUrl;


}
