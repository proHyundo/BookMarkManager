package com.hyun.bookmarkshare.security.jwt.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {

    private CustomHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new CustomHttpServletRequest("/signup/email/verification");
    }

    @DisplayName("허용된 요청인지 확인 - List")
    @Test
    void isPermitRequestUsingList() {
        // measure time
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // given
        String[] permitUrls = {"/signup/email/verification", "/signup/email/verification/check"};

        // when
        boolean resultA = Arrays.asList(permitUrls).contains(request.getRequestURI());

        // measure time
        stopWatch.stop();
        System.out.println("stopWatch.getNanos = " + stopWatch.getLastTaskTimeNanos());

        // then
        Assertions.assertThat(resultA).isTrue();
    }

    @DisplayName("허용된 요청인지 확인 - Stream")
    @Test
    void isPermitRequestUsingStream(){
        // measure time
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // given
        String[] permitUrls = {"/signup/email/verification", "/signup/email/verification/check"};

        // when
        boolean resultB = Arrays.stream(permitUrls).anyMatch(request.getRequestURI()::equals);

        // measure time
        stopWatch.stop();
        System.out.println("stopWatch.getNanos = " + stopWatch.getLastTaskTimeNanos());

        // then
        Assertions.assertThat(resultB).isTrue();

    }
}

class CustomHttpServletRequest{
    private String requestURI;

    public CustomHttpServletRequest(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestURI() {
        return requestURI;
    }
}