package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkAddRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.UrlParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class BookmarkRepositoryInsertQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @DisplayName("새 북마크를 저장한다.")
    @Test
    void save() {
        // given
        Bookmark target = createBookmark(1L, 1L, "네이버", "https://www.naver.com");

        // when
        int savedBookmarkSeq = bookmarkRepository.save(target);

        // then
        assertThat(savedBookmarkSeq).isEqualTo(1);
        Optional<Bookmark> resultBookmark = bookmarkRepository.findByBookmarkSeq(Integer.toUnsignedLong(savedBookmarkSeq));
        assertThat(resultBookmark.get().getBookmarkTitle()).isEqualTo("네이버");

        // https://frhyme.github.io/others/DB_NOT_NULL_vs_default/
        // https://cocoon1787.tistory.com/843
    }

    private Bookmark createBookmark(Long userId, Long folderId, String bookmarkTitle, String bookmarkUrl){
        return Bookmark.builder()
                .userId(userId)
                .folderSeq(folderId)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption("북마크 설명")
                .bookmarkScheme("")
                .bookmarkHost("")
                .bookmarkDomain("")
                .bookmarkUrl(bookmarkUrl)
                .bookmarkRegDate(Date.valueOf(LocalDate.of(2023, 7, 5)))
                .bookmarkModDate(Date.valueOf(LocalDate.of(2023, 7, 5)))
                .build();
    }

}
