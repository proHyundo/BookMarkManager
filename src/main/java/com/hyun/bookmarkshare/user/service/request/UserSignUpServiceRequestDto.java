package com.hyun.bookmarkshare.user.service.request;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignUpServiceRequestDto {

    private Long userId;
    private String userEmail;
    private String userPwd;
    private String userName;
    private String userPhoneNum;
    private String userGender;
    private String emailValidationCode;

    @Builder
    public UserSignUpServiceRequestDto(Long userId, String userEmail, String userPwd, String userName, String userPhoneNum, String userGender, String emailValidationCode) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userName = userName;
        this.userPhoneNum = userPhoneNum;
        this.userGender = userGender;
        this.emailValidationCode = emailValidationCode;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userEmail(userEmail)
                .userPwd(userPwd)
                .userName(userName)
                .userPhoneNum(userPhoneNum)
                .userRegDate(null)
                .userModDate(null)
                .userState("n")
                .userGrade("g")
                .userRole("ROLE_USER")
                .userSocial("n")
                .build();
    }

}
