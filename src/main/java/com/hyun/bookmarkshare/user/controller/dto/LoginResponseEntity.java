package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponseEntity {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;
    private final Long userSeq;
//    private final String userEmail;
    private final String userRole;
    private final String userAccessToken;
    private final String userRefreshToken;

    public static ResponseEntity<LoginResponseEntity> toResponseEntity(User paramUser){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LoginResponseEntity.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusDescription(HttpStatus.OK.name())
                        .message("로그인 성공")
                        .userSeq(paramUser.getUSER_SEQ())
//                        .userEmail(paramUser.getUSER_EMAIL())
                        .userRole(paramUser.getUSER_ROLE())
                        .userAccessToken(paramUser.getUSER_ACCESS_TOKEN())
                        .userRefreshToken(paramUser.getUSER_REFRESH_TOKEN())
                        .build());
    }

}
