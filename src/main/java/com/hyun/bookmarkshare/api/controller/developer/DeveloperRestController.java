package com.hyun.bookmarkshare.api.controller.developer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
public class DeveloperRestController {

    private final Environment environment;
    private ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping("/api/v1/developer/whoami/profile")
    public ResponseEntity<String> getProfile() {
        return ResponseEntity
                .ok()
                .body("현재 실행중인 profile >> " + Arrays.toString(environment.getActiveProfiles()) + "입니다.");
    }

    // 참고 링크 : https://www.baeldung.com/spring-boot-running-port
    @GetMapping("/api/v1/developer/whoami/port")
    public ResponseEntity<String> getPort() {
        return ResponseEntity
                .ok()
                .body("현재 실행중인 port >> " + webServerAppCtxt.getWebServer().getPort() + "입니다.");
    }

}
