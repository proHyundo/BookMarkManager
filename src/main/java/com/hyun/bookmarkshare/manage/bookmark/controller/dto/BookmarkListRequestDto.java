package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BookmarkListRequestDto {

    // valid : null, 0, 음수 불가
    @NotNull
    @Positive
    private Long userId;

    // valid : null, 0, 음수 불가
    @NotNull
    @Positive
    private Long folderSeq;

    public BookmarkServiceRequestDto toServiceDto(){
        return BookmarkServiceRequestDto.builder()
                .userId(this.userId)
                .bookmarkSeq(null)
                .folderSeq(this.folderSeq)
                .build();
    }
}
