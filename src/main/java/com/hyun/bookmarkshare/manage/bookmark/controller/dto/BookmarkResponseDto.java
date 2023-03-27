package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkResponseDto {

    Long bookmarkSeq;
    String bookmarkCaption;
    String bookmarkUrl;


}
