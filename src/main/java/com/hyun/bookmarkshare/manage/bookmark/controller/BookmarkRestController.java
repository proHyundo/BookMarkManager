package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BookmarkRestController {

    private final BookmarkService bookmarkService;

    @GetMapping("/manage/bookmark/list")
    public ResponseEntity<BookmarkListResponseEntity> getBookListRequest(@RequestBody @Valid BookmarkListRequestDto bookmarkListRequestDto){
        return BookmarkListResponseEntity.toBookmarkListResponseEntity(bookmarkService.getBookList(bookmarkListRequestDto));
    }

    @GetMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> getBookmarkRequest(@RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.getBookmark(bookmarkRequestDto));
    }

    @PostMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> addBookmarkRequest(@RequestBody @Valid BookmarkAddRequestDto bookmarkAddRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.createBookmark(bookmarkAddRequestDto));
    }

    @PatchMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> updateBookmarkRequest(@RequestBody @Valid BookmarkUpdateRequestDto bookmarkUpdateRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.updateBookmark(bookmarkUpdateRequestDto));
    }

    @DeleteMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> deleteBookmarkRequest(@RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.deleteBookmark(bookmarkRequestDto));
    }

}
