package com.hyun.bookmarkshare.user.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.api.controller.ControllerTestConfig;
import com.hyun.bookmarkshare.user.controller.dto.request.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.request.UserSignUpRequestDto;
import com.hyun.bookmarkshare.user.service.UserService;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.utils.ApiResponseWithCookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// REST Docs Documentation : https://docs.spring.io/spring-restdocs/docs/2.0.7.RELEASE/reference/html5/#introduction
// restdocs-api-spec Documentation : https://github.com/ePages-de/restdocs-api-spec
// https://lemontia.tistory.com/1088
// https://ttl-blog.tistory.com/760
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = {UserPermitRestController.class})
class UserPermitRestControllerTest extends ControllerTestConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("사용자 로그인 API")
    @Test
    void loginRequest() throws Exception {
        // given
        LoginRequestDto requestDto = LoginRequestDto.builder().email("test@test.com").pwd("1111").build();
        LoginServiceRequestDto serviceRequestDto = requestDto.toServiceDto();

        BDDMockito.given(userService.loginProcess(any(LoginServiceRequestDto.class)))
                .willReturn(UserLoginResponse.builder()
                        .userId(1L)
                        .userEmail("test@test.com")
                        .userRole("ROLE_USER")
                        .userAccessToken("testAccessToken")
                        .userRefreshToken("testRefreshToken")
                        .build());
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/user/login")
                        .content(objectMapper.writeValueAsString(serviceRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("로그인 성공"))
                .andDo(MockMvcRestDocumentationWrapper.document("user-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("pwd").type(JsonFieldType.STRING).description("사용자 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 ID"),
                                fieldWithPath("data.userEmail").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("data.userRole").type(JsonFieldType.STRING).description("사용자 권한"),
                                fieldWithPath("data.userAccessToken").type(JsonFieldType.STRING).description("사용자 Access 토큰"),
                                fieldWithPath("data.userRefreshToken").type(JsonFieldType.STRING).description("사용자 Refresh 토큰"),
                                fieldWithPath("cookie").type(JsonFieldType.OBJECT).description("쿠키"),
                                fieldWithPath("cookie.name").type(JsonFieldType.STRING).description("쿠키 이름"),
                                fieldWithPath("cookie.value").type(JsonFieldType.STRING).description("쿠키 값"),
                                fieldWithPath("cookie.maxAge").type(JsonFieldType.STRING).description("쿠키 만료 시간"),
                                fieldWithPath("cookie.domain").type(JsonFieldType.STRING).description("쿠키 도메인"),
                                fieldWithPath("cookie.path").type(JsonFieldType.STRING).description("쿠키 경로"),
                                fieldWithPath("cookie.secure").type(JsonFieldType.BOOLEAN).description("쿠키 보안 여부"),
                                fieldWithPath("cookie.httpOnly").type(JsonFieldType.BOOLEAN).description("쿠키 HTTP 여부"),
                                fieldWithPath("cookie.sameSite").type(JsonFieldType.STRING).description("쿠키 SameSite 여부")
                        )
                ));
    }

    @DisplayName("사용자 회원가입 API")
    @Test
    void signupRequest() throws Exception {
        // given
        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder()
                .userId(null)
                .userEmail("test@test.com")
                .userPwd("1111")
                .userName("test")
                .userPhoneNum("01012341234")
                .userGender("m")
                .emailValidationCode("12345678")
                .build();
        BDDMockito.given(userService.signUp(any(UserSignUpServiceRequestDto.class)))
                .willReturn(UserResponse.builder()
                        .userId(1L)
                        .userEmail("test@test.com")
                        .userName("test")
                        .userState("n")
                        .userRegDate(LocalDateTime.of(2023, 8, 8, 12, 34, 0))
                        .userModDate(LocalDateTime.of(2023, 8, 8, 12, 34, 0))
                        .build());

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/user/signup")
                .content(objectMapper.writeValueAsString(requestDto.toServiceDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("회원가입 성공"))
                .andDo(MockMvcRestDocumentationWrapper.document("user-signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NULL).description("사용자 ID"),
                                fieldWithPath("userEmail").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("userPwd").type(JsonFieldType.STRING).description("사용자 비밀번호"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("userPhoneNum").type(JsonFieldType.STRING).description("사용자 전화번호"),
                                fieldWithPath("userGender").type(JsonFieldType.STRING).description("사용자 성별"),
                                fieldWithPath("emailValidationCode").type(JsonFieldType.STRING).description("이메일 인증 코드")
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

    @DisplayName("사용자 로그인 연장 API")
    @Test
    void refreshRequest() throws Exception {
        // 참고 링크 : https://0soo.tistory.com/211
        // given
        MockCookie mockCookie = new MockCookie("userRefreshToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI");
        BDDMockito.given(userService.extendLoginState(any(String.class)))
                .willReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.HgJhtt0PLS3wX5xjFrUHWfthSBYZmHzdw4-Hlsf-HQA");
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/user/refresh")
                .cookie(mockCookie)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("로그인 연장 성공"))
                .andDo(MockMvcRestDocumentationWrapper.document("user-refresh-token",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("사용자 Access 토큰")
                        )
                ));
    }

    @DisplayName("중복 Email 여부 확인 API")
    @Test
    void checkRegisteredEmailRequest() throws Exception {
        // given
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("userEmail", "fresh@test.com");
        BDDMockito.given(userService.checkDuplicateEmail(any(String.class)))
                .willReturn(false);
        // when  // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/user/email/check")
                .content(objectMapper.writeValueAsString(requestMap))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("사용 가능한 이메일입니다."))
                .andDo(MockMvcRestDocumentationWrapper.document("user-check-duplicate-email",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userEmail").type(JsonFieldType.STRING).description("중복 확인 대상 이메일")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("사용 가능한 이메일")
                        )

                ));

    }
}