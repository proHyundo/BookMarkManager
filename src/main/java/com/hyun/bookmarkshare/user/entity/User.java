package com.hyun.bookmarkshare.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@AllArgsConstructor
public class User {

    Long USER_SEQ;
    String USER_EMAIL;
    String USER_NAME;
    String USER_STATE;
    String USER_GRADE;
    String USER_ROLE;
    String USER_ACCESS_TOKEN;
    String USER_REFRESH_TOKEN;
}
