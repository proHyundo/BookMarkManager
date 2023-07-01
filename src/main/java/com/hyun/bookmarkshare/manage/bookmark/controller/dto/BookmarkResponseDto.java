package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkResponseDto {

    Long bookmarkSeq;
    String bookmarkTitle;
    String bookmarkCaption;
    String bookmarkUrl;


}
