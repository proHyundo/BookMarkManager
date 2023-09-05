package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.api.controller.ControllerTestConfig;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.BookmarkListRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.BookmarkRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser
@WebMvcTest(controllers = {BookmarkRestController.class})
class BookmarkRestControllerTest extends ControllerTestConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @WithCustomAuthUser(email = "test@test.com", userId = 1, role = "ROLE_USER")
    @DisplayName("북마크 리스트 요청 API")
    @Test
    void getBookListRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI";

        BDDMockito.given(bookmarkService.getBookList(any(BookmarkServiceRequestDto.class)))
                .willReturn(List.of(
                        BookmarkResponseDto.builder().bookmarkSeq(1L).userId(1L).folderSeq(1L).bookmarkTitle("bookmark title1")
                            .bookmarkCaption("bookmark caption1").bookmarkUrl("bookmark-tool.com/example1").bookmarkOrder(1L)
                            .bookmarkRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                            .bookmarkModDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                            .bookmarkDelFlag("n").build(),
                        BookmarkResponseDto.builder().bookmarkSeq(2L).userId(1L).folderSeq(1L).bookmarkTitle("bookmark title2")
                            .bookmarkCaption("bookmark caption2").bookmarkUrl("bookmark-tool.com/example2").bookmarkOrder(2L)
                            .bookmarkRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                            .bookmarkModDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                            .bookmarkDelFlag("n").build()
                ));
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/manage/bookmarks/{folderSeq}", 1)
                .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("bookmark-list",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(parameterWithName("folderSeq").description("폴더 시퀀스")),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                                ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data[].bookmarkSeq").description("북마크 시퀀스"),
                                fieldWithPath("data[].userId").description("사용자 시퀀스"),
                                fieldWithPath("data[].folderSeq").description("폴더 시퀀스"),
                                fieldWithPath("data[].bookmarkTitle").description("북마크 제목"),
                                fieldWithPath("data[].bookmarkCaption").description("북마크 설명"),
                                fieldWithPath("data[].bookmarkUrl").description("북마크 URL"),
                                fieldWithPath("data[].bookmarkOrder").description("북마크 순서"),
                                fieldWithPath("data[].bookmarkRegDate").description("북마크 등록일"),
                                fieldWithPath("data[].bookmarkModDate").description("북마크 수정일"),
                                fieldWithPath("data[].bookmarkDelFlag").description("북마크 삭제 여부")
                        )
                ));
    }

}