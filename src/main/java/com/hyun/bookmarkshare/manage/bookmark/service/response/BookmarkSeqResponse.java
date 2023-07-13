package com.hyun.bookmarkshare.manage.bookmark.service.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkSeqResponse {

    private Long bookmarkSeq;
    private Long userId;
    private Long folderSeq;

    @Builder
    public BookmarkSeqResponse(Long bookmarkSeq, Long userId, Long folderSeq) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.folderSeq = folderSeq;
    }
}
