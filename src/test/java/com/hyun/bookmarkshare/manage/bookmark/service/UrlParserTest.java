package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
// MockitoAnnotations.openMocks(this) 대신 @ExtendWith(MockitoExtension.class)를 사용할 수 있다. 둘다 @Mock, @InjectMocks 어노테이션을 사용하기 위함.
@ExtendWith(MockitoExtension.class)
public class UrlParserTest {

    @InjectMocks
    UrlParser urlParser;

    @DisplayName("스키마, 호스트, 포트번호, 도메인, 경로 모두 존재하는 URL 인 경우, 모든 필드에 값이 할당되어야 한다.")
    @Test
    void assignUrlFieldsWhenUrlHasAllParts() {
        // given
        String target = "https://www.domain.com:8080/abc/def/ghi/jkl";
        BookmarkCreateServiceRequestDto requestDto = BookmarkCreateServiceRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .bookmarkTitle("bookmarkTitle")
                .bookmarkCaption("bookmarkCaption")
                .bookmarkScheme(null)
                .bookmarkHost(null)
                .bookmarkDomain(null)
                .bookmarkPort(null)
                .bookmarkPath(null)
                .bookmarkUrl(target)
                .build();
        // when
        BookmarkCreateServiceRequestDto resultDto = urlParser.assignUrlFields(requestDto);

        // then
        assertThat(resultDto)
                .extracting("bookmarkScheme", "bookmarkHost", "bookmarkDomain", "bookmarkPort",
                        "bookmarkPath", "bookmarkUrl")
                .containsExactlyInAnyOrder("https://", "www.", "domain.com", ":8080", "/abc/def/ghi/jkl", target);
    }

    @DisplayName("호스트, 포트번호 가 존재하지 않는 URL 인 경우, null 값이 할당된다.")
    @Test
    void assignUrlFieldsWhenUrlWithoutHostAndPort() {
        // given
        String target = "https://velog.io/@name/%A1%9C/URL-%EC%.html";
        BookmarkCreateServiceRequestDto requestDto = BookmarkCreateServiceRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .bookmarkTitle("bookmarkTitle")
                .bookmarkCaption("bookmarkCaption")
                .bookmarkScheme(null)
                .bookmarkHost(null)
                .bookmarkDomain(null)
                .bookmarkPort(null)
                .bookmarkPath(null)
                .bookmarkUrl(target)
                .build();
        // when
        BookmarkCreateServiceRequestDto resultDto = urlParser.assignUrlFields(requestDto);
        // then
        assertThat(resultDto)
                .extracting("bookmarkScheme", "bookmarkHost", "bookmarkDomain", "bookmarkPort",
                        "bookmarkPath", "bookmarkUrl")
                .containsExactlyInAnyOrder("https://", null, "velog.io", null, "/@name/%A1%9C/URL-%EC%.html", target);
    }

    @DisplayName("Url 구조 종류별 추출 테스트")
    @MethodSource("urlProviderMethod")
    @ParameterizedTest
    void extractUrlByPattern(String urlParam) {
        // given
        String url = urlParam;

        // when
        /* 참고링크
         * https://goodidea.tistory.com/86
         * https://blog.naver.com/PostView.nhn?blogId=psj9102&logNo=221203659771&redirect=Dlog&widgetTypeCall=true&directAccess=false
         * https://velog.io/@jhhwghg9911/%EC%A0%95%EA%B7%9C%ED%91%9C%ED%98%84%EC%8B%9D%EC%9C%BC%EB%A1%9C-URL-%EC%B6%94%EC%B6%9C%ED%95%98%EA%B8%B0
         * https://regexr.com/
         */

        // This pattern need more validation
        // Pattern urlPattern = Pattern.compile("^(https?://)(www\\.)?([^:\\/\\s]+)(:([^\\/]*))?([-a-zA-Z0-9@:%_\\+.~#()?&//=]*)?");

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
                log.info("group {} >> {}", i, matcher.group(i));
            }
        }
        log.info("==========================================================================");
    }

    static Stream<Arguments> urlProviderMethod() {
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
