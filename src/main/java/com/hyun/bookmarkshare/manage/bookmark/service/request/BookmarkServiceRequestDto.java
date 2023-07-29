package com.hyun.bookmarkshare.manage.bookmark.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkServiceRequestDto {

    Long bookmarkSeq;
    Long userId;
    Long folderSeq;

    @Builder
    public BookmarkServiceRequestDto(Long bookmarkSeq, Long userId, Long folderSeq) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.folderSeq = folderSeq;
    }
}
