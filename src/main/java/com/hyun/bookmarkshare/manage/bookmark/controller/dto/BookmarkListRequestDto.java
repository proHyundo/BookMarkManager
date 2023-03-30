package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

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
    private Long folderParentSeq;
}
