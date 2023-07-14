package com.hyun.bookmarkshare.user.service.response;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    Long userId;
    String userEmail;
    String userName;
    String userPhoneNum;
    LocalDateTime userRegDate;
    LocalDateTime userModDate;
    String userState;
    String userGrade;
    String userRole;
    String userSocial;

    @Builder
    public UserResponse(Long userId, String userEmail, String userName, String userPhoneNum, LocalDateTime userRegDate, LocalDateTime userModDate, String userState, String userGrade, String userRole, String userSocial) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhoneNum = userPhoneNum;
        this.userRegDate = userRegDate;
        this.userModDate = userModDate;
        this.userState = userState;
        this.userGrade = userGrade;
        this.userRole = userRole;
        this.userSocial = userSocial;
    }

    public static UserResponse of(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .userState(user.getUserState())
                .userRegDate(user.getUserRegDate())
                .userModDate(user.getUserModDate())
                .build();
    }
}
