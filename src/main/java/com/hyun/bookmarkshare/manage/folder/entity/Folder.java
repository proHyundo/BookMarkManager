package com.hyun.bookmarkshare.manage.folder.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Folder {

    Long folderSeq;
    Long userId;
    Long folderParentSeq;
    String folderRootFlag;
    String folderName;
    String folderCaption;
    Long folderOrder;
    String folderScope;
    LocalDateTime folderRegDate;
    LocalDateTime folderModDate;
    String folderDelFlag;

    @Builder
    public Folder(Long folderSeq, Long userId, Long folderParentSeq, String folderRootFlag, String folderName, String folderCaption, Long folderOrder, String folderScope, LocalDateTime folderRegDate, LocalDateTime folderModDate, String folderDelFlag) {
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
        this.folderDelFlag = folderDelFlag;
    }


    @Override
    public String toString() {
        return "Folder{" +
                "folderSeq=" + folderSeq +
                ", userId=" + userId +
                ", folderParent=" + folderParentSeq +
                ", folderRootFlag='" + folderRootFlag + '\'' +
                ", folderName='" + folderName + '\'' +
                ", folderCaption='" + folderCaption + '\'' +
                ", folderOrder=" + folderOrder +
                ", folderScope='" + folderScope + '\'' +
                ", folderRegDate=" + folderRegDate +
                ", folderModDate=" + folderModDate +
                ", folderDelFlag='" + folderDelFlag + '\'' +
                '}';
    }

    public Folder updateEntityBy(Folder requestFolder) {
        this.folderName = requestFolder.getFolderName();
        this.folderCaption = requestFolder.getFolderCaption();
        this.folderScope = requestFolder.getFolderScope();
        return this;
    }

}
