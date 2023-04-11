package com.hyun.bookmarkshare.manage.bookmark.entity;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
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

    public BookmarkResponseDto toBookmarkResponseDto() {
        return BookmarkResponseDto.builder()
                .bookmarkSeq(this.bookmarkSeq)
                .bookmarkTitle(this.bookmarkTitle)
                .bookmarkCaption(this.bookmarkCaption)
                .bookmarkUrl(this.bookmarkUrl)
                .build();
    }


}
