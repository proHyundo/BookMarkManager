package com.hyun.bookmarkshare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger UI의 JSON 파일 경로를 설정합니다.
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/docs/");

        // Swagger UI의 리소스 핸들러를 설정합니다.
        registry.addResourceHandler("/api/docs/swagger/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
