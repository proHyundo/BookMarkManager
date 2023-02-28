package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class SignUpResponseEntity {



    public static ResponseEntity<SignUpResponseEntity> toResponseEntity(User paramUser) {

        return ResponseEntity.status(HttpStatus.OK).body(
                SignUpResponseEntity.builder().build()
        );
    }
}
