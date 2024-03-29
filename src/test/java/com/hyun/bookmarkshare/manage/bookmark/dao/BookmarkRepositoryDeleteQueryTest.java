package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookmarkRepositoryDeleteQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach() {
        jdbcTemplate.update("ALTER TABLE TBOOKMARK AUTO_INCREMENT = 0;");
    }

    @DisplayName("북마크 삭제 시 deleteFlag 값이 y로 변경된다.")
    @Test
    void deleteBookmarkByBookmarkSeq() {
        // given
        Bookmark bookmark = createBookmark(1L,1L, 1L, "네이버", "https://www.naver.com");
        bookmarkRepository.save(bookmark);

        // when
        int resultRow = bookmarkRepository.deleteByUserIdAndBookmarkSeq(bookmark.getUserId(), bookmark.getBookmarkSeq());

        // then
        assertThat(bookmarkRepository.findByBookmarkSeqForTest(1L).get()).extracting("bookmarkDelFlag").isEqualTo("y");
    }

    private Bookmark createBookmark(Long bookmarkSeq, Long userId, Long folderId, String bookmarkTitle, String bookmarkUrl){
        return Bookmark.builder()
                .bookmarkSeq(bookmarkSeq)
                .userId(userId)
                .folderSeq(folderId)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption("북마크 설명")
                .bookmarkScheme("")
                .bookmarkHost("")
                .bookmarkDomain("")
                .bookmarkUrl(bookmarkUrl)
                .bookmarkRegDate(LocalDateTime.of(2023, 7, 12, 0, 0, 0))
                .bookmarkModDate(LocalDateTime.of(2023, 7, 12, 0, 0, 0))
                .build();
    }

}
