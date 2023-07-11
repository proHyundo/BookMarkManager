package com.hyun.bookmarkshare.manage.folder.service.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
public class FolderListServiceRequestDto {

    private Long userId;
    private Long folderParentSeq;

    @Builder
    public FolderListServiceRequestDto(Long userId, Long folderParentSeq) {
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
    }
}
