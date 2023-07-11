package com.hyun.bookmarkshare.manage.folder.service.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FolderReorderResponse {

    private Long userId;
    private Long folderParentSeq;
    private List<Long> folderSeqOrder;

    @Builder
    public FolderReorderResponse(Long userId, Long folderParentSeq, List<Long> folderSeqOrder) {
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
        this.folderSeqOrder = folderSeqOrder;
    }
}
