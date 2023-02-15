package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
public class FolderCreateRequestDto {

    @NotBlank
    @Positive
    private Long userId;

    @NotBlank
    @PositiveOrZero
    private Long folderParentSeq;

    @Size(min = 0, max = 50)
    private String folderName;

    private String folderCaption;

    @Pattern(regexp = "[pou]")
    private String folderScope;
}
