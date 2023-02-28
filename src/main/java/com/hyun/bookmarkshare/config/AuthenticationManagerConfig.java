package com.hyun.bookmarkshare.config;

import com.hyun.bookmarkshare.security.jwt.filter.JwtAuthenticationFilter;
import com.hyun.bookmarkshare.security.jwt.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig extends AbstractHttpConfigurer<AuthenticationManagerConfig, HttpSecurity> {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    /**
     * 해당 메서드는 AuthenticationManager가 JWT 토큰을 파싱하여 인증을 수행하기 위해 별도로 생성한 JwtAuthenticationProvider를
     * 사용할 수 있도록 설정한다.
     * */
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        // UsernamePasswordAuthenticationFilter 필터 앞에 JwtAuthenticationFilter 필터를 생성.
        // 이때 JwtAuthenticationFilter 필터는 AuthenticationManager 를 사용하도록 설정.
        // AuthenticationManager JwtAuthenticationProvider 를 사용하도록 설정.
        builder.addFilterBefore(
                        new JwtAuthenticationFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(jwtAuthenticationProvider);
    }
}
