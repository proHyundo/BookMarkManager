package com.hyun.bookmarkshare.manage.folder.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest
class FolderRestControllerTest {

    // [error] Could not autowire. No beans of 'MockMvc' type found.
    //https://stackoverflow.com/questions/73511395/intellij-could-not-autowire-no-beans-of-mockmvc-type-found-but-test-is-ok
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;
/*
    @ParameterizedTest
    @MethodSource("paramsForFolderCreateRequestDto_success")
    @DisplayName("FolderCreateRequestDto @Valid - arguments 성공 케이스")
    void folderCreateRequestDto_success(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope) throws Exception{
        this.mockMvc.perform(post("/api/v1/manage/folder/new")
                .content("{\"folderSeq\": "+folderSeq+",\n" +
                        "\"userId\": "+userId+",\n" +
                        "\"folderParentSeq\": "+folderParentSeq+",\n" +
                        "\"folderName\": " + (folderName==null?"null":"\""+folderName+"\"") + ",\n" +
                        "\"folderCaption\": " + (folderCaption==null?"null":"\""+folderCaption+"\"") + ",\n" +
                        "\"folderScope\": \""+folderScope+"\"}")
                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    static Stream<Arguments> paramsForFolderCreateRequestDto_success(){
        return Stream.of(
                // Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope
                Arguments.of(null, 1L, 0L, "folderName0216", null, "p"),
                Arguments.of(null, 1L, 1L, "folderName0217", null, "p"),
                Arguments.of(null, 1L, 1L, "folderName_0217", null, "p"), // folderName에 대소문자+언더바가 들어간 경우
                Arguments.of(null, 1L, 1L, "folderName 0217", null, "p"), // folderName에 대소문자+공백 경우
                Arguments.of(null, 1L, 1L, "folderName0217", null, "p"), // folderName에 대소문자+숫자 경우
                Arguments.of(null, 1L, 1L, " ", null, "p"), // folderName에 공백인 경우
                Arguments.of(null, 1L, 1L, null, null, "p") // #6 folderName이 Null인 경우

        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderCreateRequestDto_fail")
    @DisplayName("FolderCreateRequestDto @Valid - arguments 실패 케이스")
    void folderCreateRequestDto_fail(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope) throws Exception{
        this.mockMvc.perform(post("/api/v1/manage/folder/new")
                        .content("{\"folderSeq\": "+folderSeq+",\n" +
                                "\"userId\": "+userId+",\n" +
                                "\"folderParentSeq\": "+folderParentSeq+",\n" +
                                "\"folderName\": " + (folderName==null?"":"\""+folderName+"\"") + ",\n" +
                                "\"folderCaption\": " + (folderCaption==null?"":"\""+folderCaption+"\"") + ",\n" +
                                "\"folderScope\": \""+folderScope+"\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
    static Stream<Arguments> paramsForFolderCreateRequestDto_fail(){
        return Stream.of(
                // Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope
                Arguments.of(999L, 1L, 0L, "folderName0216", null, "p"), // #1 folderSeq가 존재하는 경우
                Arguments.of(null, null, 1L, "folderName0217", null, "p"), // #2 userId가 Null인 경우
                Arguments.of(null, -1L, 1L, "folderName0217", null, "p"), // #3 userId가 음수인 경우
                Arguments.of(null, 1L, null, "folderName0217", null, "p"), // #4 folderParentSeq가 Null인 경우
                Arguments.of(null, 1L, -1L, "folderName0217", null, "p"), // #5 folderParentSeq가 음수인 경우
                Arguments.of(null, 1L, 1L, "!@", null, "p"), // #7 folderName에 언더바를 제외한 특수문자가 들어간 경우
                Arguments.of(null, 1L, 1L, "", null, "p"), // # folderName의 길이가 1 미만인 빈 문자열의 경우
                Arguments.of(null, 1L, 1L, "012345678901234567890123456789012345678901234567890", null, "p"), // folderName의 길이가 50 초과인 경우
                Arguments.of(null, 1L, 1L, "folderName0217", null, "k"), // folderScope가 p,o,u 외 글자인 경우
                Arguments.of(null, 1L, 1L, "folderName0217", null, null) // folderScope가 Null인 경우

        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderRequestDto_success")
    @DisplayName("FolderRequestDto @Valid - arguments 성공 케이스")
    void folderRequestDto_success(Long folderSeq, Long userId, String folderName, String folderCaption, String folderScope, Long folderParentSeq) throws Exception {
        this.mockMvc.perform(patch("/manage/folder/update")
                        .content("{\"folderSeq\": "+folderSeq+",\n" +
                                "\"userId\": "+userId+",\n" +
                                "\"folderName\": " + (folderName==null?"":"\""+folderName+"\"") + ",\n" +
                                "\"folderCaption\": " + (folderCaption==null?"":"\""+folderCaption+"\"") + ",\n" +
                                "\"folderScope\": \""+folderScope+"\",\n" +
                                "\"folderParentSeq\": "+folderParentSeq+"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    static Stream<Arguments> paramsForFolderRequestDto_success(){
        return Stream.of(
                Arguments.of(1L, 1L, "dtoTestFolderName", "caption-0217", "p", 1L)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderRequestDto_fail")
    @DisplayName("FolderRequestDto @Valid - arguments 실패 케이스")
    void folderRequestDto_fail(Long folderSeq, Long userId, String folderName, String folderCaption, String folderScope, Long folderParentSeq) throws Exception {
        this.mockMvc.perform(patch("/manage/folder/update")
                        .content("{\"folderSeq\": "+folderSeq+",\n" +
                                "\"userId\": "+userId+",\n" +
                                "\"folderName\": " + (folderName==null?"":"\""+folderName+"\"") + ",\n" +
                                "\"folderCaption\": " + (folderCaption==null?"":"\""+folderCaption+"\"") + ",\n" +
                                "\"folderScope\": \""+folderScope+"\",\n" +
                                "\"folderParentSeq\": "+folderParentSeq+"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
    static Stream<Arguments> paramsForFolderRequestDto_fail(){
        return Stream.of(
                Arguments.of(null, 1L, "dtoTestFolderName", "caption-0217", "p", 1L), // folderSeq 가 null
                Arguments.of(0L, 1L, "dtoTestFolderName", "caption-0217", "p", 1L), // folderSeq 가 0
                Arguments.of(-2L, 1L, "dtoTestFolderName", "caption-0217", "p", 1L), // folderSeq 가 음수
                Arguments.of(1L, null, "dtoTestFolderName", "caption-0217", "p", 1L), // userId 가 null
                Arguments.of(1L, 0L, "dtoTestFolderName", "caption-0217", "p", 1L), // userId 가 0
                Arguments.of(1L, 1L, null, "caption-0217", "p", 1L), // folderName 이 null
                Arguments.of(1L, 1L, "dtoTestFolderName@!", "caption-0217", "p", 1L), // folderName 에 특수문자
                Arguments.of(16L, 1L, "dtoTestFolderName", "012345678901234567890123456789012345678901234567891", "p", 1L), // folderCaption 길이 50 초과
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", null, 1L), // folderScope 가 null
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", "", 1L), // folderScope 가 빈문자열
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", "a", 1L), // folderScope 가 pou 외 문자
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", "p", null) // folderParentSeq 가 null
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderListRequestDto_success")
    @DisplayName("FolderListRequestDto @Valid - arguments 성공 케이스")
    void folderListRequestDto_success(Long userId, Long folderParentSeq) throws Exception {
        this.mockMvc.perform(get("/manage/folder/list")
                        .content("{\"userId\": " + userId + ", \n" +
                                "\"folderParentSeq\": " + folderParentSeq +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    static Stream<Arguments> paramsForFolderListRequestDto_success(){
        return Stream.of(
                Arguments.of(1L, 0L),
                Arguments.of(1L, 1L),
                Arguments.of(1L, 2L)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderListRequestDto_fail")
    @DisplayName("FolderListRequestDto @Valid - arguments 실패 케이스")
    void folderListRequestDto_fail(Long userId, Long folderParentSeq) throws Exception {
        this.mockMvc.perform(get("/manage/folder/list")
                        .content("{\"userId\": " + userId + ", \n" +
                                "\"folderParentSeq\": " + folderParentSeq +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
    static Stream<Arguments> paramsForFolderListRequestDto_fail(){
        return Stream.of(
                Arguments.of(null, 1L), // userId 가 null 인 경우
                Arguments.of(0L, 1L), // userId 가 0 인 경우
                Arguments.of(-1L, 1L), // userId 가 음수 인 경우
                Arguments.of(1L, null), // folderParentSeq 가 null 인 경우
                Arguments.of(1L, -1L) // folderParentSeq 가 음수 인 경우

        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderReorderRequestDto_success")
    @DisplayName("FolderReorderRequestDto @Valid - arguments 성공 케이스")
    void folderReorderRequestDto_success(Long userId, Long folderParentSeq, List<Integer> folderSeqOrder) throws Exception {
        System.out.println("folderSeqOrder = " + folderSeqOrder);
        this.mockMvc.perform(post("/manage/folder/reorder")
                        .content("[\n" +
                                "{\"userId\": "+userId +
                                ", \"folderParentSeq\": "+ folderParentSeq +
                                ", \"folderSeqOrder\":"+folderSeqOrder+"}\n" +
                                "]")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    static Stream<Arguments> paramsForFolderReorderRequestDto_success(){
        List<Integer> seqOrders1 = Arrays.asList(3,14,15,16);
        List<Integer> seqOrders2 = Arrays.asList(1,2,4,6,7,8,9,10,11,12,13);

        return Stream.of(
                Arguments.of(1L, 0L, seqOrders2),
                Arguments.of(1L, 1L, seqOrders1)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderReorderRequestDto_fail")
    @DisplayName("FolderReorderRequestDto @Valid - arguments 실패 케이스")
    void folderReorderRequestDto_fail(Long userId, Long folderParentSeq, List<Integer> folderSeqOrder) throws Exception {
        System.out.println("folderSeqOrder = " + folderSeqOrder);
        this.mockMvc.perform(post("/manage/folder/reorder")
                        .content("[\n" +
                                "{\"userId\": "+userId +
                                ", \"folderParentSeq\": "+ folderParentSeq +
                                ", \"folderSeqOrder\":"+folderSeqOrder+"}\n" +
                                "]")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(409));

    }
    static Stream<Arguments> paramsForFolderReorderRequestDto_fail(){
        List<Integer> seqOrders1 = Arrays.asList(3,14,15,16);
        List<Integer> seqOrders2 = Arrays.asList(1);

        return Stream.of(
                Arguments.of(null, 0L, seqOrders1), // userId null
                Arguments.of(-1L, 1L, seqOrders1), // userId 음수
                Arguments.of(1L, null, seqOrders1), // folderParentSeq null
                Arguments.of(1L, -1L, seqOrders1), // folderParentSeq 음수
                Arguments.of(1L, 1L, seqOrders2) // folderSeqOrder 길이 2 미만
        );
    }
    */
}