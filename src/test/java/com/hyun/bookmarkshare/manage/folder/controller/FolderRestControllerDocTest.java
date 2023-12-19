package com.hyun.bookmarkshare.manage.folder.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.api.controller.ControllerTestConfig;
import com.hyun.bookmarkshare.manage.folder.controller.dto.request.*;
import com.hyun.bookmarkshare.manage.folder.service.FolderService;
import com.hyun.bookmarkshare.manage.folder.service.request.*;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderReorderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderSeqResponse;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.utils.WithCustomAuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
* 참고 링크
* https://velog.io/@cieroyou/WebMvcTest와-Spring-Security-함께-사용하기
* https://github.com/senolatac/spring-boot-book-seller
* https://m.blog.naver.com/qjawnswkd/222392092171
* */

// WithMockUser : https://docs.spring.io/spring-security/reference/servlet/test/method.html#test-method-withmockuser
//@Tag(name = "Folder API", description = "폴더 관련 API")
@WithMockUser
@WebMvcTest(FolderRestController.class)
public class FolderRestControllerDocTest extends ControllerTestConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FolderService folderService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    /*
    * 아래 @BeforeEach 메서드는 WithMockUser 어노테이션을 제거하고 실제 토큰 검증까지 수행하기 위해 토큰을 생성하는 메서드 이다.
    * 각 request 마다 Authorization 헤더와 Cookie 에 토큰을 추가해야 한다.
    private String accessToken;

    @BeforeEach
    public void setUp() {
        // assuming you have a JwtTokenUtil class to generate tokens
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        accessToken = jwtTokenUtil.generateAccessToken(1L); // assuming '1' is the user ID
    }
    */

    @WithCustomAuthUser(email = "test@test.com", userId = 1, role = "ROLE_USER")
    @DisplayName("신규 폴더 생성 API")
    @Test
    void addFolderRequest() throws Exception {
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI";

        // given
        FolderCreateRequestDto request = FolderCreateRequestDto.builder()
                .folderSeq(null)
                .userId(null)
                .folderParentSeq(1L)
                .folderName("folderName")
                .folderCaption("folderCaption")
                .folderScope("p")
                .build();
        BDDMockito.given(folderService.createFolder(any(FolderCreateServiceRequestDto.class), any(Long.class)))
                .willReturn(FolderResponse.builder()
                        .folderSeq(1L)
                        .userId(1L)
                        .folderParentSeq(1L)
                        .folderRootFlag("n")
                        .folderName("folderName")
                        .folderCaption("folderCaption")
                        .folderOrder(2L)
                        .folderScope("p")
                        .folderRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .folderModDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .build());
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                    .post("/api/v1/manage/folder/new")
                    .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                    .content(objectMapper.writeValueAsString(request.toServiceRequestDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("신규 폴더 생성 완료"))
                .andDo(MockMvcRestDocumentationWrapper.document("folder-create",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Folder API")
                                .description("신규 폴더 생성 API"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("folderSeq").type(JsonFieldType.NUMBER).description("폴더 식별번호").optional(),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("folderParentSeq").type(JsonFieldType.NUMBER).description("상위 폴더 식별번호"),
                                fieldWithPath("folderName").type(JsonFieldType.STRING).description("폴더명"),
                                fieldWithPath("folderCaption").type(JsonFieldType.STRING).description("폴더 설명"),
                                fieldWithPath("folderScope").type(JsonFieldType.STRING).description("폴더 공개 범위")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.folderSeq").type(JsonFieldType.NUMBER).description("폴더 식별번호"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("data.folderParentSeq").type(JsonFieldType.NUMBER).description("상위 폴더 식별번호"),
                                fieldWithPath("data.folderRootFlag").type(JsonFieldType.STRING).description("루트 폴더 여부"),
                                fieldWithPath("data.folderName").type(JsonFieldType.STRING).description("폴더명"),
                                fieldWithPath("data.folderCaption").type(JsonFieldType.STRING).description("폴더 설명"),
                                fieldWithPath("data.folderOrder").type(JsonFieldType.NUMBER).description("폴더 정렬 순서"),
                                fieldWithPath("data.folderScope").type(JsonFieldType.STRING).description("폴더 공개 범위"),
                                fieldWithPath("data.folderRegDate").type(JsonFieldType.STRING).description("폴더 생성일"),
                                fieldWithPath("data.folderModDate").type(JsonFieldType.STRING).description("폴더 수정일")
                        )
                        ));
    }

    @WithCustomAuthUser(email = "test@test.com", userId = 1, role = "ROLE_USER")
    @DisplayName("상위 폴더 내, 폴더 리스트 요청 API")
    @Test
    void getFolderListRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI";

        BDDMockito.given(folderService.findFolderList(any(FolderListServiceRequestDto.class)))
                .willReturn(List.of(
                        FolderResponse.builder().folderSeq(1L).userId(1L).folderParentSeq(1L).folderRootFlag("n")
                                .folderName("folderName1").folderCaption("folderCaption1").folderOrder(1L).folderScope("p")
                                .folderRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                                .folderModDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                                .build(),
                        FolderResponse.builder().folderSeq(2L).userId(1L).folderParentSeq(1L).folderRootFlag("n")
                                .folderName("folderName2").folderCaption("folderCaption2").folderOrder(2L).folderScope("p")
                                .folderRegDate(LocalDateTime.of(2021, 1, 2, 0, 0, 0))
                                .folderModDate(LocalDateTime.of(2021, 1, 2, 0, 0, 0))
                                .build()
                ));
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/manage/folders/{folderParentSeq}", 1L)
                .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("folder-list",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Folder API")
                                .description("상위 폴더 내, 폴더 리스트 요청 API"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("folderParentSeq").description("상위 폴더 식별번호")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 인증 토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                fieldWithPath("data[].folderSeq").type(JsonFieldType.NUMBER).description("폴더 식별번호"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("data[].folderParentSeq").type(JsonFieldType.NUMBER).description("상위 폴더 식별번호"),
                                fieldWithPath("data[].folderRootFlag").type(JsonFieldType.STRING).description("루트 폴더 여부"),
                                fieldWithPath("data[].folderName").type(JsonFieldType.STRING).description("폴더명"),
                                fieldWithPath("data[].folderCaption").type(JsonFieldType.STRING).description("폴더 설명"),
                                fieldWithPath("data[].folderOrder").type(JsonFieldType.NUMBER).description("폴더 정렬 순서"),
                                fieldWithPath("data[].folderScope").type(JsonFieldType.STRING).description("폴더 공개 범위"),
                                fieldWithPath("data[].folderRegDate").type(JsonFieldType.STRING).description("폴더 생성일"),
                                fieldWithPath("data[].folderModDate").type(JsonFieldType.STRING).description("폴더 수정일")
                        )
                ));
    }

    @DisplayName("폴더 수정 요청 API")
    @Test
    void updateFolderRequest() throws Exception {
        // given
        FolderRequestDto request = FolderRequestDto.builder()
                .folderSeq(1L)
                .userId(1L)
                .folderName("UpdateFolderName")
                .folderCaption("folderCaption")
                .folderScope("p")
                .folderParentSeq(1L)
                .build();
        BDDMockito.given(folderService.updateFolder(any(FolderServiceRequestDto.class)))
                .willReturn(FolderResponse.builder()
                        .folderSeq(1L)
                        .userId(1L)
                        .folderParentSeq(1L)
                        .folderRootFlag("n")
                        .folderName("UpdateFolderName")
                        .folderCaption("folderCaption")
                        .folderOrder(2L)
                        .folderScope("p")
                        .folderRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .folderModDate(LocalDateTime.of(2021, 2, 13, 0, 0, 0))
                        .build());
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .patch("/api/v1/manage/folder/update")
                .content(objectMapper.writeValueAsString(request.toServiceDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("폴더 수정 완료"))
                .andDo(MockMvcRestDocumentationWrapper.document("folder-update",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Folder API")
                                .description("폴더 수정 요청 API"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("folderSeq").type(JsonFieldType.NUMBER).description("폴더 식별번호"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("folderParentSeq").type(JsonFieldType.NUMBER).description("상위 폴더 식별번호"),
                                fieldWithPath("folderName").type(JsonFieldType.STRING).description("폴더명"),
                                fieldWithPath("folderCaption").type(JsonFieldType.STRING).description("폴더 설명"),
                                fieldWithPath("folderScope").type(JsonFieldType.STRING).description("폴더 공개 범위")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.folderSeq").type(JsonFieldType.NUMBER).description("폴더 식별번호"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("data.folderParentSeq").type(JsonFieldType.NUMBER).description("상위 폴더 식별번호"),
                                fieldWithPath("data.folderRootFlag").type(JsonFieldType.STRING).description("루트 폴더 여부"),
                                fieldWithPath("data.folderName").type(JsonFieldType.STRING).description("폴더명"),
                                fieldWithPath("data.folderCaption").type(JsonFieldType.STRING).description("폴더 설명"),
                                fieldWithPath("data.folderOrder").type(JsonFieldType.NUMBER).description("폴더 정렬 순서"),
                                fieldWithPath("data.folderScope").type(JsonFieldType.STRING).description("폴더 공개 범위"),
                                fieldWithPath("data.folderRegDate").type(JsonFieldType.STRING).description("폴더 생성일"),
                                fieldWithPath("data.folderModDate").type(JsonFieldType.STRING).description("폴더 수정일")
                        )
                        ));
    }

    @DisplayName("폴더 삭제 요청 API")
    @Test
    void deleteFolderRequest() throws Exception {
        // given
        FolderDeleteRequestDto request = FolderDeleteRequestDto.builder()
                .folderSeq(1L)
                .userId(1L)
                .build();
        BDDMockito.given(folderService.deleteFolder(any(FolderDeleteServiceRequestDto.class)))
                .willReturn(FolderSeqResponse.builder()
                        .userId(1L)
                        .folderSeqList(List.of(1L, 4L, 5L))
                        .build());
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .delete("/api/v1/manage/folder/delete")
                .content(objectMapper.writeValueAsString(request.toServiceDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("폴더 삭제 완료"))
                .andDo(MockMvcRestDocumentationWrapper.document("folder-delete",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Folder API")
                                .description("폴더 삭제 요청 API"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("folderSeq").type(JsonFieldType.NUMBER).description("폴더 식별번호"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("data.folderSeqList").type(JsonFieldType.ARRAY).description("삭제된 폴더 식별번호 리스트")
                        )
                ));
    }

    @DisplayName("폴더 순서 변경 요청 API")
    @Test
    void reorderFolderRequest() throws Exception {
        // given
        List<FolderReorderRequestDto> requestDtoList = List.of(
                FolderReorderRequestDto.builder()
                        .folderParentSeq(1L)
                        .userId(1L)
                        .folderSeqOrder(List.of(3L, 1L, 2L))
                        .build()
        );
        BDDMockito.given(folderService.updateFolderOrder(any(List.class)))
                .willReturn(List.of(FolderReorderResponse.builder()
                        .userId(1L)
                        .folderParentSeq(1L)
                        .folderSeqOrder(List.of(3L, 1L, 2L))
                        .build()));
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .patch("/api/v1/manage/folder/reorder/multi")
                .content(objectMapper.writeValueAsString(requestDtoList))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("폴더 순서 수정 완료"))
                .andDo(MockMvcRestDocumentationWrapper.document("folder-reorder-multi",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Folder API")
                                .description("클라이언트에서 변경된 순서와 일치하는 폴더 식별 번호 배열을 전달받아, 폴더 순서 변경 요청을 처리한다.").summary("폴더 순서 변경 요청 API"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("[].folderParentSeq").type(JsonFieldType.NUMBER).description("상위 폴더 식별번호"),
                                fieldWithPath("[].userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("[].folderSeqOrder").type(JsonFieldType.ARRAY).description("폴더 순서 리스트")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                fieldWithPath("data.[].userId").type(JsonFieldType.NUMBER).description("사용자 식별번호"),
                                fieldWithPath("data.[].folderParentSeq").type(JsonFieldType.NUMBER).description("상위 폴더 식별번호"),
                                fieldWithPath("data.[].folderSeqOrder").type(JsonFieldType.ARRAY).description("폴더 순서 리스트")
                        )
                ));
    }

}
