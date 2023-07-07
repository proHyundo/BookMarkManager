package com.hyun.bookmarkshare.manage.folder.service.request;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FolderCreateServiceRequestDto {

    private Long folderSeq;
    private Long userId;
    private Long folderParentSeq;
    private String folderName;
    private String folderCaption;
    private String folderScope;

    @Builder
    public FolderCreateServiceRequestDto(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope) {
        this.folderSeq = folderSeq;
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
        this.folderName = folderName;
        this.folderCaption = folderCaption;
        this.folderScope = folderScope;
    }

    public Folder toFolder() {
        return Folder.builder()
                .folderSeq(this.folderSeq)
                .userId(this.userId)
                .folderParentSeq(this.folderParentSeq)
                .folderName(this.folderName)
                .folderCaption(this.folderCaption)
                .folderScope(this.folderScope)
                .build();
    }
}
