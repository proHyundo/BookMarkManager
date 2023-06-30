package com.hyun.bookmarkshare.smtp.controller.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class EmailResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;

    public static ResponseEntity<EmailResponseEntity> toResponseEntity(HttpStatus httpStatus, String message){
        return ResponseEntity.status(httpStatus)
                .body(EmailResponseEntity.builder()
                        .statusCode(httpStatus.value())
                        .statusDescription(httpStatus.name())
                        .message(message)
                        .build());
    }
}
