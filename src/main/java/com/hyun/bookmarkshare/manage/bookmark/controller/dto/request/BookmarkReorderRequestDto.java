package com.hyun.bookmarkshare.manage.bookmark.controller.dto.request;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@NoArgsConstructor
public class BookmarkReorderRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long folderSeq;

    @NotNull
    private List<Long> bookmarkSeqOrder;

    @Builder
    public BookmarkReorderRequestDto(Long userId, Long folderSeq, List<Long> bookmarkSeqOrder) {
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkSeqOrder = bookmarkSeqOrder;
    }

    public BookmarkReorderServiceRequestDto toServiceRequestDto(){
        return BookmarkReorderServiceRequestDto.builder()
                .userId(this.userId)
                .folderSeq(this.folderSeq)
                .bookmarkSeqOrder(this.bookmarkSeqOrder)
                .build();
    }
}
