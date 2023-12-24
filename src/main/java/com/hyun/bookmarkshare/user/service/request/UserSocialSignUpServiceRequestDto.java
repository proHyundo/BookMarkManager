package com.hyun.bookmarkshare.user.service.request;

import com.hyun.bookmarkshare.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSocialSignUpServiceRequestDto {

    private Long socialUserId;
    private String socialUserEmail;
    private String socialUserPwd;
    private String socialUserName;

    @Builder
    public UserSocialSignUpServiceRequestDto(Long socialUserId, String socialUserEmail, String socialUserPwd, String socialUserName) {
        this.socialUserId = socialUserId;
        this.socialUserEmail = socialUserEmail;
        this.socialUserPwd = socialUserPwd;
        this.socialUserName = socialUserName;
    }

    public User toEntity() {
        return User.builder()
                .userId(null)
                .userEmail(socialUserEmail)
                .userPwd(socialUserPwd)
                .userName(socialUserName)
                .userPhoneNum(null)
                .userRegDate(null)
                .userModDate(null)
                .userState("n")
                .userGrade("g")
                .userRole("ROLE_USER")
                .userSocial("k")
                .build();
    }
}
