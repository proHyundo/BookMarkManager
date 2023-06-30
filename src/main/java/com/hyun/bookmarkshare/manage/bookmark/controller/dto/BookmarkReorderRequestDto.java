package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@AllArgsConstructor
public class BookmarkReorderRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long folderSeq;

    @NotNull
    private List<Long> bookmarkSeqOrder;
}
