package com.hyun.bookmarkshare.exceptions;

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

    public static ResponseEntity<CustomErrorResponseEntity> toResponseEntity(CustomErrorCode errorCode){
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
