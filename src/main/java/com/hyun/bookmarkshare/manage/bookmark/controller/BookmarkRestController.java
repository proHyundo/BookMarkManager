package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookmarkRestController {

    private final BookmarkService bookmarkService;

    /**
     * 특정 폴더의 북마크 리스트 GET Request 처리.
     * <br/>
     * @param bookmarkListRequestDto 의 userId, folderParentSeq 를 받는다.
     * @return 해당 User의 해당 Folder 의 Bookmark List 를 BookmarkListResponseEntity 으로 감싸서 반환.
     * */
    @GetMapping("/manage/bookmark/list")
    public ResponseEntity<BookmarkListResponseEntity> getBookListRequest(@RequestBody @Valid BookmarkListRequestDto bookmarkListRequestDto){
        return BookmarkListResponseEntity.toBookmarkListResponseEntity(bookmarkService.getBookList(bookmarkListRequestDto));
    }


    /**
     * 특정 Bookmark 정보 GET Request 처리.
     *
     * @param bookmarkRequestDto 의 userId, bookmarkSeq 를 받는다.
     * @return 해당 Bookmark 의 .. 를 BookmarkResponseEntity 으로 감싸서 반환.
     * */
    @GetMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> getBookmarkRequest(@RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.getBookmark(bookmarkRequestDto));
    }


    /**
     * 새로운 Bookmark POST Request 처리.
     *
     * @param bookmarkAddRequestDto 의 userId, folderParentSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 를 받는다.
     * @return 저장된 Bookmark 정보를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @PostMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> addBookmarkRequest(@RequestBody @Valid BookmarkAddRequestDto bookmarkAddRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.createBookmark(bookmarkAddRequestDto));
    }


    /**
     * Bookmark 정보 Patch Request 처리.
     *
     * @param bookmarkUpdateRequestDto 의 userId, bookmarkSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 를 받는다.
     * @return 수정된 Bookmark 정보를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @PatchMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> updateBookmarkRequest(@RequestBody @Valid BookmarkUpdateRequestDto bookmarkUpdateRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(bookmarkService.updateBookmark(bookmarkUpdateRequestDto));
    }


    /**
     * Bookmark 순서 변경 Patch Request 처리.
     *
     * @param bookmarkReorderRequestDtos 의 userId, folderSeq, bookmarkSeqOrder 를 받는다.
     * @return 수정된 bookmarkSeq List 를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @PatchMapping("/manage/bookmark/reorder")
    public ResponseEntity<BookmarkReorderResponseEntity> updateBookmarkOrderRequest(@RequestBody
                                                                                    @Valid
                                                                                    List<BookmarkReorderRequestDto> bookmarkReorderRequestDtos){
        List<BookmarkReorderServiceRequestDto> bookmarkReorderServiceRequestDtos = bookmarkReorderRequestDtos.stream()
                .map(requestDto -> requestDto.toServiceRequestDto())
                .collect(Collectors.toList());
        return BookmarkReorderResponseEntity.toResponseEntity(
                bookmarkService.updateBookmarkOrder(bookmarkReorderServiceRequestDtos)
        );
    }


    /**
     * 특정 Bookmark DELETE Request 처리.
     *
     * @param bookmarkRequestDto 에서 userId, bookmarkSeq 를 받는다.
     * @return 삭제된 Bookmark 정보를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @DeleteMapping("/manage/bookmark")
    public ResponseEntity<BookmarkResponseEntity> deleteBookmarkRequest(@RequestBody
                                                                        @Valid BookmarkRequestDto bookmarkRequestDto){
        return BookmarkResponseEntity.toBookmarkResponseEntity(
                bookmarkService.deleteBookmark(bookmarkRequestDto.toServiceRequestDto())
        );
    }


}
