package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FolderReorderResponseEntity {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final List<Long> folderParentSeqList;

    public static ResponseEntity<FolderReorderResponseEntity> toResponseEntity(List<Long> folderParentSeqListParam){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FolderReorderResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("폴더 순서 변경 요청 성공")
                        .folderParentSeqList(folderParentSeqListParam)
                        .build());
    }
}
