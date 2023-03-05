package com.hyun.bookmarkshare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// spring MVC 에 대한 설정파일.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS 설정. 해당 개념은 반드시 학습해야 한다.
    // http://localhost:3000 -> 9090 api 호출 가능.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "OPTIONS", "DELETE") // "*" 전체허용
                .allowCredentials(true); // ture 시 쿠키 요청을 허용한다(다른 도메인 서버에 인증하는 경우에만 사용해야하며, true 설정시 보안상 이슈가 발생할 수 있다)
    }

}
