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
                .allowedMethods("GET", "POST", "PATCH", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true);
    }

}
