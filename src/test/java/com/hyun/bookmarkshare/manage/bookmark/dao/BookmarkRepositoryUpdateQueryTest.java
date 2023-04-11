package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@MybatisTest
public class BookmarkRepositoryUpdateQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @DisplayName("BookmarkRepository.updateByBookmarkUpdateRequestDto > 북마크 수정 > Success")
    @Test
    void updateByBookmarkUpdateRequestDto(){
        // given
        BookmarkUpdateRequestDto targetDto = BookmarkUpdateRequestDto.builder()
                .bookmarkSeq(12L)
                .userId(1L)
                .bookmarkCaption("네이버 test 2023-04-11")
                .bookmarkScheme("https")
                .bookmarkHost("www.")
                .bookmarkDomain("naver.com")
                .bookmarkPort(":8080")
                .bookmarkPath("/test/2023-04-11/14:09")
                .bookmarkUrl("https://www.naver.com/test/2023-04-11/14:09")
                .build();

        // when
        bookmarkRepository.updateByBookmarkUpdateRequestDto(targetDto);
        Optional<BookmarkResponseDto> resultDto = bookmarkRepository.findByUserIdAndBookmarkSeq(targetDto.getUserId(), targetDto.getBookmarkSeq());

        // then
        assertThat(resultDto).get().isNotNull();
        assertThat(resultDto.get().getBookmarkUrl()).isEqualTo(targetDto.getBookmarkUrl());
    }
}
