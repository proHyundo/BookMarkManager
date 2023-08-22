package com.hyun.bookmarkshare.user.service.response;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginResponse {

    private Long userId;
    private String userEmail;
    private String userRole;
    private String userAccessToken;
    private String userRefreshToken;

    @Builder
    public UserLoginResponse(Long userId, String userEmail, String userRole, String userAccessToken, String userRefreshToken) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.userAccessToken = userAccessToken;
        this.userRefreshToken = userRefreshToken;
    }

    public static UserLoginResponse of(User user) {
        return UserLoginResponse.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userRole(user.getUserRole())
                .userAccessToken(user.getUserAccessToken())
                .userRefreshToken(user.getUserRefreshToken())
                .build();
    }
}
