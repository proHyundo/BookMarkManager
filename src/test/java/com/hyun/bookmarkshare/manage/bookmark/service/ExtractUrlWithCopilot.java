package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractUrlWithCopilot {

    @DisplayName("Copilot 활용 url 추출 테스트")
    @MethodSource("urlProvider")
    @ParameterizedTest
    void extractUrlByPattern(String urlParam) {
        // given
        String url = urlParam;

        // when

        /* group1 : ^(https?://)    >>  's' 문자는 생략가능, '^' 줄의 시작위치를 의미.
         * group2 : (www\.)?        >>  서브도메인. www 있을수도 없을수도.
         * group3 : ([^:\/\s]+)     >>  루트도메인. 콜론(:), 슬래시(/), 빈칸(' ')을 뺀 나머지 문자열이 하나 이상 나타남.
         * group4 : (:([^\/]*))?    >>  포트번호(:8080) 있을수도 없을수도. ':' 을 포함해서 추출
         * group5 :                 >>  ':' 제외하고 포트번호 추출.
         * group6 : ((\/[^\s/\/]+)*)? >> 슬래시(/)로 시작하고 공백이나 다른 슬래시(/)가 아닌 문자열을 찾습니다. 이 그룹은 URL 주소의 path를 나타냅니다.
         */
        Pattern urlPattern = Pattern.compile("^(https?://)(www\\.)?([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?");
        Matcher matcher = urlPattern.matcher(url);

        // then
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("group "+i+" >> "+matcher.group(i));
            }
        }
        System.out.println("=====================================");

    }

    // UrlProvider
    static Stream<Arguments> urlProvider() {
        return Stream.of(
                Arguments.arguments("https://www.naver.com"),
                Arguments.arguments("https://www.naver.com:8080"),
                Arguments.arguments("https://www.naver.com:8080/"),
                Arguments.arguments("https://www.naver.com:8080/abc"),
                Arguments.arguments("https://www.naver.com:8080/abc/def"),
                Arguments.arguments("https://www.naver.com:8080/abc/def/ghi"),
                Arguments.arguments("https://www.naver.com:8080/abc/def/ghi/jkl"),
                Arguments.arguments("https://www.naver.com:8080/abc/def/ghi/jkl.html"),
                Arguments.arguments("https://www.naver.com:8080/abc/def/ghi/jkl.html?abc=def&ghi=jkl"),
                Arguments.arguments("https://www.naver.com:8080/abc/def/ghi/jkl.html?abc=def&ghi=jkl#abc"),
                Arguments.arguments("https://www.naver.com:8080/abc/def/ghi/jkl.html?abc=def&ghi=jkl#abc/def")
        );
    }
}
