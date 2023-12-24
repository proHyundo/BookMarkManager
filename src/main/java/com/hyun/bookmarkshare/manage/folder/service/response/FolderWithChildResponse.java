package com.hyun.bookmarkshare.manage.folder.service.response;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class FolderWithChildResponse {

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
    private List<FolderWithChildResponse> childFolderList;

    @Builder
    public FolderWithChildResponse(Long folderSeq, Long userId, Long folderParentSeq, String folderRootFlag, String folderName, String folderCaption, Long folderOrder, String folderScope, LocalDateTime folderRegDate, LocalDateTime folderModDate, List<FolderWithChildResponse> childFolderList) {
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
        this.childFolderList = childFolderList;
    }

    public static FolderWithChildResponse of(Folder rootFolder) {
        return FolderWithChildResponse.builder()
                .folderSeq(rootFolder.getFolderSeq())
                .userId(rootFolder.getUserId())
                .folderParentSeq(rootFolder.getFolderParentSeq())
                .folderRootFlag(rootFolder.getFolderRootFlag())
                .folderName(rootFolder.getFolderName())
                .folderCaption(rootFolder.getFolderCaption())
                .folderOrder(rootFolder.getFolderOrder())
                .folderScope(rootFolder.getFolderScope())
                .folderRegDate(rootFolder.getFolderRegDate())
                .folderModDate(rootFolder.getFolderModDate())
                .build();
    }

    public void setChildFolderList(List<FolderWithChildResponse> childFolderList) {
        this.childFolderList = childFolderList;
    }
}
