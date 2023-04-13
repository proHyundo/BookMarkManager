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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
@Transactional
class BookmarkRepositorySelectQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @DisplayName("BookmarkRepository.findByUserIdAndBookmarkSeq - Success")
    @Test
    void findByUserIdAndBookmarkSeq() {
        // given
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(1L, 2L);

        // when
        BookmarkResponseDto bookmarkResponseDto = bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkRequestDto.getUserId(), bookmarkRequestDto.getBookmarkSeq()).get();

        // then
        assertThat(bookmarkResponseDto.getBookmarkSeq()).isEqualTo(bookmarkRequestDto.getBookmarkSeq());
        assertThat(bookmarkResponseDto.getBookmarkUrl()).isNotNull();
    }

    @DisplayName("BookmarkRepository.findByUserIdAndBookmarkSeq - Fail : bookmarkSeq is not exist")
    @Test
    void findByUserIdAndBookmarkSeqFail1() {
        // given
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(1L, 100L);

        // when
        // then
        assertThatThrownBy(() -> bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkRequestDto.getUserId(), bookmarkRequestDto.getBookmarkSeq()).get())
                .isInstanceOf(NoSuchElementException.class);
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


}