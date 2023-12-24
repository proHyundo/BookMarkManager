package com.hyun.bookmarkshare.manage.folder.service.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FolderDeleteResponse {

    private Long userId;
    private List<Long> folderSeqList;
    private Integer deleteBookmarksCount;

    @Builder
    public FolderDeleteResponse(Long userId, List<Long> folderSeqList, Integer deleteBookmarksCount) {
        this.userId = userId;
        this.folderSeqList = folderSeqList;
        this.deleteBookmarksCount = deleteBookmarksCount;
    }
}
