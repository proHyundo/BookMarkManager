package com.hyun.bookmarkshare.security.jwt.provider;

import com.hyun.bookmarkshare.security.jwt.token.JwtAuthenticationToken;
import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenizer jwtTokenizer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        // 토큰을 꺼내서 검증한다. 기간이 만료되었는지, 토큰 문자열이 문제가 있는지 등 Exception이 발생한다.
        Claims claims = jwtTokenizer.parseAccessToken(authenticationToken.getToken());
        // 만약, 토큰의 보라색 부분을 암호화 하여 sub 데이터를 집어넣고 싶다면, 복호화하는 코드를 넣어야 한다.
        String email = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
//        String name = claims.get("name", String.class);
        List<GrantedAuthority> authorities = getGrantedAuthorities(claims);

        LoginInfoDto loginInfo = LoginInfoDto.builder().email(email).userId(userId).build();

        // 검증이 완료 되었다면, JwtAuthenticationToken을 새로 만들어 인증된 Token을 권한들과 함께 반환한다.
        return new JwtAuthenticationToken(authorities, loginInfo, null);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(()-> role);
        }
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}