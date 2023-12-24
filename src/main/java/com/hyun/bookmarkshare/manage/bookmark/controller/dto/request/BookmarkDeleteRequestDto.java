package com.hyun.bookmarkshare.manage.bookmark.controller.dto.request;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class BookmarkDeleteRequestDto {

    @NotNull
    @Positive
    Long userId;

    @NotNull
    @Positive
    Long bookmarkSeq;

    @NotNull
    @Positive
    Long folderSeq;

    @Builder
    public BookmarkDeleteRequestDto(Long userId, Long bookmarkSeq, Long folderSeq) {
        this.userId = userId;
        this.bookmarkSeq = bookmarkSeq;
        this.folderSeq = folderSeq;
    }

    public BookmarkServiceRequestDto toServiceRequestDto() {
        return BookmarkServiceRequestDto.builder()
                .userId(this.userId)
                .bookmarkSeq(this.bookmarkSeq)
                .folderSeq(this.folderSeq)
                .build();
    }
}
