package com.hyun.bookmarkshare.user.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRefreshToken {

    Long USER_SEQ;
    String REFRESH_TOKEN;
}
