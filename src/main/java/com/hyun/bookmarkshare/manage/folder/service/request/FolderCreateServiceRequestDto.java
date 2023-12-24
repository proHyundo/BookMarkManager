package com.hyun.bookmarkshare.manage.folder.service.request;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FolderCreateServiceRequestDto {

    private Long userId;
    private Long folderParentSeq;
    private String folderName;
    private String folderCaption;
    private String folderScope;

    @Builder
    public FolderCreateServiceRequestDto(Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope) {
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
        this.folderName = folderName;
        this.folderCaption = folderCaption;
        this.folderScope = folderScope;
    }

    public Folder toFolder() {
        return Folder.builder()
                .folderSeq(null)
                .userId(this.userId)
                .folderParentSeq(this.folderParentSeq)
                .folderRootFlag("n")
                .folderName(this.folderName)
                .folderCaption(this.folderCaption)
                .folderScope(this.folderScope)
                .build();
    }
}
