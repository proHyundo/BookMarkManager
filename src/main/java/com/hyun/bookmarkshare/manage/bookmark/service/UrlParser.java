package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkAddRequestDto;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UrlParser {

    /**
     * BookmarkAddRequestDto 의 url 을 scheme, host, domain, port, path 으로 분해하여 필드에 재할당.
     * @param bookmarkAddRequestDto with url
     * @return bookmarkAddRequestDto with url + fields
     * */
    public BookmarkAddRequestDto assignUrlFields(BookmarkAddRequestDto bookmarkAddRequestDto){
        String[] urlFragmentsArray = disUnitUrl(bookmarkAddRequestDto.getBookmarkUrl());

        bookmarkAddRequestDto.setBookmarkScheme(urlFragmentsArray[1]);
        bookmarkAddRequestDto.setBookmarkHost(urlFragmentsArray[2]);
        bookmarkAddRequestDto.setBookmarkDomain(urlFragmentsArray[3]);
        bookmarkAddRequestDto.setBookmarkPort(urlFragmentsArray[4]);
        bookmarkAddRequestDto.setBookmarkPath(urlFragmentsArray[6]);

        return bookmarkAddRequestDto;
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
