package com.hyun.bookmarkshare.manage.folder.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderDeleteServiceRequestDto {

    private Long folderSeq;
    private Long userId;

    @Builder
    public FolderDeleteServiceRequestDto(Long folderSeq, Long userId) {
        this.folderSeq = folderSeq;
        this.userId = userId;
    }
}
