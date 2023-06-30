package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
public class SignUpResponseEntity {

    private static final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final SignUpResponseDto signUpResponseDto;

    public static ResponseEntity<SignUpResponseEntity> toResponseEntity(User paramUser) {
        log.info(timestamp.toString());
        return ResponseEntity.status(HttpStatus.OK).body(
                SignUpResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("회원가입 완료")
                        .signUpResponseDto(SignUpResponseDto.builder()
                                .userEmail(paramUser.getUserEmail())
                                .userName(paramUser.getUserName())
                                .build())
//                        .userEmail(paramUser.getUserEmail())
//                        .userName(paramUser.getUserName())
                        .build()
        );
    }
}
