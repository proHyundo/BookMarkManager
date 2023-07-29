package com.hyun.bookmarkshare.user.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
public class UserRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotEmpty
    private String userPwd;
}
