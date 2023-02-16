package com.hyun.bookmarkshare.manage.folder.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class FolderRestControllerTest {

    // [error] Could not autowire. No beans of 'MockMvc' type found.
    //https://stackoverflow.com/questions/73511395/intellij-could-not-autowire-no-beans-of-mockmvc-type-found-but-test-is-ok
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("paramsForFolderCreateRequestDto_success")
    @DisplayName("FolderCreateRequestDto @Valid - arguments 성공 케이스")
    void addFolderRequest_success(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope) throws Exception{
        this.mockMvc.perform(post("/manage/folder/addFolder")
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
                //Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope
                Arguments.of(null, 1L, 0L, "folderName0216", null, "p"),
                Arguments.of(null, 1L, 1L, "folderName0217", null, "p"),
                Arguments.of(null, 1L, 1L, "folderName_0217", null, "p"), // folderName에 대소문자+언더바가 들어간 경우
                Arguments.of(null, 1L, 1L, "folderName 0217", null, "p"), // folderName에 대소문자+공백 경우
                Arguments.of(null, 1L, 1L, "folderName0217", null, "p") // folderName에 대소문자+숫자 경우

        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderCreateRequestDto_fail")
    @DisplayName("FolderCreateRequestDto @Valid - arguments 실패 케이스")
    void addFolderRequest_fail(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope) throws Exception{
        this.mockMvc.perform(post("/manage/folder/addFolder")
                        .content("{\"folderSeq\": "+folderSeq+",\n" +
                                "\"userId\": "+userId+",\n" +
                                "\"folderParentSeq\": "+folderParentSeq+",\n" +
                                "\"folderName\": " + (folderName==null?"null":"\""+folderName+"\"") + ",\n" +
                                "\"folderCaption\": " + (folderCaption==null?"null":"\""+folderCaption+"\"") + ",\n" +
                                "\"folderScope\": \""+folderScope+"\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
    static Stream<Arguments> paramsForFolderCreateRequestDto_fail(){
        return Stream.of(
                //Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope
                Arguments.of(999L, 1L, 0L, "folderName0216", null, "p"), // #1 folderSeq가 존재하는 경우
                Arguments.of(null, null, 1L, "folderName0217", null, "p"), // #2 userId가 Null인 경우
                Arguments.of(null, -1L, 1L, "folderName0217", null, "p"), // #3 userId가 음수인 경우
                Arguments.of(null, 1L, null, "folderName0217", null, "p"), // #4 folderParentSeq가 Null인 경우
                Arguments.of(null, 1L, -1L, "folderName0217", null, "p"), // #5 folderParentSeq가 음수인 경우
                Arguments.of(null, 1L, 1L, null, null, "p"), // #6 folderName가 Null인 경우
                Arguments.of(null, 1L, 1L, "", null, "p"), // #7 folderName에 언더바를 제외한 특수문자가 들어간 경우
                Arguments.of(null, 1L, 1L, "", null, "p"), // # folderName의 길이가 0 이하인 경우
                Arguments.of(null, 1L, 1L, "012345678901234567890123456789012345678901234567890", null, "p"), // folderName의 길이가 50 초과인 경우
                Arguments.of(null, 1L, 1L, "folderName0217", null, "k"), // folderScope가 p,o,u 외 글자인 경우
                Arguments.of(null, 1L, 1L, "folderName0217", null, null) // folderScope가 Null인 경우

        );
    }
}