package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UrlParser {

    /**
     * BookmarkAddRequestDto 의 url 을 scheme, host, domain, port, path 으로 분해하여 필드에 재할당.
     * @param requestDto with url
     * @return bookmarkAddRequestDto with url + fields
     * */
    public BookmarkCreateServiceRequestDto assignUrlFields(BookmarkCreateServiceRequestDto requestDto){
        String[] urlFragmentsArray = disUnitUrl(requestDto.getBookmarkUrl());
        requestDto.setBookmarkScheme(urlFragmentsArray[1]);
        requestDto.setBookmarkHost(urlFragmentsArray[2]);
        requestDto.setBookmarkDomain(urlFragmentsArray[3]);
        requestDto.setBookmarkPort(urlFragmentsArray[4]);
        requestDto.setBookmarkPath(urlFragmentsArray[6]);
        return requestDto;
    }

    // TODO : 분석된 url fragment 를 requestDto 에 할당하는 로직이다. 두 가지 방법 사이에서 고민하고 있다.
    //  1. 제네릭과, Reflection 을 사용하여 하나의 메서드로 bookmark create 요청과 update 요청을 모두 처리하는 방법 : 자원 소모가 증가.
    //  2. bookmark create 요청과 update 요청을 각각 처리하는 메서드를 모두 만드는 방법 : 중복 코드가 발생.
    public <T> T assignUrlFields(T requestDto, String url){
        String[] urlFragmentsArray = disUnitUrl(url);

        List<String> targetFieldNames = List.of("bookmarkScheme", "bookmarkHost", "bookmarkDomain", "bookmarkPort",
                "bookmarkPath");
        try {
            Field[] fields = requestDto.getClass().getDeclaredFields();
            for (Field field : fields){
                if(targetFieldNames.contains(field.getName())){
                    field.setAccessible(true);
                    switch (field.getName()){
                        case "bookmarkScheme":
                            field.set(requestDto, urlFragmentsArray[1]);
                            break;
                        case "bookmarkHost":
                            field.set(requestDto, urlFragmentsArray[2]);
                            break;
                        case "bookmarkDomain":
                            field.set(requestDto, urlFragmentsArray[3]);
                            break;
                        case "bookmarkPort":
                            field.set(requestDto, urlFragmentsArray[4]);
                            break;
                        case "bookmarkPath":
                            field.set(requestDto, urlFragmentsArray[6]);
                            break;
                    }
                }
                if(field.canAccess(requestDto)){
                    field.setAccessible(false);
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        /*
        // switch 문을 사용하지 않고, Map 을 사용하여 처리하는 방법.
        * Map<String, Integer> targetFieldMap = Map.of(
            "bookmarkScheme", 1,
            "bookmarkHost", 2,
            "bookmarkDomain", 3,
            "bookmarkPort", 4,
            "bookmarkPath", 6
    );

    try {
        Field[] fields = requestDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            Integer urlFragmentIndex = targetFieldMap.get(field.getName());
            if (urlFragmentIndex != null) {
                boolean isFieldAccessible = field.canAccess(requestDto);
                if (!isFieldAccessible) {
                    field.setAccessible(true);
                }

                field.set(requestDto, urlFragmentsArray[urlFragmentIndex]);

                if (!isFieldAccessible) {
                    field.setAccessible(false);
                }
            }
        }
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    }
        * */

        return requestDto;
    }


    private String[] disUnitUrl(String url){
        Pattern urlPattern = Pattern.compile("^(https?://)(www\\.)?([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?");
        String[] extractGroup = new String[8];

        Matcher matcher = urlPattern.matcher(url);
        if(matcher.find()){
            for (int i = 0; i <= matcher.groupCount(); i++) {
                extractGroup[i] = matcher.group(i);
            }
        }
        
        return extractGroup;
    }

}
