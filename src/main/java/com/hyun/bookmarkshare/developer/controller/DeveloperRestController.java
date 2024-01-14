package com.hyun.bookmarkshare.developer.controller;

import com.hyun.bookmarkshare.exceptions.errorcode.UserErrorCode;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;

@Slf4j
@NoArgsConstructor
@RestController
public class DeveloperRestController {

    @Autowired
    private Environment environment;

    // 참고 링크 : https://inspeerity.com/blog/setting-default-spring-profile-for-tests-with-override-option
    @GetMapping("/api/test/developer/whoami/profile")
    public ResponseEntity<String> getProfile() {
        System.out.println("현재 실행중인 profile >> " + Arrays.toString(environment.getActiveProfiles()) + "입니다.");
        return ResponseEntity
                .ok()
                .body("현재 실행중인 profile >> " + Arrays.toString(environment.getActiveProfiles()) + "입니다.");
    }

    // 참고 링크 : https://www.baeldung.com/spring-boot-running-port
    @GetMapping("/api/test/developer/whoami/port")
    public ResponseEntity<String> getPort() {
        System.out.println("현재 실행중인 port >> " + environment.getProperty("local.server.port") + "입니다.");
        return ResponseEntity
                .ok()
                .body("현재 실행중인 port >> " + environment.getProperty("local.server.port") + "입니다.");
    }

    @GetMapping("/api/test/developer/exception/user/{userId}")
    public ApiResponse<UserResponse> getUserException(@PathVariable("userId") Long userId){
        if (userId == 99) {
            throw new IllegalArgumentException(UserErrorCode.USER_NOT_FOUND.getMessage());
        }
        return ApiResponse.of(HttpStatus.OK, "유저 조회 실패", UserResponse.builder().userId(userId).build());
    }

    @GetMapping("/api/test/user/info")
    public ApiResponse<String> getUserInfoWithPrinciple(Principal principal){
        log.info("principal.getName() : {}", principal.getName());
        log.info("SecurityContextHolder.getContext().getAuthentication().getName() : {}", SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("SecurityContextHolder.getContext().getAuthentication().getPrincipal() : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        LoginInfoDto loginInfoDto = (LoginInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("loginInfoDto.toString() : {}", loginInfoDto.toString());
        return ApiResponse.of(HttpStatus.OK, principal.toString());
    }

    @GetMapping("/api/test/user/info/v2")
    public ApiResponse<String> getUserInfoWithAnnotation(@AuthenticationPrincipal LoginInfoDto loginInfoDto){
        log.info("loginInfoDto.toString() : {}", loginInfoDto.toString());
        return ApiResponse.of(HttpStatus.OK, null);
    }

    @GetMapping("/api/docs/open-api-3.0.1.json")
    public String getSwaggerAsset(){
        return "classpath:/docs/open-api-3.0.1.json";
    }


}
