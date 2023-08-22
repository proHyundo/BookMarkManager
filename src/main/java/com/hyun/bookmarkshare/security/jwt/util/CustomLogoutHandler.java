package com.hyun.bookmarkshare.security.jwt.util;

import com.hyun.bookmarkshare.exceptions.errorcode.RefreshTokenErrorCode;
import com.hyun.bookmarkshare.user.dao.TokenRepository;
import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import com.hyun.bookmarkshare.exceptions.domain.user.UserLogoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("=====================");
        log.info("Start logout handler");

        // find userRefreshToken from Cookie and delete it from DB
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("userRefreshToken"))
                    .forEach(cookie -> {
                        String userRefreshToken = cookie.getValue();
                        log.info("userRefreshToken: "+userRefreshToken);
                        // DB 에서 RefreshToken 존재 확인
                        UserRefreshToken userRefreshTokenEntity = tokenRepository.findByRefreshToken(userRefreshToken)
                                .orElseThrow(() -> new UserLogoutException(RefreshTokenErrorCode.RT_NOT_FOUND));
                        // DB 에서 RefreshToken 삭제
                        int deletedRows = tokenRepository.deleteRefreshTokenByUserId(userRefreshTokenEntity.getUserId());
                        if(deletedRows != 1) throw new UserLogoutException(RefreshTokenErrorCode.RT_DB_RESULT_WRONG);
                    });
        }

        // Authorization 헤더에서 Bearer AccessToken 토큰 추출
        String authorization = request.getHeader("Authorization");
        String userAccessToken = "";
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")){
            userAccessToken = authorization.split(" ")[1];
            log.info("userAccessToken: "+userAccessToken);
        }

        log.info("End logout handler");
        log.info("=====================");
    }
}
