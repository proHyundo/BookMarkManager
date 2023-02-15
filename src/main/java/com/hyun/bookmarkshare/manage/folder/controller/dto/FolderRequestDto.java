package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FolderRequestDto {

    private Long folderSeq;

    @NotBlank
    @Positive
    private Long userId;

    private String folderName;
    private String folderCaption;

    @Pattern(regexp = "[pou]")
    private String folderScope;

    @NotBlank
    @PositiveOrZero
    private Long folderParentSeq;


}
