package com.hyun.bookmarkshare.manage.folder.controller.dto;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FolderListResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final List<Folder> folderList;

    public static ResponseEntity<FolderListResponseEntity> toResponseEntity(List<Folder> paramFolderList){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FolderListResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("폴더 리스트 조회 성공")
                        .folderList(paramFolderList)
                        .build());
    }
}
