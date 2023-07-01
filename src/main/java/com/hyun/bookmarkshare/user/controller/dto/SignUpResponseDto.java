package com.hyun.bookmarkshare.user.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class SignUpResponseDto {
    private String userEmail;
    private String userName;
}
