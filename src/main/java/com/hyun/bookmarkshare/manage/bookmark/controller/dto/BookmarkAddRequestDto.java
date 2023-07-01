package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class BookmarkAddRequestDto {

    @NotNull
    @Positive
    Long userId;

    @NotEmpty
    String bookmarkUrl;

    @NotNull
    @Positive
    Long folderSeq;

    String bookmarkTitle;
    String bookmarkCaption;

    String bookmarkScheme;
    String bookmarkHost;
    String bookmarkDomain;
    String bookmarkPort;
    String bookmarkPath;

    @Null
    Long bookSeq;

    public BookmarkResponseDto toBookmarkResponseDto(BookmarkAddRequestDto requestDto){
        return BookmarkResponseDto.builder()
                .bookmarkSeq(requestDto.getBookSeq())
                .bookmarkTitle(requestDto.getBookmarkTitle())
                .bookmarkCaption(requestDto.getBookmarkCaption())
                .bookmarkUrl(requestDto.getBookmarkUrl())
                .build();
    }


}
