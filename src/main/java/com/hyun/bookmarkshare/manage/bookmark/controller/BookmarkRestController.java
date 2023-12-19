package com.hyun.bookmarkshare.manage.bookmark.controller;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.*;
import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkSeqResponse;
import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // 특정 폴더 내 북마크 리스트 조회
    @GetMapping("/api/v1/manage/bookmarks/{folderSeq}")
    public ApiResponse<List<BookmarkResponseDto>> getBookListRequest(@PathVariable("folderSeq") @NotNull @Positive Long folderSeq,
                                                                     @AuthenticationPrincipal LoginInfoDto loginInfoDto){
        List<BookmarkResponseDto> bookList = bookmarkService.getBookList(
                BookmarkListRequestDto.builder()
                        .userId(loginInfoDto.getUserId())
                        .folderSeq(folderSeq)
                        .build()
                        .toServiceDto());
        return ApiResponse.ok(bookList);
    }

    // 특정 북마크의 정보 조회
    @GetMapping("/api/v1/manage/bookmark/{bookmarkSeq}")
    public ApiResponse<BookmarkResponseDto> getBookmarkRequest(@PathVariable("bookmarkSeq") @NotNull @Positive Long bookmarkSeq,
                                                               @AuthenticationPrincipal LoginInfoDto loginInfoDto){
        return ApiResponse.ok(bookmarkService.getBookmark(BookmarkRequestDto.builder()
                .userId(loginInfoDto.getUserId())
                .bookmarkSeq(bookmarkSeq)
                .build()
                .toServiceRequestDto()));
    }

    // 북마크 신규 생성
    @PostMapping("/api/v1/manage/bookmark/new")
    public ApiResponse<BookmarkResponseDto> createBookmarkRequest(@RequestBody @Valid BookmarkCreateRequestDto bookmarkCreateRequestDto){
        return ApiResponse.ok(bookmarkService.createBookmark(bookmarkCreateRequestDto.toServiceDto()));
    }

    // 북마크 수정
    @PatchMapping("/api/v1/manage/bookmark")
    public ApiResponse<BookmarkResponseDto> updateBookmarkRequest(@RequestBody @Valid BookmarkUpdateRequestDto bookmarkUpdateRequestDto){
        return ApiResponse.ok(bookmarkService.updateBookmark(bookmarkUpdateRequestDto.toServiceRequestDto()));
    }

    // 북마크 순서 변경
    @PatchMapping("/api/v1/manage/bookmark/reorder")
    public ApiResponse<Boolean> updateBookmarkOrderRequest(@RequestBody @Valid List<BookmarkReorderRequestDto> requestDtoList){
        List<BookmarkReorderServiceRequestDto> serviceRequestDto = requestDtoList.stream()
                .map(requestDto -> requestDto.toServiceRequestDto())
                .collect(Collectors.toList());
        return ApiResponse.ok(bookmarkService.updateBookmarkOrder(serviceRequestDto));
    }

    // 북마크 삭제
    @DeleteMapping("/api/v1/manage/bookmark")
    public ApiResponse<BookmarkSeqResponse> deleteBookmarkRequest(@RequestBody
                                                                        @Valid BookmarkRequestDto bookmarkRequestDto){
        return ApiResponse.ok(
                bookmarkService.deleteBookmark(bookmarkRequestDto.toServiceRequestDto())
        );
    }


}
