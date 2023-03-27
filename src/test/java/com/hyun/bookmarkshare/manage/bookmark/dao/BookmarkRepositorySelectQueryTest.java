package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
@Transactional
class BookmarkRepositorySelectQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @DisplayName("북마크 식별번호로 북마크 조회 - 성공")
    @Test
    void findByUserIdAndBookmarkSeq() {
        // given
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(1L, 2L);

        // when
        BookmarkResponseDto bookmarkResponseDto = bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkRequestDto).get();

        // then
        assertThat(bookmarkResponseDto.getBookmarkSeq()).isEqualTo(bookmarkRequestDto.getBookmarkSeq());
        assertThat(bookmarkResponseDto.getBookmarkUrl()).isNotNull();
    }
}