package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class BookmarkAddRequestDto {

    @Positive
    Long userId;

    @NotEmpty
    String bookmarkUrl;

    @Positive
    Long folderSeq;

    String bookmarkCaption;
    String bookScheme;
    String bookHost;
    String bookDomain;
    String bookPath;

}
