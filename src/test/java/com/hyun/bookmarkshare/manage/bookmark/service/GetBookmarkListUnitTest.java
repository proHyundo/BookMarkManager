package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkListRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GetBookmarkListUnitTest {

    @Mock
    private BookmarkRepository bookmarkRepository;
    @InjectMocks
    private BookmarkServiceImpl bookmarkService;

    @DisplayName("북마크 리스트_조회_성공")
    @Test
    void getBookmarkListSuccess() {
        // given - BookMarkListRequestDto
        BookmarkListRequestDto bookmarkListRequestDto = BookmarkListRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .build();
        Mockito.when(bookmarkRepository.findAllByUserIdAndFolderSeq(bookmarkListRequestDto.getUserId(), bookmarkListRequestDto.getFolderSeq()))
                .thenReturn(List.of(
                        Bookmark.builder()
                                .bookmarkSeq(1L)
                                .userId(1L)
                                .folderSeq(1L)
                                .bookmarkCaption("test1")
                                .bookmarkScheme("http")
                                .bookmarkHost("www")
                                .bookmarkDomain("test1.com")
                                .bookmarkPath("/test1")
                                .bookmarkUrl("https://www.test1.com/test1")
                                .bookmarkDelFlag("N")
                                .build(),
                        Bookmark.builder()
                                .bookmarkSeq(2L)
                                .userId(1L)
                                .folderSeq(1L)
                                .bookmarkCaption("test2")
                                .bookmarkScheme("http")
                                .bookmarkHost("")
                                .bookmarkDomain("test2.com")
                                .bookmarkPath("/test2")
                                .bookmarkUrl("https://test2.com/test2")
                                .bookmarkDelFlag("N")
                                .build()
                ));
        // when
        List<BookmarkResponseDto> bookList = bookmarkService.getBookList(bookmarkListRequestDto.toServiceDto());

        // then
        assertThat(bookList.size()).isEqualTo(2);
        assertThat(bookList.get(0).getBookmarkSeq()).isEqualTo(1L);
        assertThat(bookList.get(0).getUserId()).isEqualTo(1L);
    }

    @DisplayName("북마크 리스트_조회_실패 : 반환된 북마크 없음")
    @Test
    void getBookmarkListFail() {
        // given - BookMarkListRequestDto
        BookmarkListRequestDto bookmarkListRequestDto = BookmarkListRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .build();

        Mockito.when(bookmarkRepository.findAllByUserIdAndFolderSeq(bookmarkListRequestDto.getUserId(), bookmarkListRequestDto.getFolderSeq()))
                .thenReturn(Collections.emptyList());

        // when
        // then
        Assertions.assertThatThrownBy(() -> {
            List<BookmarkResponseDto> bookList = bookmarkService.getBookList(bookmarkListRequestDto.toServiceDto());
        }).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("북마크 리스트_조회_실패 : 요청 Dto 값 이상")
    @Test
    void getBookmarkListFail2() {
        // given - BookMarkListRequestDto
        BookmarkListRequestDto bookmarkListRequestDto = BookmarkListRequestDto.builder()
                .userId(null)
                .folderSeq(1L)
                .build();

        Mockito.when(bookmarkRepository.findAllByUserIdAndFolderSeq(bookmarkListRequestDto.getUserId(), bookmarkListRequestDto.getFolderSeq()))
                .thenReturn(List.of(
                        Bookmark.builder()
                                .bookmarkSeq(1L)
                                .userId(1L)
                                .folderSeq(1L)
                                .bookmarkCaption("test1")
                                .bookmarkScheme("http")
                                .bookmarkHost("www")
                                .bookmarkDomain("test1.com")
                                .bookmarkPath("/test1")
                                .bookmarkUrl("https://www.test1.com/test1")
                                .bookmarkDelFlag("N")
                                .build(),
                        Bookmark.builder()
                                .bookmarkSeq(2L)
                                .userId(1L)
                                .folderSeq(1L)
                                .bookmarkCaption("test2")
                                .bookmarkScheme("http")
                                .bookmarkHost("")
                                .bookmarkDomain("test2.com")
                                .bookmarkPath("/test2")
                                .bookmarkUrl("https://test2.com/test2")
                                .bookmarkDelFlag("N")
                                .build()
                ));

        // when
        // then
        Assertions.assertThatThrownBy(() -> {
            List<BookmarkResponseDto> bookList = bookmarkService.getBookList(bookmarkListRequestDto.toServiceDto());
        }).isInstanceOf(NoSuchElementException.class);
    }


}