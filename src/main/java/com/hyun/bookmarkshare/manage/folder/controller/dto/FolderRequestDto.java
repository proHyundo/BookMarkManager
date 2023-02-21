package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FolderRequestDto {

    @NotNull
    @Positive
    private Long folderSeq;

    @NotNull
    @Positive
    private Long userId;

    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9_ ]{1,50}")
    @Size(min = 1, max = 50)
    private String folderName;

    @Size(max = 50)
    private String folderCaption;

    @NotBlank
    @Pattern(regexp = "[pou]")
    private String folderScope;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;


}
