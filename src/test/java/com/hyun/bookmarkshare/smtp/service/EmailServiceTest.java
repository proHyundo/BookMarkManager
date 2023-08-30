package com.hyun.bookmarkshare.smtp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    @DisplayName("이메일 컨텐츠 생성 테스트")
    @Test
    void createContent() throws IOException {
        String htmlFilePath = "src/main/resources/static/email/signupTemplate.html";
        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)), StandardCharsets.UTF_8);
        System.out.println("htmlContent = " + htmlContent);
    }

}