package com.hyun.bookmarkshare.manage.folder.controller.dto;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class FolderResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final Folder folder;

    public static ResponseEntity<FolderResponseEntity> toResponseEntity(Folder paramFolder){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FolderResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("폴더 생성 완료")
                        .folder(paramFolder)
                        .build());
    }
}
