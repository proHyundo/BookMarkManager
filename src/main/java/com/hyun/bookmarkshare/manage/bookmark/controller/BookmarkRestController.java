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

    /*
     * BookmarkListRequestDto 에서 userId, folderParentSeq 를 받아서 해당 유저의 해당 폴더의 북마크 리스트를 반환
     * */
    @GetMapping("/manage/bookmark/list")
    public ResponseEntity<BookmarkListResponseEntity> getBookListRequest(@RequestBody @Valid BookmarkListRequestDto bookmarkListRequestDto){
        return BookmarkListResponseEntity.toBookmarkListResponseEntity(bookmarkService.getBookList(bookmarkListRequestDto));
    }

    /*
     * BookmarkRequestDto 에서 userId, bookmarkSeq 를 받아서 해당 유저의 해당 북마크를 반환
     * */
    @GetMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> getBookmarkRequest(@RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.getBookmark(bookmarkRequestDto));
    }

    /*
     * 북마크 저장. 해당 유저의 해당 폴더에 북마크를 추가.
     * BookmarkAddRequestDto(userId, folderParentSeq, bookmarkUrl, bookmarkTitle, bookmarkMemo)
     * */
    @PostMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> addBookmarkRequest(@RequestBody @Valid BookmarkAddRequestDto bookmarkAddRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.createBookmark(bookmarkAddRequestDto));
    }

    /*
     * BookmarkUpdateRequestDto 에서 userId, bookmarkSeq, bookmarkUrl, bookmarkTitle, bookmarkMemo 를 받아서 해당 유저의 해당 북마크를 수정
     * */
    @PatchMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> updateBookmarkRequest(@RequestBody @Valid BookmarkUpdateRequestDto bookmarkUpdateRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.updateBookmark(bookmarkUpdateRequestDto));
    }

    /*
     * BookmarkRequestDto 에서 userId, bookmarkSeq 를 받아서 해당 유저의 해당 북마크를 삭제
     * */
    @DeleteMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> deleteBookmarkRequest(@RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.deleteBookmark(bookmarkRequestDto));
    }


}
