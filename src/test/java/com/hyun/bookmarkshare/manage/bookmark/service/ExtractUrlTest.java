package com.hyun.bookmarkshare.manage.bookmark.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractUrlTest {

    @DisplayName("url 분리")
    @Test
    void extractUrlPattern(){
        // given
        String target = "https://velog.io/@jhhwghg9911/%EC%A0%95%EA%B7%9C%ED%91%9C%ED%98%84%EC%8B%9D%EC%9C%BC%EB%A1%9C-URL-%EC%B6%94%EC%B6%9C%ED%95%98%EA%B8%B0.html";

        // when

        /** 참고링크
        // https://goodidea.tistory.com/86
        // https://blog.naver.com/PostView.nhn?blogId=psj9102&logNo=221203659771&redirect=Dlog&widgetTypeCall=true&directAccess=false
        // https://velog.io/@jhhwghg9911/%EC%A0%95%EA%B7%9C%ED%91%9C%ED%98%84%EC%8B%9D%EC%9C%BC%EB%A1%9C-URL-%EC%B6%94%EC%B6%9C%ED%95%98%EA%B8%B0
        // https://regexr.com/
        **/

//        Pattern urlPattern4 = Pattern.compile("^(https?://)(www\\.)?([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?"); // path 이전까지 성공
        Pattern urlPattern = Pattern.compile("^(https?://)(www\\.)?([^:\\/\\s]+)(:([^\\/]*))?([-a-zA-Z0-9@:%_\\+.~#()?&//=]*)?");


        // group1 : ^(https?://)    >>  's' 문자는 생략가능, '^' 줄의 시작위치를 의미.
        // group2 : (www\.)?        >>  서브도메인. www 있을수도 없을수도.
        // group3 : ([^:\/\s]+)     >>  루트도메인. 콜론(:), 슬래시(/), 빈칸(' ')을 뺀 나머지 문자열이 하나 이상 나타남.
        // group4 : (:([^\/]*))?    >>  포트번호(:8080) 있을수도 없을수도. ':' 을 포함해서 추출
        // group5 :                 >>  ':' 제외하고 포트번호 추출.
        // group6 : ([-a-zA-Z0-9@:%_\+.~#()?&//=]*)?    >> 각종 특수문자를 허용하는 path 로 쿼리파라미터와 파일확장자를 포함할 수 있다.

        String[] extractGroup = new String[7];

        Matcher matcher = urlPattern.matcher(target);
        if(matcher.find()){
            for (int i = 0; i <= matcher.groupCount(); i++) {
                if(matcher.group(i) == null || matcher.group(i).isEmpty()){
                    System.out.println("nullnull하구먼");
                }
                extractGroup[i] = matcher.group(i);
            }
        }

    }


}
