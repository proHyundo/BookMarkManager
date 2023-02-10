package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FolderReorderRequestDto {
    Long folderParentSeq;
    Long userId;
    List<Integer> folderSeqOrder;
}
