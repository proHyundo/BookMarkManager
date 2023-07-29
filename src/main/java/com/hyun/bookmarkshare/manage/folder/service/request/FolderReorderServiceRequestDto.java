package com.hyun.bookmarkshare.manage.folder.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FolderReorderServiceRequestDto {

    private Long userId;
    private Long folderParentSeq;
    private List<Long> folderSeqOrder;

    @Builder
    public FolderReorderServiceRequestDto(Long userId, Long folderParentSeq, List<Long> folderSeqOrder) {
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
        this.folderSeqOrder = folderSeqOrder;
    }
}
