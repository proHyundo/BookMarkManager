package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FolderListRequestDto {

    private String userId;
    private String folderRootSeq;
    private String folderParentSeq;
    // 검증이 필요할텐데..
}
