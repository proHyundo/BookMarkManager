package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class RefreshResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final LoginRefreshResponseDto loginResponseDto;

    static public ResponseEntity<RefreshResponseEntity> toResponseEntity(LoginRefreshResponseDto paramLoginRefreshResponseDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(RefreshResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("로그인 연장 성공")
                        .loginResponseDto(paramLoginRefreshResponseDto)
                        .build());
    }
}
