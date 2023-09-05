package com.hyun.bookmarkshare.user.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.api.controller.ControllerTestConfig;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.user.service.UserService;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.user.service.response.UserSignoutResponse;
import com.hyun.bookmarkshare.utils.WithCustomAuthUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = {UserRestController.class})
class UserRestControllerTest extends ControllerTestConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @WithCustomAuthUser(email = "test@test.com", userId = 1, role = "ROLE_USER")
    @DisplayName("사용자 정보 호출 API")
    @Test
    void getUserInfoRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI";

        BDDMockito.given(userService.getUserInfo(any(Long.class)))
                .willReturn(UserResponse.builder()
                        .userId(1L)
                        .userEmail("test@test.com")
                        .userName("test")
                        .userState("n")
                        .userRegDate(LocalDateTime.of(2023, 8, 6, 12, 34, 15))
                        .userModDate(LocalDateTime.of(2023, 8, 10, 3, 41, 21))
                        .build());

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/user/info")
                .header(HttpHeaders.AUTHORIZATION, accessTokenFromRequestHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("user-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 ID"),
                                fieldWithPath("data.userEmail").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("data.userPhoneNum").type(JsonFieldType.STRING).description("사용자 전화번호").optional(),
                                fieldWithPath("data.userState").type(JsonFieldType.STRING).description("사용자 상태"),
                                fieldWithPath("data.userGrade").type(JsonFieldType.STRING).description("사용자 등급").optional(),
                                fieldWithPath("data.userRole").type(JsonFieldType.STRING).description("사용자 권한").optional(),
                                fieldWithPath("data.userSocial").type(JsonFieldType.STRING).description("사용자 로그인 종류").optional(),
                                fieldWithPath("data.userRegDate").type(JsonFieldType.STRING).description("사용자 등록일"),
                                fieldWithPath("data.userModDate").type(JsonFieldType.STRING).description("사용자 수정일")
                        )
                ));
    }

    @DisplayName("사용자 회원 탈퇴 API")
    @Test
    void signOutRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI";
        String userEmailFromRequestBody = "test@test.com";
        BDDMockito.given(userService.signOut(any(String.class), any(String.class)))
                .willReturn(UserSignoutResponse.builder()
                        .userEmail("test@test.com")
                        .userState("e")
                        .build());
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .delete("/api/v1/user/sign/out")
                .header(HttpHeaders.AUTHORIZATION, accessTokenFromRequestHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userEmailFromRequestBody))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("회원탈퇴 성공"))
                .andDo(MockMvcRestDocumentationWrapper.document("user-signout",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.userEmail").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("data.userState").type(JsonFieldType.STRING).description("사용자 상태")
                        )
                ));
    }
}