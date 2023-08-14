package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkUpdateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkSeqResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
public class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach(){
        jdbcTemplate.update("ALTER TABLE TBOOKMARK AUTO_INCREMENT = 0");
    }

    @DisplayName("하나의 북마크를 조회한다.")
    @Test
    void getBookmark() {
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1", "bookmarkUrl1");
        Bookmark bookmark2 = createBookmark(1L, 1L, "bookmarkTitle2", "bookmarkUrl2");
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        BookmarkServiceRequestDto requestDto = BookmarkServiceRequestDto.builder()
                .userId(1L)
                .bookmarkSeq(2L)
                .build();
        // when
        BookmarkResponseDto responseDto = bookmarkService.getBookmark(requestDto);
        // then
        assertThat(responseDto)
                .extracting("bookmarkSeq", "bookmarkTitle")
                .containsExactly(2L, "bookmarkTitle2");
    }

    @DisplayName("특정 폴더에 속한 북마크 리스트를 조회한다.")
    @Test
    void getBookList() {
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1", "bookmarkUrl1");
        Bookmark bookmark2 = createBookmark(1L, 1L, "bookmarkTitle2", "bookmarkUrl2");
        Bookmark bookmark3 = createBookmark(1L, 2L, "bookmarkTitle3", "bookmarkUrl3");
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);
        BookmarkServiceRequestDto serviceRequestDto = BookmarkServiceRequestDto.builder()
                .userId(1L)
                .bookmarkSeq(null)
                .folderSeq(1L)
                .build();
        // when
        List<BookmarkResponseDto> bookmarkList = bookmarkService.getBookList(serviceRequestDto);
        // then
        assertThat(bookmarkList).hasSize(2);
        assertThat(bookmarkList).extracting("bookmarkSeq", "bookmarkTitle")
                .containsExactly(
                        tuple(1L, "bookmarkTitle1"),
                        tuple(2L, "bookmarkTitle2")
                );

    }

    @DisplayName("새로운 북마크를 추가할 때, 정렬 순서는 포함된 북마크들의 최대 정렬 순서 +1 이다.")
    @Test
    void createBookmark() {
        // given
        BookmarkCreateServiceRequestDto requestDto = BookmarkCreateServiceRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .bookmarkTitle("bookmarkTitle")
                .bookmarkCaption("bookmarkCaption")
                .bookmarkScheme(null)
                .bookmarkHost(null)
                .bookmarkPort(null)
                .bookmarkDomain(null)
                .bookmarkPath(null)
                .bookmarkUrl("https://www.naver.com/sports/123")
                .build();
        // when
        BookmarkResponseDto responseDto = bookmarkService.createBookmark(requestDto);
        // then
        assertThat(responseDto.getBookmarkOrder()).isEqualTo(1L);
    }

    @DisplayName("새로운 북마크를 추가할 때, Url Parser 에 의해 분석된 정보가 저장된다.")
    @Test
    void createBookmarkSetFieldByUrlParser() {
        // given
        BookmarkCreateServiceRequestDto requestDto = BookmarkCreateServiceRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .bookmarkTitle("bookmarkTitle")
                .bookmarkCaption("bookmarkCaption")
                .bookmarkScheme(null)
                .bookmarkHost(null)
                .bookmarkPort(null)
                .bookmarkDomain(null)
                .bookmarkPath(null)
                .bookmarkUrl("https://www.naver.com/sports/123")
                .build();
        // when
        BookmarkResponseDto responseDto = bookmarkService.createBookmark(requestDto);
        // then
        assertThat(bookmarkRepository.findByBookmarkSeq(responseDto.getBookmarkSeq()).get())
                .extracting("bookmarkScheme", "bookmarkHost", "bookmarkPort", "bookmarkDomain", "bookmarkPath")
                .containsExactly("https://", "www.", null, "naver.com", "/sports/123");
    }

    @DisplayName("북마크 수정 시, 기존 북마크를 조회해 북마크 정보를 수정한다.")
    @Test
    void updateBookmark() {
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1",
                "https://www.naver.com/sports/123");
        bookmarkRepository.save(bookmark1);
        BookmarkUpdateServiceRequestDto requestDto = BookmarkUpdateServiceRequestDto.builder()
                .userId(1L)
                .bookmarkSeq(1L)
                .bookmarkTitle("bookmarkTitle")
                .bookmarkCaption("bookmarkCaption")
                .bookmarkUrl("https://www.naver.com/sports/456")
                .build();
        // when
        BookmarkResponseDto responseDto = bookmarkService.updateBookmark(requestDto);
        // then
        assertThat(responseDto).extracting("bookmarkSeq", "bookmarkTitle", "bookmarkCaption", "bookmarkUrl")
                .containsExactly(1L, "bookmarkTitle", "bookmarkCaption", "https://www.naver.com/sports/456");
    }

    @DisplayName("북마크 정렬 순서를 변경한다.")
    @Test
    void updateBookmarkOrder() {
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1", "bookmarkUrl1");
        Bookmark bookmark2 = createBookmark(1L, 1L, "bookmarkTitle2", "bookmarkUrl2");
        Bookmark bookmark3 = createBookmark(1L, 1L, "bookmarkTitle3", "bookmarkUrl3");
        Bookmark bookmark4 = createBookmark(1L, 2L, "bookmarkTitle4", "bookmarkUrl4");
        Bookmark bookmark5 = createBookmark(1L, 2L, "bookmarkTitle5", "bookmarkUrl5");
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);
        bookmarkRepository.save(bookmark4);
        bookmarkRepository.save(bookmark5);
        List<BookmarkReorderServiceRequestDto> requestList = List.of(BookmarkReorderServiceRequestDto.builder()
                .userId(1L)
                .folderSeq(1L)
                .bookmarkSeqOrder(List.of(3L, 1L, 2L))
                .build(),
                BookmarkReorderServiceRequestDto.builder()
                .userId(1L)
                .folderSeq(2L)
                .bookmarkSeqOrder(List.of(5L, 4L))
                .build()
                );
        // when
        bookmarkService.updateBookmarkOrder(requestList);
        // then
        assertThat(bookmarkRepository.findAllByUserIdAndFolderSeqExcludeDeleted(1L, 1L))
                .extracting("bookmarkSeq", "bookmarkOrder")
                .containsExactly(
                        tuple(3L, 1L),
                        tuple(1L, 2L),
                        tuple(2L, 3L)
                );
        assertThat(bookmarkRepository.findAllByUserIdAndFolderSeqExcludeDeleted(1L, 2L))
                .extracting("bookmarkSeq", "bookmarkOrder")
                .containsExactly(
                        tuple(5L, 1L),
                        tuple(4L, 2L)
                );
    }

    @DisplayName("단일 북마크 삭제 시, 대상 북마크의 deleteFlag 는 'y'로 변경된다.")
    @Test
    void deleteOneBookmark() {
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1", "bookmarkUrl1");
        bookmarkRepository.save(bookmark1);
        BookmarkServiceRequestDto requestDto = BookmarkServiceRequestDto.builder()
                .userId(1L)
                .bookmarkSeq(1L)
                .folderSeq(1L)
                .build();
        // when
        BookmarkSeqResponse responseDto = bookmarkService.deleteBookmark(requestDto);
        // then
        assertThat(responseDto).extracting("bookmarkSeq").isEqualTo(1L);
    }


    @DisplayName("폴더 삭제 시, 대상 폴더에 포함된 모든 북마크들의 deleteFlag 는 'y'로 변경된다.")
    @Test
    void whenDeleteFolderExecutedDeleteAllBookmarks() {
        // given
        Bookmark bookmark1 = createBookmark(1L, 1L, "bookmarkTitle1", "bookmarkUrl1");
        Bookmark bookmark2 = createBookmark(1L, 1L, "bookmarkTitle2", "bookmarkUrl2");
        Bookmark bookmark3 = createBookmark(1L, 2L, "bookmarkTitle3", "bookmarkUrl3");
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);
        // when
        bookmarkService.deleteAllBookmarksInFolderSeqAndUserId(List.of(1L, 2L), 1L);
        // then
        assertThat(bookmarkRepository.findAllByUserIdAndFolderSeq(1L, 1L))
                .extracting("bookmarkSeq", "bookmarkDelFlag")
                .containsExactly(
                        tuple(1L, "y"),
                        tuple(2L, "y")
                );
        assertThat(bookmarkRepository.findAllByUserIdAndFolderSeq(1L, 2L))
                .extracting("bookmarkSeq", "bookmarkDelFlag")
                .containsExactly(
                        tuple(3L, "y")
                );
    }

    private Bookmark createBookmark(Long userId, Long folderSeq, String bookmarkTitle, String bookmarkUrl){
        return Bookmark.builder()
                .bookmarkSeq(null)
                .userId(userId)
                .folderSeq(folderSeq)
                .bookmarkTitle(bookmarkTitle)
                .bookmarkCaption("bookmarkCaption")
                .bookmarkScheme("")
                .bookmarkHost("")
                .bookmarkPort("")
                .bookmarkDomain("")
                .bookmarkPath("")
                .bookmarkUrl(bookmarkUrl)
                .bookmarkRegDate(null)
                .bookmarkModDate(null)
                .bookmarkOrder(null)
                .bookmarkDelFlag("n")
                .build();
    }
}
