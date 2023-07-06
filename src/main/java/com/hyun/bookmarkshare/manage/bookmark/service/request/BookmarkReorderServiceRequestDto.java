package com.hyun.bookmarkshare.manage.bookmark.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookmarkReorderServiceRequestDto {

    private Long userId;
    private Long folderSeq;
    private List<Long> bookmarkSeqOrder;

    @Builder
    public BookmarkReorderServiceRequestDto(Long userId, Long folderSeq, List<Long> bookmarkSeqOrder) {
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkSeqOrder = bookmarkSeqOrder;
    }
}
