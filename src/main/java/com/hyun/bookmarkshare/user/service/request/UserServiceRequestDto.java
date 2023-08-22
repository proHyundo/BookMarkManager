package com.hyun.bookmarkshare.user.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserServiceRequestDto {

        private Long userId;
        private String userPwd;

        @Builder
        public UserServiceRequestDto(Long userId, String userPwd) {
            this.userId = userId;
            this.userPwd = userPwd;
        }
}
