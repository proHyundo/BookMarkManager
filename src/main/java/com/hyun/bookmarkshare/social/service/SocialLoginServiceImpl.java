package com.hyun.bookmarkshare.social.service;

import com.google.gson.Gson;
import com.hyun.bookmarkshare.social.exception.SocialLoginErrorCode;
import com.hyun.bookmarkshare.social.exception.SocialLoginException;
import com.hyun.bookmarkshare.social.service.request.KakaoUser;
import com.hyun.bookmarkshare.social.service.resposne.KakaoAccessTokenDto;
import com.hyun.bookmarkshare.social.service.resposne.KakaoUserInfoDto;
import com.hyun.bookmarkshare.user.service.UserService;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSocialSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

// 선택 사항 사용자 미동의 또는 사용자가 카카오에 제공하지 않은 정보인 경우 예외처리
// 추가 항목 동의 받기기
// https://velog.io/@sumong/Flutter에서-카카오-로그인-구현하기
// https://jonguk.tistory.com/m/entry/SpringBoot-React-카카오-소셜-로그인-REST-API-방식-구현-2-카카오-소셜-로그인-구조-분석

// 1. 카카오 토큰 발급
// 2. 카카오 토큰으로 카카오 유저 정보 가져오기
// 3. 카카오 유저 정보로 회원가입 또는 로그인 처리

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialLoginServiceImpl implements SocialLoginService{

    @Value("${property.social.kakao.rest-api-key}")
    private String kakaoRestApiKey;

    @Value("${property.social.kakao.redirect-url}")
    private String kakaoRedirectUri;

    @Value("${property.social.kakao.password-suffix}")
    private String kakaoPasswordSuffix;

    private final UserService userService;
    private final Gson gson;

    @Override
    public UserLoginResponse loginByKakao(KakaoUser kakaoUser) {
        Boolean isRegistered = userService.checkRegisteredEmail(kakaoUser.getKakaoUserEmail(), "kakao");
        if (!isRegistered) {
            signUpByKakao(kakaoUser);
        }

        return userService.loginProcess(LoginServiceRequestDto.builder()
                        .email(kakaoUser.getKakaoUserEmail())
                        .pwd(kakaoUser.getKakaoUserPassword())
                        .build());
    }

    @Override
    public UserResponse signUpByKakao(KakaoUser kakaoUser) {
        Boolean isRegistered = userService.checkRegisteredEmail(kakaoUser.getKakaoUserEmail(), "kakao");
        if(isRegistered){
            throw new IllegalArgumentException("이미 가입된 사용자입니다.");
        }

        return userService.signUpBySocialAccount(UserSocialSignUpServiceRequestDto.builder()
                        .socialUserEmail(kakaoUser.getKakaoUserEmail())
                        .socialUserPwd(kakaoUser.getKakaoUserPassword())
                        .socialUserName(kakaoUser.getKakaoUserNickname())
                        .build());
    }

    @Override
    public void unLinkKakaoAccount(String kakaoAccessToken){
        log.info("unlinkKakaoAccount method start");
        log.info("kakaoAccessToken at unLinkKakaoAccount method = " + kakaoAccessToken.isEmpty());
        RestTemplate restTemplate = new RestTemplate();
        String unlinkUrl = "https://kapi.kakao.com/v1/user/unlink";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(unlinkUrl, request, String.class);

        log.info("unlink " + response.getBody());
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("카카오 API 요청 실패 : 카카오 연동 해제 실패");
            throw new IllegalArgumentException("카카오 API 요청 실패 : 카카오 연동 해제 실패");
        }
    }

    @Override
    public String getKakaoAccessTokenBy(String kakaoCode) {

        String kakaoAccessToken = "";
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("https://kauth.kakao.com/oauth/token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoRestApiKey);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", kakaoCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            KakaoAccessTokenDto accessTokenInfo = gson.fromJson(response.getBody(), KakaoAccessTokenDto.class);
            kakaoAccessToken = accessTokenInfo.getAccess_token();
        }

        return kakaoAccessToken;
    }

    @Override
    public KakaoUser getKakaoUserInfo(String kakaoAccessToken){
        log.info("getKakaoUserInfo method start");
        KakaoUser kakaoUser = null;

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("property_keys", "kakao_account.email");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            KakaoUserInfoDto kakaoUserInfoDto = gson.fromJson(response.getBody(), KakaoUserInfoDto.class);

            log.info("kakaoUserInfoDto : {}", kakaoUserInfoDto.toString());

            checkRequiredKakaoUserInfo(kakaoUserInfoDto, kakaoAccessToken);

            kakaoUser = KakaoUser.builder()
                    .kakaoUserId(kakaoUserInfoDto.getId())
                    .kakaoUserEmail(kakaoUserInfoDto.getKakao_account().getEmail())
                    .kakaoUserPassword(addKakaoUserPasswordSuffix(kakaoUserInfoDto.getId()))
                    .kakaoUserNickname(kakaoUserInfoDto.getProperties().getNickname())
                    .build();

            log.info("kakaoUser 정보 가져오기 성공 : {}", kakaoUser.getKakaoUserEmail());
        }

        return kakaoUser;
    }

    private void checkRequiredKakaoUserInfo(KakaoUserInfoDto kakaoUserInfoDto, String kakaoAccessToken) {
        log.info("checkRequiredKakaoUserInfo private method start", kakaoUserInfoDto.toString());
        if(kakaoUserInfoDto.getKakao_account().getEmail() == null || kakaoUserInfoDto.getKakao_account().getEmail().equals("")) {
            log.info("카카오 로그인 실패 : 이메일 정보가 없습니다.");
            unLinkKakaoAccount(kakaoAccessToken);
            throw new SocialLoginException(SocialLoginErrorCode.KAKAO_LOGIN_NO_EMAIL,"카카오 로그인 실패 : 이메일 정보가 없습니다.");
        }
    }

    private String addKakaoUserPasswordSuffix(Long kakaoUserId){
        StringBuffer newPwd = new StringBuffer();
        newPwd.append(kakaoUserId);
        newPwd.append(kakaoPasswordSuffix);
        return String.valueOf(newPwd);
    }


}
