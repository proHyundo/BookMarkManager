package com.hyun.bookmarkshare.exceptions;

import com.hyun.bookmarkshare.manage.folder.exceptions.FolderExceptionErrorCode;
import com.hyun.bookmarkshare.user.controller.dto.LoginErrorResponseEntity;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomErrorResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;

    public static ResponseEntity<CustomErrorResponseEntity> toResponseEntity(FolderExceptionErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CustomErrorResponseEntity.builder()
                        .statusCode(errorCode.getHttpStatus().value())
                        .statusDescription(errorCode.getHttpStatus().name())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

}
