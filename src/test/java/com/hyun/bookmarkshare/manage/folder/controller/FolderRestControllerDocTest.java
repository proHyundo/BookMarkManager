package com.hyun.bookmarkshare.manage.folder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderCreateRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.FolderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
* 참고 링크
* https://velog.io/@cieroyou/WebMvcTest와-Spring-Security-함께-사용하기
* https://github.com/senolatac/spring-boot-book-seller
* https://m.blog.naver.com/qjawnswkd/222392092171
* */

// WithMockUser : https://docs.spring.io/spring-security/reference/servlet/test/method.html#test-method-withmockuser
@WithMockUser
@WebMvcTest(FolderRestController.class)
public class FolderRestControllerDocTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FolderService folderService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    // 아래 주석은 WithMockUser 어노테이션을 제거하고 실제 토큰 검증까지 수행하기 위해 토큰을 생성하는 메서드 이다.
    // 각 request 마다 Authorization 헤더와 Cookie 에 토큰을 추가해야 한다.
//    private String accessToken;
//
//    @BeforeEach
//    public void setUp() {
//        // assuming you have a JwtTokenUtil class to generate tokens
//        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//        accessToken = jwtTokenUtil.generateAccessToken(1L); // assuming '1' is the user ID
//    }

    @DisplayName("create new folder.")
    @Test
    void addFolderRequest() throws Exception {
        // given
        FolderCreateRequestDto request = FolderCreateRequestDto.builder()
                .folderSeq(null)
                .userId(1L)
                .folderParentSeq(1L)
                .folderName("folderName")
                .folderCaption("folderCaption")
                .folderScope("p")
                .build();

        // when // then
        mockMvc.perform(post("/api/v1/manage/folder/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
