package com.hyun.bookmarkshare.user.service.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignoutResponse {

    private String userEmail;
    private String userState;

    @Builder
    public UserSignoutResponse(String userEmail, String userState) {
        this.userEmail = userEmail;
        this.userState = userState;
    }

    public static UserSignoutResponse of(String userEmail, String userState){
        return UserSignoutResponse.builder()
                .userEmail(userEmail)
                .userState(userState)
                .build();
    }
}
