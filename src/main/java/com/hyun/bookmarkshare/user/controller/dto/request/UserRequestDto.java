package com.hyun.bookmarkshare.user.controller.dto.request;

import com.hyun.bookmarkshare.user.service.request.UserServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotEmpty
    private String userPwd;

    @Builder
    public UserRequestDto(Long userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

    public UserServiceRequestDto toServiceDto() {
        return UserServiceRequestDto.builder()
                .userId(userId)
                .userPwd(userPwd)
                .build();
    }
}
