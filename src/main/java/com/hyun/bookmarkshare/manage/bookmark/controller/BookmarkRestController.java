package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkRestController {

    private final BookmarkService bookmarkService;


}
