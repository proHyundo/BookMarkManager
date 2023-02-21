package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FolderListRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;
}
