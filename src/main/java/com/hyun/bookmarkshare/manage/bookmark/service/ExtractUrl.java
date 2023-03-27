package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkAddRequestDto;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExtractUrl {

    public String[] extractUrlByPattern(BookmarkAddRequestDto dto){
        Pattern urlPattern = Pattern.compile("^(https?://)(www\\.)?([^:\\/\\s]+)(:([^\\/]*))?([-a-zA-Z0-9@:%_\\+.~#()?&//=]*)?");
        String[] extractGroup = new String[7];

        Matcher matcher = urlPattern.matcher(dto.getBookmarkUrl());
        if(matcher.find()){
            for (int i = 0; i <= matcher.groupCount(); i++) {
                extractGroup[i] = matcher.group(i);
            }
        }
        return null;
    }
}
