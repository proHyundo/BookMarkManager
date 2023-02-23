package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BookmarkListRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;
}
