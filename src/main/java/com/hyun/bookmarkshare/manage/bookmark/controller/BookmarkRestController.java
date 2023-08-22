package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.*;
import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkSeqResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
     * @return 특정 Folder 내부에 포함된 Bookmark List 를 반환.
     * */
    @GetMapping("/api/v1/manage/bookmarks")
    public ApiResponse<List<BookmarkResponseDto>> getBookListRequest(@RequestBody @Valid BookmarkListRequestDto bookmarkListRequestDto){
        return ApiResponse.ok(bookmarkService.getBookList(bookmarkListRequestDto.toServiceDto()));
    }

    @GetMapping("/api/v1/manage/bookmarks/{userId}/{folderSeq}")
    public ApiResponse<List<BookmarkResponseDto>> getBookListRequest2nd(@PathVariable("userId") @NotNull @Positive Long userId,
                                                                        @PathVariable("folderSeq") @NotNull @Positive Long folderSeq,
                                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return ApiResponse.ok(bookmarkService.getBookList(BookmarkListRequestDto.builder()
                .userId(userId)
                .folderSeq(folderSeq)
                .build()
                .toServiceDto()));
    }


    /**
     * 특정 Bookmark 정보 GET Request 처리.
     *
     * @param bookmarkRequestDto 의 userId, bookmarkSeq 를 받는다.
     * @return 특정 Bookmark 를 반환.
     * */
    @PostMapping("/api/v1/manage/bookmark")
    public ApiResponse<BookmarkResponseDto> getBookmarkRequest(@RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
        return ApiResponse.ok(bookmarkService.getBookmark(bookmarkRequestDto.toServiceRequestDto()));
    }


    /**
     * 새로운 Bookmark POST Request 처리.
     *
     * @param bookmarkCreateRequestDto 의 userId, folderParentSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 를 받는다.
     * @return 저장된 Bookmark 정보를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @PostMapping("/api/v1/manage/bookmark/new")
    public ApiResponse<BookmarkResponseDto> createBookmarkRequest(@RequestBody @Valid BookmarkCreateRequestDto bookmarkCreateRequestDto){
        return ApiResponse.ok(bookmarkService.createBookmark(bookmarkCreateRequestDto.toServiceDto()));
    }

    /**
     * Bookmark 정보 Patch Request 처리.
     *
     * @param bookmarkUpdateRequestDto 의 userId, bookmarkSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 를 받는다.
     * @return 수정된 Bookmark 정보를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @PatchMapping("/api/v1/manage/bookmark")
    public ApiResponse<BookmarkResponseDto> updateBookmarkRequest(@RequestBody @Valid BookmarkUpdateRequestDto bookmarkUpdateRequestDto){
        return ApiResponse.ok(bookmarkService.updateBookmark(bookmarkUpdateRequestDto.toServiceRequestDto()));
    }


    /**
     * Bookmark 순서 변경 Patch Request 처리.
     *
     * @param requestDtoList 의 userId, folderSeq, bookmarkSeqOrder 를 받는다.
     * @return 수정된 bookmarkSeq List 를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @PatchMapping("/api/v1/manage/bookmark/reorder")
    public ApiResponse<Boolean> updateBookmarkOrderRequest(@RequestBody @Valid List<BookmarkReorderRequestDto> requestDtoList){
        List<BookmarkReorderServiceRequestDto> serviceRequestDto = requestDtoList.stream()
                .map(requestDto -> requestDto.toServiceRequestDto())
                .collect(Collectors.toList());
        return ApiResponse.ok(bookmarkService.updateBookmarkOrder(serviceRequestDto));
    }


    /**
     * 특정 Bookmark DELETE Request 처리.
     *
     * @param bookmarkRequestDto 에서 userId, bookmarkSeq 를 받는다.
     * @return 삭제된 Bookmark 정보를 BookmarkResponseEntity 로 감싸서 반환.
     * */
    @DeleteMapping("/api/v1/manage/bookmark")
    public ApiResponse<BookmarkSeqResponse> deleteBookmarkRequest(@RequestBody
                                                                        @Valid BookmarkRequestDto bookmarkRequestDto){
        return ApiResponse.ok(
                bookmarkService.deleteBookmark(bookmarkRequestDto.toServiceRequestDto())
        );
    }


}
