package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginErrorResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;

    public static ResponseEntity<LoginErrorResponseEntity> toResponseEntity(LoginExceptionErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(LoginErrorResponseEntity.builder()
                        .statusCode(errorCode.getHttpStatus().value())
                        .statusDescription(errorCode.getHttpStatus().name())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

}
