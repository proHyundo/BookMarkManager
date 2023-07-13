package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
