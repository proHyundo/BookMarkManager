package com.hyun.bookmarkshare.security.jwt.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
public class LoginInfoDto {

    private String email;
    private Long userId;

    @Builder
    public LoginInfoDto(String email, Long userId) {
        this.email = email;
        this.userId = userId;
    }
}
