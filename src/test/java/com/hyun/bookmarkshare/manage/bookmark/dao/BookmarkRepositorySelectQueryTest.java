package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkListRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class BookmarkRepositorySelectQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @DisplayName("사용자Id와 북마크Id로 북마크를 조회한다.")
    @Test
    void findByUserIdAndBookmarkSeq() {
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1");
        Bookmark bookmark2 = createBookmark(2L, 1L, "bookmarkTitle2");
        Bookmark bookmark3 = createBookmark(3L, 2L, "bookmarkTitle3");

        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);

        // when
        BookmarkResponseDto bookmarkResponseDto = bookmarkRepository
                .findByUserIdAndBookmarkSeq(1L, 1L)
                .get();

        // then
        assertThat(bookmarkResponseDto.getBookmarkSeq()).isEqualTo(bookmark1.getBookmarkSeq());
        assertThat(bookmarkResponseDto.getBookmarkTitle()).isEqualTo("bookmarkTitle1");
    }

    @DisplayName("사용자Id와 북마크Id로 북마크를 조회시, 일치하는 북마크Id가 없을 경우 비어있는 객체가 반환된다.")
    @Test
    void findByUserIdAndBookmarkSeqFail1() {
        // given
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1");
        Bookmark bookmark2 = createBookmark(2L, 1L, "bookmarkTitle2");
        Bookmark bookmark3 = createBookmark(3L, 2L, "bookmarkTitle3");

        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);

        // when
        Optional<BookmarkResponseDto> result = bookmarkRepository.findByUserIdAndBookmarkSeq(1L, 100L);
        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("BookmarkRepository.findByUserIdAndBookmarkSeq - Fail : userId is not exist")
    @Test
    void findByUserIdAndBookmarkSeqFail2() {
        // given
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(100L, 2L);

        // when
        // then
        assertThatThrownBy(() -> bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkRequestDto.getUserId(), bookmarkRequestDto.getBookmarkSeq()).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("BookmarkRepository.findAllByUserIdAndFolderSeq - Success")
    @Test
    void findAllByUserIdAndFolderSeq() {
        // given
        BookmarkListRequestDto bookmarkRequestDto = new BookmarkListRequestDto(1L, 49L);

        // when
        List<Bookmark> bookmarkListResult = bookmarkRepository.findAllByUserIdAndFolderSeq(bookmarkRequestDto.getUserId(), bookmarkRequestDto.getFolderSeq());

        // then
        assertThat(bookmarkListResult).isNotNull();
        assertThat(bookmarkListResult.size()).isEqualTo(2);

        System.out.println(bookmarkListResult.size());
        bookmarkListResult.forEach(bookmarkResponseDto -> {
            log.info("bookmarkResponseDto : {}", bookmarkResponseDto.getBookmarkUrl());
        });
    }

    @DisplayName("BookmarkRepository.findAllByUserIdAndFolderSeq - Fail : folderParentSeq is not exist")
    @Test
    void findAllByUserIdAndFolderSeqFail1(){
        // given
        BookmarkListRequestDto bookmarkListRequestDto = new BookmarkListRequestDto(1L, 99L);

        // when
        List<Bookmark> allByUserIdAndFolderParentSeq = bookmarkRepository.findAllByUserIdAndFolderSeq(bookmarkListRequestDto.getUserId(), bookmarkListRequestDto.getFolderSeq());

        // then
        assertThat(allByUserIdAndFolderParentSeq).isEmpty();
    }

    @DisplayName("BookmarkRepository.findAllByUserIdAndFolderSeq - Fail : userId is not exist")
    @Test
    void findAllByUserIdAndFolderSeqFail2(){
        // given
        BookmarkListRequestDto bookmarkListRequestDto = new BookmarkListRequestDto(999L, 49L);

        // when
        List<Bookmark> allByUserIdAndFolderParentSeq = bookmarkRepository.findAllByUserIdAndFolderSeq(bookmarkListRequestDto.getUserId(), bookmarkListRequestDto.getFolderSeq());

        // then
        assertThat(allByUserIdAndFolderParentSeq).isEmpty();
    }

    private Bookmark createBookmark(Long bookmarkSeq, Long userId, String bookmarkTitle){
        return Bookmark.builder()
                .bookmarkSeq(bookmarkSeq)
                .userId(userId)
                .folderSeq(1L)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption("caption sample")
                .bookmarkScheme("http://")
                .bookmarkHost("www.")
                .bookmarkPort(null)
                .bookmarkDomain("google.com")
                .bookmarkPath("/")
                .bookmarkUrl("http://www.google.com/")
                .bookmarkOrder(1L)
                .bookmarkRegDate(Date.valueOf(LocalDate.of(2023, 7, 3)))
                .bookmarkModDate(Date.valueOf(LocalDate.of(2023, 7, 3)))
                .bookmarkDelFlag("n")
                .build();
    }

}