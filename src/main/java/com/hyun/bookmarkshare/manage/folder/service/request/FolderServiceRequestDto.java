package com.hyun.bookmarkshare.manage.folder.service.request;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FolderServiceRequestDto {

    private Long folderSeq;
    private Long userId;
    private String folderName;
    private String folderCaption;
    private String folderScope;
    private Long folderParentSeq;

    @Builder
    public FolderServiceRequestDto(Long folderSeq, Long userId, String folderName, String folderCaption, String folderScope, Long folderParentSeq) {
        this.folderSeq = folderSeq;
        this.userId = userId;
        this.folderName = folderName;
        this.folderCaption = folderCaption;
        this.folderScope = folderScope;
        this.folderParentSeq = folderParentSeq;
    }

    public Folder toEntity(){
        return Folder.builder()
                .folderSeq(this.folderSeq)
                .userId(this.userId)
                .folderName(this.folderName)
                .folderCaption(this.folderCaption)
                .folderScope(this.folderScope)
                .folderParentSeq(this.folderParentSeq)
                .build();
    }
}
