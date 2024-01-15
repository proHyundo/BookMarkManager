package com.hyun.bookmarkshare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//
//        registry.addResourceHandler("/docs/**")
//                .addResourceLocations("classpath:/BOOT-INF/resources/static/docs/");

        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/docs/");
//
//        registry.addResourceHandler("/api/docs/**")
//                .addResourceLocations("classpath:/static/docs/");
//
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//
//        registry.addResourceHandler("/docs/**")
//                .addResourceLocations("classpath:/BOOT-INF/resources/static/docs/");
//
//        registry.addResourceHandler("/api/docs/swagger/**")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
