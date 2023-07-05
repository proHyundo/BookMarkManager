package com.hyun.bookmarkshare.manage.bookmark.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkServiceRequestDto {

    Long userId;
    Long bookmarkSeq;

    @Builder
    public BookmarkServiceRequestDto(Long userId, Long bookmarkSeq) {
        this.userId = userId;
        this.bookmarkSeq = bookmarkSeq;
    }
}
