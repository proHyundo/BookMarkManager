package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
public class FolderListRequestDto {

    @NotBlank
    @Positive
    private Long userId;

    @NotBlank
    @PositiveOrZero
    private Long folderParentSeq;
}
