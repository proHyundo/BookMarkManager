package com.hyun.bookmarkshare.config;

import com.hyun.bookmarkshare.security.jwt.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // https://stackoverflow.com/questions/72769680/intellij-idea-error-could-not-autowire-no-beans-of-httpsecurity-type-found
public class SecurityConfig {

    private final AuthenticationManagerConfig authenticationManagerConfig;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * 스프링시큐리티의 설정을 할 수 있다.
     * 공식문서 링크 : https://docs.spring.io/spring-security/reference/servlet/architecture.html
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // https://hou27.tistory.com/entry/Spring-Security-JWT
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .formLogin().disable()
            .csrf().disable()
            .cors()
        .and()
            .apply(authenticationManagerConfig)
        .and()
            .httpBasic().disable()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .mvcMatchers("/signup", "/login", "/refresh/login-state").permitAll()
            .mvcMatchers(GET,"/**").hasAnyRole("USER", "MANAGER", "ADMIN")
            .mvcMatchers(POST, "/**").hasAnyRole("USER", "MANAGER", "ADMIN")
            .mvcMatchers(DELETE, "/**").hasAnyRole("USER", "MANAGER", "ADMIN")
            .mvcMatchers(PATCH, "/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .anyRequest().hasAnyRole()
        .and()
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint);

        return http.build();

//        return http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // (정책)세션을 생성 & 사용하지 않음
//                .and()
//                .formLogin().disable() // form 형식 로그인을 제한. 직접 id, password를 입력받아 JWT 토큰을 리턴하는 API 생성.
//                .csrf().disable() // CSRF공격을 막기 위한 방법. 켜주는 것이 더 안전할 수 있지만, 설정이 불편하므로 disable
//                .cors() //.configurationSource(corsConfigurationSource())
//                .and()
//                .apply(authenticationManagerConfig)
//                .and()
//                .httpBasic().disable() // Http basic Auth 기반으로 로그인 인증창이 뜸. disable 시에 인증창 뜨지 않음.
//                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // Preflight 요청은 허용한다. https://velog.io/@xonic789/CORS-preflight-요청에-대해-401-에러-해결Spring-security-config
//                .mvcMatchers( "/signup", "/login", "/refresh/login-state").permitAll() // 해당 요청들은 인증 없이 접근 허용
////                .mvcMatchers(GET, "/**").permitAll()
//                .mvcMatchers(GET,"/**").hasAnyRole("USER", "MANAGER", "ADMIN") // 다수의 role 이름을 받아들여서 access 시킨다.
//                .mvcMatchers(POST,"/**").hasAnyRole("USER", "MANAGER", "ADMIN") // 즉, 모든 URL 은 인증 후 해당 권한을 가진 사용자만 접근 허용
////                .mvcMatchers(POST,"answers/**").hasAnyRole("USER", "MANAGER", "ADMIN")
//                .anyRequest().hasAnyRole()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(customAuthenticationEntryPoint) // Response에 401이 떨어질만한 에러가 발생할 경우 해당로직을 타게되어, commence라는 메소드를 실행
//                .and()
//                .build();
    }


    // <<Advanced>> Security Cors로 변경 시도
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowCredentials(true); // 이거 빼면 된다
        // https://gareen.tistory.com/66
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.setAllowedMethods(List.of("GET","POST","DELETE","PATCH","OPTION","PUT"));
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // 암호를 암호화하거나, 사용자가 입력한 암호가 기존 암호랑 일치하는지 검사할 때 이 Bean을 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
