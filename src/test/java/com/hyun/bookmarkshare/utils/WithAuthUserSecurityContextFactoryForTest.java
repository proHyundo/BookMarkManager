package com.hyun.bookmarkshare.utils;

import com.hyun.bookmarkshare.security.jwt.token.JwtAuthenticationToken;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithAuthUserSecurityContextFactoryForTest implements WithSecurityContextFactory<WithCustomAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomAuthUser annotation) {
        String email = annotation.email();
        Long userId = Long.valueOf(annotation.userId());
        String role = annotation.role();

        LoginInfoDto loginInfoDto = LoginInfoDto.builder()
                .userId(userId)
                .email(email)
                .build();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(()-> role);

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(authorities, loginInfoDto, null);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }
}
