package com.hyun.bookmarkshare.manage.folder.controller.dto;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FolderSeqResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final Long folderSeq;

    public static ResponseEntity<FolderSeqResponseEntity> toResponseEntity(Long folderSeqParam){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FolderSeqResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("해당 폴더식별번호 관련 요청 성공")
                        .folderSeq(folderSeqParam)
                        .build());
    }
}
