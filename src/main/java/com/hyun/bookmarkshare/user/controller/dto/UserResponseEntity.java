package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final User userData;

    public static ResponseEntity<UserResponseEntity> toResponseEntity(User paramUser){
        return ResponseEntity.status(HttpStatus.OK).body(UserResponseEntity.builder()
                .statusCode(HttpStatus.OK.value())
                .statusDescription(HttpStatus.OK.name())
                .message("회원 관련 요청 성공")
                .userData(paramUser)
                .build());
    }
}
