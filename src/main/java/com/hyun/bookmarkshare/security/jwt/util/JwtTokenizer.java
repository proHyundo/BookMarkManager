package com.hyun.bookmarkshare.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenizer {

    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public final static Long ACCESS_TOKEN_EXPIRE_COUNT = 1 * 24 * 60 * 60 * 1000L; // 1 day
    public final static Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L; // 7 days

    // properties 에 선언한 값을 @Value 로 가져올 수 있다.
    public JwtTokenizer(@Value("${property.jwt.secretKey}") String accessSecret, @Value("${property.jwt.refreshKey}") String refreshSecret) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * AccessToken 생성
     */
    public String createAccessToken(Long id, String email, List<String> roles) {
        return createToken(id, email, roles, ACCESS_TOKEN_EXPIRE_COUNT, accessSecret);
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(Long id, String email, List<String> roles) {
        return createToken(id, email, roles, REFRESH_TOKEN_EXPIRE_COUNT, refreshSecret);
    }


    private String createToken(Long id, String email, List<String> roles, Long expire, byte[] secretKey) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("userId", id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    /**
     * 토큰에서 유저 아이디 얻기
     */
    public Long getUserIdFromAccessToken(String accessToken) {
        String[] tokenArr = accessToken.split(" ");
        accessToken = tokenArr[1];
        Claims claims = parseAccessToken(accessToken);
        return Long.valueOf((Integer)claims.get("userId"));
    }

    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    // parse 과정에서 검증에 실패하면 예외발생.
    public Claims parseToken(String token, byte[] secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @param secretKey - byte형식
     * @return Key 형식 시크릿 키
     */
    public static Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }
}
