package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class BookmarkRepositorySelectQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach() {
        jdbcTemplate.update("ALTER TABLE TBOOKMARK AUTO_INCREMENT = 0;");
    }

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


    @DisplayName("사용자id와 폴더id로 특정 사용자의 특정 폴더에 속한 삭제되지 않은 모든 북마크들을 가져온다.")
    @Test
    void findAllByUserIdAndFolderSeqExcludeDeleted() {
        // given (사용자1, 사용자2, 폴더1, 폴더2 존재)
        bookmarkRepository.save(createBookmark(1L, 1L, 1L, "bookmarkTitle1"));
        bookmarkRepository.save(createBookmark(2L, 1L, 1L, "bookmarkTitle2"));
        bookmarkRepository.save(createBookmark(3L, 1L, 2L, "bookmarkTitle3"));

        // when
        List<Bookmark> bookmarkListResult = bookmarkRepository.findAllByUserIdAndFolderSeqExcludeDeleted(1L, 1L);

        // then
        assertThat(bookmarkListResult.size()).isEqualTo(2);
        assertThat(bookmarkListResult).extracting("bookmarkTitle")
                        .containsExactlyInAnyOrder(
                                "bookmarkTitle1",
                                "bookmarkTitle2"
                        );
    }

    @DisplayName("사용자 id와 폴더 id로 모든 북마크를 조회할 때 존재하는 북마크가 없는 경우 비어있는 객체가 반환된다.")
    @Test
    void findAllByUserIdAndFolderSeqFail1(){
        // given (사용자1, 사용자2, 폴더1, 폴더2 존재)
        bookmarkRepository.save(createBookmark(1L, 1L, 1L, "bookmarkTitle1"));
        bookmarkRepository.save(createBookmark(2L, 1L, 1L, "bookmarkTitle2"));
        bookmarkRepository.save(createBookmark(3L, 1L, 2L, "bookmarkTitle3"));

        // when
        List<Bookmark> bookmarkListResult = bookmarkRepository.findAllByUserIdAndFolderSeqExcludeDeleted(1L, 99L);

        // then
        assertThat(bookmarkListResult).isEmpty();
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
                .bookmarkRegDate(LocalDateTime.of(2023, 7, 3, 0,0,0))
                .bookmarkModDate(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .bookmarkDelFlag("n")
                .build();
    }

    private Bookmark createBookmark(Long bookmarkSeq, Long userId, Long folderSeq, String bookmarkTitle){
        return Bookmark.builder()
                .bookmarkSeq(bookmarkSeq)
                .userId(userId)
                .folderSeq(folderSeq)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption("caption sample")
                .bookmarkScheme("http://")
                .bookmarkHost("www.")
                .bookmarkPort(null)
                .bookmarkDomain("google.com")
                .bookmarkPath("/")
                .bookmarkUrl("http://www.google.com/")
                .bookmarkOrder(1L)
                .bookmarkRegDate(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .bookmarkModDate(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .bookmarkDelFlag("n")
                .build();
    }

}