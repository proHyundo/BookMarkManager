package com.hyun.bookmarkshare.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginResponseDto {

    private Long userId;
    private String userEmail;
    private String userRole;
    private String userAccessToken;
    private String userRefreshToken;


}
