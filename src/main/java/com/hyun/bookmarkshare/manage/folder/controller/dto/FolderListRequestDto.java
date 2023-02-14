package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class FolderListRequestDto {

    @Positive
    private Long userId;
    //private Long folderRootSeq; // rootSeq 는 왜 필요했더라..ㅋ?
    @Positive
    private Long folderParentSeq;
}
