package com.hyun.bookmarkshare.security.jwt.util;

import com.hyun.bookmarkshare.user.controller.dto.LogoutResponseEntity;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import com.hyun.bookmarkshare.user.exceptions.LogoutExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LogoutProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final UserRepository userRepository;

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
                        UserRefreshToken userRefreshTokenEntity = userRepository.findByRefreshToken(userRefreshToken)
                                .orElseThrow(() -> {throw new LogoutProcessException(LogoutExceptionErrorCode.NO_SUCH_REFRESH_TOKEN);});
                        // DB 에서 RefreshToken 삭제
                        int deletedRows = userRepository.deleteRefreshTokenByUserId(userRefreshTokenEntity.getUserId());
                        if(deletedRows != 1) throw new LogoutProcessException(LogoutExceptionErrorCode.DB_RESULT_WRONG);
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
