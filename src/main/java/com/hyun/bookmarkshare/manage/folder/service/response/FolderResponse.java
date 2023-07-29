package com.hyun.bookmarkshare.manage.folder.service.response;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FolderResponse {

    private Long folderSeq;
    private Long userId;
    private Long folderParentSeq;
    private String folderRootFlag;
    private String folderName;
    private String folderCaption;
    private Long folderOrder;
    private String folderScope;
    private LocalDateTime folderRegDate;
    private LocalDateTime folderModDate;

    @Builder
    public FolderResponse(Long folderSeq, Long userId, Long folderParentSeq, String folderRootFlag, String folderName, String folderCaption, Long folderOrder, String folderScope, LocalDateTime folderRegDate, LocalDateTime folderModDate) {
        this.folderSeq = folderSeq;
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
        this.folderRootFlag = folderRootFlag;
        this.folderName = folderName;
        this.folderCaption = folderCaption;
        this.folderOrder = folderOrder;
        this.folderScope = folderScope;
        this.folderRegDate = folderRegDate;
        this.folderModDate = folderModDate;
    }

    public static FolderResponse of(Folder folder){
        return FolderResponse.builder()
                .folderSeq(folder.getFolderSeq())
                .userId(folder.getUserId())
                .folderParentSeq(folder.getFolderParentSeq())
                .folderRootFlag(folder.getFolderRootFlag())
                .folderName(folder.getFolderName())
                .folderCaption(folder.getFolderCaption())
                .folderOrder(folder.getFolderOrder())
                .folderScope(folder.getFolderScope())
                .folderRegDate(folder.getFolderRegDate())
                .folderModDate(folder.getFolderModDate())
                .build();
    }
}
