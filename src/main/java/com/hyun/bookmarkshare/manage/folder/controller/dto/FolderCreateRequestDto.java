package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
public class FolderCreateRequestDto {

    @Null
    private Long folderSeq;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;

    @Pattern(regexp = "[A-Za-z0-9_ ]{1,50}")
    @Size(min = 1, max = 50)
    private String folderName;

    private String folderCaption;

    @Pattern(regexp = "[pou]")
    private String folderScope;
}
