package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookmarkRepositoryDeleteQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Test
    void deleteBookmarkByBookmarkSeq() {
        // given
        Long userId = 1L;
        Long bookmarkSeq = 12L;
        BookmarkRequestDto targetDto = new BookmarkRequestDto(userId, bookmarkSeq);

        // when
        int resultRow = bookmarkRepository.deleteByUserIdAndBookmarkSeq(targetDto);

        // then
        assertThat(resultRow).isEqualTo(1);
        assertThat(bookmarkRepository.findByUserIdAndBookmarkSeq(userId, bookmarkSeq)).isEmpty();
    }

}
