package com.hyun.bookmarkshare.social.service;

import com.hyun.bookmarkshare.social.service.request.KakaoUser;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface SocialLoginService {

    UserLoginResponse loginByKakao(KakaoUser kakaoUser);

    UserResponse signUpByKakao(KakaoUser kakaoUser);

    KakaoUser getKakaoUserInfo(String kakaoAccessToken);

    String getKakaoAccessTokenBy(String kakaoCode);

    void unLinkKakaoAccount(String kakaoAccessToken);
}
