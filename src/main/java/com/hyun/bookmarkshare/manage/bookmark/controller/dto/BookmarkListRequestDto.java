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

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long folderParentSeq;
}
