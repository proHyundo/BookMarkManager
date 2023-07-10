package com.hyun.bookmarkshare.user.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    Long userId;
    String userEmail;
    String userPwd;
    String userName;
    String userPhoneNum;
    LocalDateTime userRegDate;
    LocalDateTime userModDate;
    String userState;
    String userGrade;
    String userRole;
    String userSocial;

    // TODO : 도메인 객체 User 에 저장되어서는 안됨. ResponseDto 에서 관리할 것.
    String userAccessToken;
    String userRefreshToken;

}
