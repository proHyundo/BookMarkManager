package com.hyun.bookmarkshare.user.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    Long userId;
    String userEmail;
    String userName;
    String userState;
    String userGrade;
    String userRole;
    Date userRegDate;
    Date userModDate;
    String userAccessToken;
    String userRefreshToken;

}
