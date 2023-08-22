package com.hyun.bookmarkshare.manage.bookmark.controller.dto.request;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
@NoArgsConstructor
public class BookmarkListRequestDto {

    // valid : null, 0, 음수 불가
    @NotNull
    @Positive
    private Long userId;

    // valid : null, 0, 음수 불가
    @NotNull
    @Positive
    private Long folderSeq;

    @Builder
    public BookmarkListRequestDto(Long userId, Long folderSeq) {
        this.userId = userId;
        this.folderSeq = folderSeq;
    }

    public BookmarkServiceRequestDto toServiceDto(){
        return BookmarkServiceRequestDto.builder()
                .userId(this.userId)
                .bookmarkSeq(null)
                .folderSeq(this.folderSeq)
                .build();
    }

    @Override
    public String toString() {
        return "BookmarkListRequestDto{" +
                "userId=" + userId +
                ", folderSeq=" + folderSeq +
                '}';
    }
}
