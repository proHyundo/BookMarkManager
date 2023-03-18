package com.hyun.bookmarkshare.user.controller.dto;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRefreshResponseDto {

    Long userId;
    String userAccessToken;
    List userRole;
    String userEmail;

}
