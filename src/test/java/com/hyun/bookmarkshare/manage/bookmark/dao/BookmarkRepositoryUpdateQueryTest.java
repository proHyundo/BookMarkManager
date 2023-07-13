package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkReorderRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class BookmarkRepositoryUpdateQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach() {
        jdbcTemplate.update("ALTER TABLE TBOOKMARK AUTO_INCREMENT = 0;");
    }

    @DisplayName("기존에 존재하는 북마크의 정보를 수정한다.")
    @Test
    void updateByBookmarkUpdateRequestDto(){
        // given
        Bookmark beforeUpdateBookmark = createBookmark(1L, 1L, "다음", "https://www.naver.com");
        int savedBookmarkSeq = bookmarkRepository.save(beforeUpdateBookmark);

        Optional<Bookmark> targetBookmark = bookmarkRepository.findByBookmarkSeq((long) savedBookmarkSeq);
        targetBookmark.get().setBookmarkTitle("네이버");

        // when
        int resultRows = bookmarkRepository.update(targetBookmark.get());

        // then
        assertThat(resultRows).isEqualTo(1);
        Optional<Bookmark> updatedBookmark = bookmarkRepository.findByBookmarkSeq((long) savedBookmarkSeq);
        assertThat(updatedBookmark).get().isNotNull();
        assertThat(updatedBookmark.get().getBookmarkTitle()).isEqualTo(targetBookmark.get().getBookmarkTitle());
    }

    @DisplayName("특정 폴더 내의 북마크 순서들을 수정한다.")
    @Test
    void updateOrderByBookmarkRequestDto(){
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "네이버", "https://www.naver.com");
        Bookmark bookmark2 = createBookmark(1L, 1L, "다음", "https://www.daum.net");
        Bookmark bookmark3 = createBookmark(1L, 1L, "구글", "https://www.google.com");
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);

        List<Long> bookmarkSeqOrder = Arrays.asList(2L, 3L, 1L);
        BookmarkReorderRequestDto bookmarkReorderRequestDto = new BookmarkReorderRequestDto(1L, 1L, bookmarkSeqOrder);
        BookmarkReorderServiceRequestDto bookmarkReorderServiceRequestDto = bookmarkReorderRequestDto.toServiceRequestDto();

        // when
        int resultRows = bookmarkRepository.updateOrderByBookmarkRequestDto(bookmarkReorderServiceRequestDto);

        // then
        assertThat(bookmarkRepository.findAllByUserIdAndFolderSeqExcludeDeleted(1L, 1L))
                .extracting("bookmarkSeq", "bookmarkOrder")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(2L, 1L),
                        Tuple.tuple(3L, 2L),
                        Tuple.tuple(1L, 3L));
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
                .bookmarkRegDate(LocalDateTime.of(2023, 7, 5, 0, 0, 0))
                .bookmarkModDate(LocalDateTime.of(2023, 7, 5, 0, 0, 0))
                .build();
    }
}
