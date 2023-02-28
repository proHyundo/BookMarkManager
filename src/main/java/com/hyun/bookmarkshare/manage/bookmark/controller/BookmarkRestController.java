package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkListRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkListResponseEntity;
import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BookmarkRestController {

    private final BookmarkService bookmarkService;

    @GetMapping("/manage/bookmark/list")
    public ResponseEntity<BookmarkListResponseEntity> getBookListRequest(@RequestBody @Valid BookmarkListRequestDto bookmarkListRequestDto){
        return BookmarkListResponseEntity.toBookmarkListResponseEntity(bookmarkService.getBookList(bookmarkListRequestDto));
    }


}
