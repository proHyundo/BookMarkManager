package com.hyun.bookmarkshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

//    Docket: Swagger 설정 Bean
//    useDefaultResponseMessages: Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404). true 로 설정하면 기본 응답 코드를 노출
//    apis: api 스펙이 작성되어 있는 패키지 (Controller) 를 지정
//    paths: apis 에 있는 API 중 특정 path 를 선택

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
