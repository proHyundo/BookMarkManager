package com.hyun.bookmarkshare.social.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUser {

    private Long kakaoUserId;
    private String kakaoUserEmail;
    private String kakaoUserPassword;
    private String kakaoUserNickname;

    @Builder
    public KakaoUser(Long kakaoUserId, String kakaoUserEmail, String kakaoUserPassword, String kakaoUserNickname) {
        this.kakaoUserId = kakaoUserId;
        this.kakaoUserEmail = kakaoUserEmail;
        this.kakaoUserPassword = kakaoUserPassword;
        this.kakaoUserNickname = kakaoUserNickname;
    }

    public void setKakaoUserPassword(String kakaoUserPassword) {
        this.kakaoUserPassword = kakaoUserPassword;
    }
}
