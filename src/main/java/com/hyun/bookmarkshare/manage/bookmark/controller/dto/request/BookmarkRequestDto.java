package com.hyun.bookmarkshare.manage.bookmark.controller.dto.request;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class BookmarkRequestDto {

    @NotNull
    @Positive
    Long userId;

    @NotNull
    @Positive
    Long bookmarkSeq;

    @Builder
    public BookmarkRequestDto(Long userId, Long bookmarkSeq) {
        this.userId = userId;
        this.bookmarkSeq = bookmarkSeq;
    }

    public BookmarkServiceRequestDto toServiceRequestDto() {
        return BookmarkServiceRequestDto.builder()
                .userId(this.userId)
                .bookmarkSeq(this.bookmarkSeq)
                .build();
    }

}
