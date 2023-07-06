package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookmarkRepositoryDeleteQueryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

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
                .bookmarkRegDate(Date.valueOf(LocalDate.of(2023, 7, 5)))
                .bookmarkModDate(Date.valueOf(LocalDate.of(2023, 7, 5)))
                .build();
    }

}
