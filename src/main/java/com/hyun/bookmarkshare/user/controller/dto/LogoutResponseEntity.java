package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class LogoutResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;

    public static ResponseEntity<LogoutResponseEntity> toResponseEntity(HttpStatus resultStatus) {
        String strMessage = resultStatus==HttpStatus.OK?"로그아웃 완료.":"로그아웃 실패";
        return ResponseEntity.status(resultStatus.value()).body(LogoutResponseEntity
                .builder()
                .statusCode(resultStatus.value())
                .statusDescription(resultStatus.name())
                .message(strMessage)
                .build());
    }

}