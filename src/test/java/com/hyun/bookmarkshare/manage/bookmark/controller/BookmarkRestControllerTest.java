package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.api.controller.ControllerTestConfig;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.BookmarkCreateRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.BookmarkReorderRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.BookmarkRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.BookmarkUpdateRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkUpdateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkSeqResponse;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Bookmark API")
                                .description("북마크 리스트 요청 API"),
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(parameterWithName("folderSeq").description("폴더 시퀀스")),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                                ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
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

    @WithCustomAuthUser(email = "test@test.com", userId = 1, role = "ROLE_USER")
    @DisplayName("단일 북마크 정보 요청 API")
    @Test
    void getBookmarkRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJ0ZXN0IiwidXNlcmlkIjoxfQ.eMKhy-XdJmhuS2QeH1fjycXLS4lucpSa0D56JFMr0fI";

        BDDMockito.given(bookmarkService.getBookmark(any(BookmarkServiceRequestDto.class)))
                .willReturn(BookmarkResponseDto.builder()
                        .bookmarkSeq(1L).userId(1L).folderSeq(1L).bookmarkTitle("bookmark title1")
                        .bookmarkCaption("bookmark caption1").bookmarkUrl("bookmark-tool.com/example1").bookmarkOrder(1L)
                        .bookmarkRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .bookmarkModDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .bookmarkDelFlag("n").build());

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/manage/bookmark/{bookmarkSeq}", 1L)
                .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("bookmark-single-info",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Bookmark API")
                                .description("단일 북마크 정보 요청 API"),
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(parameterWithName("bookmarkSeq").description("북마크 식별자")),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.bookmarkSeq").description("북마크 시퀀스"),
                                fieldWithPath("data.userId").description("사용자 시퀀스"),
                                fieldWithPath("data.folderSeq").description("폴더 시퀀스"),
                                fieldWithPath("data.bookmarkTitle").description("북마크 제목"),
                                fieldWithPath("data.bookmarkCaption").description("북마크 설명"),
                                fieldWithPath("data.bookmarkUrl").description("북마크 URL"),
                                fieldWithPath("data.bookmarkOrder").description("북마크 순서"),
                                fieldWithPath("data.bookmarkRegDate").description("북마크 등록일"),
                                fieldWithPath("data.bookmarkModDate").description("북마크 수정일"),
                                fieldWithPath("data.bookmarkDelFlag").description("북마크 삭제 여부")
                        )
                ));
    }

    @WithCustomAuthUser(email = "test@test.com" , userId = 1, role = "ROLE_USER")
    @DisplayName("북마크 신규 등록 API")
    @Test
    void createBookmarkRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoxLCJlbWFpbCI6InRlc3RAdGVzdC5jb20ifSwicm9sZXMiOiJST0xFX1RFU1QiLCJ1c2VyaWQiOjF9.jW8sg5JmIqST_2WAxXT89AthwRG921dBycV_xqkWW60";
        BookmarkCreateRequestDto requestDto = BookmarkCreateRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .bookmarkTitle("bookmark title1")
                .bookmarkCaption("bookmark caption1")
                .bookmarkUrl("https://bookmark-tool.com/sample")
                .build();

        BDDMockito.given(bookmarkService.createBookmark(any(BookmarkCreateServiceRequestDto.class)))
                .willReturn(BookmarkResponseDto.builder()
                        .bookmarkSeq(1L).userId(1L).folderSeq(1L).bookmarkTitle("bookmark title1")
                        .bookmarkCaption("bookmark caption1").bookmarkUrl("bookmark-tool.com/example1").bookmarkOrder(1L)
                        .bookmarkRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .bookmarkModDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .bookmarkDelFlag("n").build());


        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/manage/bookmark/new")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("bookmark-create",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Bookmark API")
                                .description("북마크 신규 생성 요청 API"),
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                        ),
                        requestFields(
                                fieldWithPath("bookmarkSeq").description("북마크 시퀀스").type(JsonFieldType.NUMBER).optional(),
                                fieldWithPath("userId").description("사용자 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("folderSeq").description("폴더 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("bookmarkTitle").description("북마크 제목").type(JsonFieldType.STRING),
                                fieldWithPath("bookmarkCaption").description("북마크 설명").type(JsonFieldType.STRING),
                                fieldWithPath("bookmarkUrl").description("북마크 URL").type(JsonFieldType.STRING)
                        ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드").type(JsonFieldType.NUMBER),
                                fieldWithPath("status").description("응답 상태").type(JsonFieldType.STRING),
                                fieldWithPath("message").description("응답 메시지").type(JsonFieldType.STRING),
                                fieldWithPath("timestamp").description("응답 시간").type(JsonFieldType.STRING),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.bookmarkSeq").description("북마크 시퀀스"),
                                fieldWithPath("data.userId").description("사용자 시퀀스"),
                                fieldWithPath("data.folderSeq").description("폴더 시퀀스"),
                                fieldWithPath("data.bookmarkTitle").description("북마크 제목"),
                                fieldWithPath("data.bookmarkCaption").description("북마크 설명"),
                                fieldWithPath("data.bookmarkUrl").description("북마크 URL"),
                                fieldWithPath("data.bookmarkOrder").description("북마크 순서"),
                                fieldWithPath("data.bookmarkRegDate").description("북마크 등록일"),
                                fieldWithPath("data.bookmarkModDate").description("북마크 수정일"),
                                fieldWithPath("data.bookmarkDelFlag").description("북마크 삭제 여부")
                        )
                ));
    }

    @DisplayName("북마크 정보 수정 API")
    @Test
    void updateBookmarkRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoxLCJlbWFpbCI6InRlc3RAdGVzdC5jb20ifSwicm9sZXMiOiJST0xFX1RFU1QiLCJ1c2VyaWQiOjF9.jW8sg5JmIqST_2WAxXT89AthwRG921dBycV_xqkWW60";
        BookmarkUpdateRequestDto requestDto = BookmarkUpdateRequestDto.builder()
                .bookmarkSeq(1L)
                .userId(1L)
                .folderSeq(1L)
                .bookmarkTitle("bookmark updated title")
                .bookmarkCaption("bookmark updated caption")
                .bookmarkUrl("https://bookmark-tool.com/update-sample")
                .build();
        BDDMockito.given(bookmarkService.updateBookmark(any(BookmarkUpdateServiceRequestDto.class)))
                .willReturn(BookmarkResponseDto.builder()
                        .bookmarkSeq(1L).userId(1L).folderSeq(1L).bookmarkTitle("bookmark updated title")
                        .bookmarkCaption("bookmark updated caption").bookmarkUrl("bookmark-tool.com/update-sample").bookmarkOrder(1L)
                        .bookmarkRegDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                        .bookmarkModDate(LocalDateTime.of(2021, 1, 2, 1, 42, 0))
                        .bookmarkDelFlag("n").build());
        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/manage/bookmark")
                        .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("bookmark-update",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Bookmark API")
                                .description("북마크 정보 수정 요청 API"),
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                        ),
                        requestFields(
                                fieldWithPath("bookmarkSeq").description("북마크 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("userId").description("사용자 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("folderSeq").description("폴더 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("bookmarkTitle").description("북마크 제목").type(JsonFieldType.STRING),
                                fieldWithPath("bookmarkCaption").description("북마크 설명").type(JsonFieldType.STRING),
                                fieldWithPath("bookmarkUrl").description("북마크 URL").type(JsonFieldType.STRING)
                        ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드").type(JsonFieldType.NUMBER),
                                fieldWithPath("status").description("응답 상태").type(JsonFieldType.STRING),
                                fieldWithPath("message").description("응답 메시지").type(JsonFieldType.STRING),
                                fieldWithPath("timestamp").description("응답 시간").type(JsonFieldType.STRING),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.bookmarkSeq").description("북마크 시퀀스"),
                                fieldWithPath("data.userId").description("사용자 시퀀스"),
                                fieldWithPath("data.folderSeq").description("폴더 시퀀스"),
                                fieldWithPath("data.bookmarkTitle").description("북마크 제목"),
                                fieldWithPath("data.bookmarkCaption").description("북마크 설명"),
                                fieldWithPath("data.bookmarkUrl").description("북마크 URL"),
                                fieldWithPath("data.bookmarkOrder").description("북마크 순서"),
                                fieldWithPath("data.bookmarkRegDate").description("북마크 등록일"),
                                fieldWithPath("data.bookmarkModDate").description("북마크 수정일"),
                                fieldWithPath("data.bookmarkDelFlag").description("북마크 삭제 여부")
                        )

                ));
    }

    @DisplayName("북마크 정렬 순서 변경 API")
    @Test
    void updateBookmarkOrderRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoxLCJlbWFpbCI6InRlc3RAdGVzdC5jb20ifSwicm9sZXMiOiJST0xFX1RFU1QiLCJ1c2VyaWQiOjF9.jW8sg5JmIqST_2WAxXT89AthwRG921dBycV_xqkWW60";
        List<BookmarkReorderRequestDto> requestDtoList = List.of(BookmarkReorderRequestDto.builder()
                .userId(1L).folderSeq(1L).bookmarkSeqOrder(List.of(1L, 3L, 2L)).build());
        BDDMockito.given(bookmarkService.updateBookmarkOrder(any(List.class)))
                .willReturn(true);

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/manage/bookmark/reorder")
                .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDtoList))
                .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("bookmark-reorder",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Bookmark API")
                                .description("북마크 정렬 순서 변경 요청 API"),
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                        ),
                        requestFields(
                                fieldWithPath("[].userId").description("사용자 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("[].folderSeq").description("폴더 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("[].bookmarkSeqOrder").description("북마크 시퀀스 순서").type(JsonFieldType.ARRAY)
                        ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드").type(JsonFieldType.NUMBER),
                                fieldWithPath("status").description("응답 상태").type(JsonFieldType.STRING),
                                fieldWithPath("message").description("응답 메시지").type(JsonFieldType.STRING),
                                fieldWithPath("timestamp").description("응답 시간").type(JsonFieldType.STRING),
                                fieldWithPath("data").description("응답 데이터")
                        )
                ));
    }

    @DisplayName("단일 북마크 삭제 API")
    @Test
    void deleteBookmarkRequest() throws Exception {
        // given
        String accessTokenFromRequestHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidXNlcklkIjoxLCJlbWFpbCI6InRlc3RAdGVzdC5jb20ifSwicm9sZXMiOiJST0xFX1RFU1QiLCJ1c2VyaWQiOjF9.jW8sg5JmIqST_2WAxXT89AthwRG921dBycV_xqkWW60";
        BookmarkRequestDto requestDto = BookmarkRequestDto.builder().bookmarkSeq(1L).userId(1L).build();
        BDDMockito.given(bookmarkService.deleteBookmark(any(BookmarkServiceRequestDto.class)))
                .willReturn(BookmarkSeqResponse.builder().bookmarkSeq(1L).folderSeq(1L).userId(1L).build());

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/manage/bookmark")
                .header("Authorization", "Bearer " + accessTokenFromRequestHeader)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(MockMvcRestDocumentationWrapper.document("bookmark-delete",
                        MockMvcRestDocumentationWrapper.resourceDetails()
                                .tag("Bookmark API")
                                .description("단일 북마크 삭제 요청 API"),
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access 토큰")
                        ),
                        requestFields(
                                fieldWithPath("bookmarkSeq").description("북마크 시퀀스").type(JsonFieldType.NUMBER),
                                fieldWithPath("userId").description("사용자 시퀀스").type(JsonFieldType.NUMBER)
                        ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드").type(JsonFieldType.NUMBER),
                                fieldWithPath("status").description("응답 상태").type(JsonFieldType.STRING),
                                fieldWithPath("message").description("응답 메시지").type(JsonFieldType.STRING),
                                fieldWithPath("timestamp").description("응답 시간").type(JsonFieldType.STRING),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.bookmarkSeq").description("삭제된 북마크 시퀀스"),
                                fieldWithPath("data.userId").description("사용자 시퀀스"),
                                fieldWithPath("data.folderSeq").description("폴더 시퀀스")
                        )
                ));
    }




}