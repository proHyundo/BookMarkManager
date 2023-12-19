package com.hyun.bookmarkshare.social.service.resposne;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class KakaoAccessTokenDto {
    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;
    private String scope;
}
