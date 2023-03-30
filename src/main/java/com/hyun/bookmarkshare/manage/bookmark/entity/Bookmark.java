package com.hyun.bookmarkshare.manage.bookmark.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Bookmark {

    private Long bookmarkSeq;
    private Long userId;
    private Long folderSeq;
    private String bookmarkCaption;
    private String bookmarkScheme;
    private String bookmarkHost;
    private String bookmarkDomain;
    private String bookmarkPath;
    private String bookmarkUrl;
    private String bookmarkDelFlag;


}
