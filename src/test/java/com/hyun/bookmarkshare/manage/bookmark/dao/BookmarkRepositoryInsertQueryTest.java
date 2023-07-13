package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class BookmarkRepositoryInsertQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach() {
        jdbcTemplate.update("ALTER TABLE TBOOKMARK AUTO_INCREMENT = 0;");
    }

    @DisplayName("새 북마크를 저장한다.")
    @Test
    void save() {
        // given
        Bookmark target = createBookmark(1L, 1L, "네이버", "https://www.naver.com");

        // when
        bookmarkRepository.save(target);

        // then
        Long savedBookmarkSeq = target.getBookmarkSeq();
        assertThat(savedBookmarkSeq).isEqualTo(1L);
        Optional<Bookmark> resultBookmark = bookmarkRepository.findByBookmarkSeq(savedBookmarkSeq);
        assertThat(resultBookmark.get().getBookmarkTitle()).isEqualTo("네이버");

        // https://frhyme.github.io/others/DB_NOT_NULL_vs_default/
        // https://cocoon1787.tistory.com/843
    }

    private Bookmark createBookmark(Long userId, Long folderId, String bookmarkTitle, String bookmarkUrl){
        return Bookmark.builder()
                .bookmarkSeq(null)
                .userId(userId)
                .folderSeq(folderId)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption("북마크 설명")
                .bookmarkScheme("scheme")
                .bookmarkHost("host")
                .bookmarkDomain("domain")
                .bookmarkUrl(bookmarkUrl)
                .bookmarkRegDate(LocalDateTime.of(2023, 7, 5, 12, 30, 30))
                .bookmarkModDate(LocalDateTime.of(2023, 7, 5, 12, 30, 30))
                .build();
    }

}
