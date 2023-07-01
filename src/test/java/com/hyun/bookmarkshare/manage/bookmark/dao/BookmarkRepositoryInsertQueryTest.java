package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkAddRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.service.UrlParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class BookmarkRepositoryInsertQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Rollback(false)
    @DisplayName("BookmarkRepository.saveNewBookmark > 새 북마크 저장 > Success")
    @Test
    void saveNewBookmark() {
        // given
        BookmarkAddRequestDto bookmarkAddRequestDto = BookmarkAddRequestDto.builder()
                .userId(1L)
                .folderSeq(49L)
                .bookmarkUrl("https://www.naver.com/test/2023-04-06/17:04")
                .bookmarkTitle("네이버 test 2023-04-06")
                .build();
        UrlParser urlParser = new UrlParser();
        BookmarkAddRequestDto targetDto = urlParser.assignUrlFields(bookmarkAddRequestDto);

        // when
        int resultRows = bookmarkRepository.saveBookmark(targetDto);

        // then
        assertThat(resultRows).isEqualTo(1);
        assertThat(targetDto.getBookSeq()).isNotNull();
        System.out.println(targetDto.getBookSeq());
    }

}
