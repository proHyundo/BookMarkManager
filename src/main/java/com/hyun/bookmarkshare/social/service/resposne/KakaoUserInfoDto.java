package com.hyun.bookmarkshare.social.service.resposne;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class KakaoUserInfoDto {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    @ToString
    public static class KakaoAccount {
        private String email;
    }

    @Getter
    @ToString
    public static class Properties {
        private String nickname;
    }
}
