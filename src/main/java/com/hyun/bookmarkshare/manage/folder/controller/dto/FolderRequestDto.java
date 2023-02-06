package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FolderRequestDto {

    private Long userId;
    private String folderName;
    private String folderCaption;
    private String folderScope;
    private String folderParentSeq;
    private Long folderSeq;

}
