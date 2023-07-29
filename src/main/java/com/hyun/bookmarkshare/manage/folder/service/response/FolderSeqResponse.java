package com.hyun.bookmarkshare.manage.folder.service.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FolderSeqResponse {

    private Long userId;
    private List<Long> folderSeqList;

    @Builder
    public FolderSeqResponse(Long userId, List<Long> folderSeqList) {
        this.userId = userId;
        this.folderSeqList = folderSeqList;
    }
}
