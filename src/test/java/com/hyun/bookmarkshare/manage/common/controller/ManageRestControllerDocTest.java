package com.hyun.bookmarkshare.manage.common.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.api.controller.ControllerTestConfig;
import com.hyun.bookmarkshare.manage.common.service.ManageService;
import com.hyun.bookmarkshare.manage.common.service.response.FolderWithIncludeBookmarksAndFolders;
import com.hyun.bookmarkshare.utils.WithCustomAuthUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(ManageRestController.class)
class ManageRestControllerDocTest extends ControllerTestConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManageService manageService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;


    @WithCustomAuthUser(email = "test@test.com", userId = 1, role = "ROLE_USER")
    @DisplayName("폴더와 북마크를 포함한 트리 구조 조회")
    @Test
    void getAllFoldersAndBookmarks() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI";
        BDDMockito.given(manageService.getAllFoldersAndBookmarks(1L))
                .willReturn(FolderWithIncludeBookmarksAndFolders.builder().folderSeq(1L).folderParentSeq(0L).userId(1L)
                                .folderName("폴더1").folderCaption("").folderOrder(1L).folderScope("p")
                                .folderRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                                .folderModDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                                .includedBookmarks(List.of())
                                .includedFolders(List.of()).build());

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/manage/folders-bookmarks/tree")
                .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("manage-folders-bookmarks-tree",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Folders and Bookmarks API")
                                .description("트리 구조의 모든 폴더 및 북마크 요청 API"),
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("JWT Access 토큰")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.folderSeq").type(JsonFieldType.NUMBER).description("폴더 식별 번호"),
                                fieldWithPath("data.folderParentSeq").type(JsonFieldType.NUMBER).description("폴더 부모 식별 번호"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 식별 번호"),
                                fieldWithPath("data.folderName").type(JsonFieldType.STRING).description("폴더 이름"),
                                fieldWithPath("data.folderCaption").type(JsonFieldType.STRING).description("폴더 설명"),
                                fieldWithPath("data.folderOrder").type(JsonFieldType.NUMBER).description("폴더 순서"),
                                fieldWithPath("data.folderScope").type(JsonFieldType.STRING).description("폴더 공개 범위"),
                                fieldWithPath("data.folderRegDate").type(JsonFieldType.STRING).description("폴더 등록 일시"),
                                fieldWithPath("data.folderModDate").type(JsonFieldType.STRING).description("폴더 수정 일시"),
                                fieldWithPath("data.includedBookmarks").type(JsonFieldType.ARRAY).description("폴더에 포함된 북마크"),
                                fieldWithPath("data.includedFolders").type(JsonFieldType.ARRAY).description("폴더에 포함된 폴더")
                        )
                ));
    }
}