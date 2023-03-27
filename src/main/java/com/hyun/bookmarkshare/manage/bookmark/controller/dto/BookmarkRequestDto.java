package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRequestDto {

    Long userId;
    Long bookmarkSeq;

}
