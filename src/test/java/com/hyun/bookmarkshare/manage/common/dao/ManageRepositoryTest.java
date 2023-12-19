package com.hyun.bookmarkshare.manage.common.dao;

import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.common.service.response.FolderWithIncludeBookmarksAndFolders;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class ManageRepositoryTest {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private ManageRepository manageRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    void initEach(){
        jdbcTemplate.update("ALTER TABLE TFOLDER AUTO_INCREMENT = 2;");
        jdbcTemplate.update("ALTER TABLE TBOOKMARK AUTO_INCREMENT = 0;");
    }

    @DisplayName("특정 사용자의 모든 북마크와 모든 폴더를 한번에 조회")
    @Test
    void selectAllFoldersAndBookmarksOfUser() {
        // given
        // VALUES(1, 0, 'y')
        // VALUES(2, 1, 'n')
        Folder folder1 = createFolder(3L, 1L, 1L, "folder3 in root");
        Folder folder2 = createFolder(4L, 1L, 1L, "folder4 in root");
        Folder folder3 = createFolder(5L, 1L, 2L, "folder5 in folder2");
        folderRepository.save(folder1);
        folderRepository.save(folder2);
        folderRepository.save(folder3);

        Bookmark bookmark1 = createBookmark(1L, 1L, 1L,"bookmarkTitle1");
        Bookmark bookmark2 = createBookmark(2L, 1L, 1L,"bookmarkTitle2");
        Bookmark bookmark3 = createBookmark(3L, 2L, 2L,"bookmarkTitle3");
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);

        // when
        // then
        List<FolderWithIncludeBookmarksAndFolders> allFoldersAndBookmarks = manageRepository.getAllFoldersAndBookmarks(1L);
        System.out.println("allFoldersAndBookmarks = " + allFoldersAndBookmarks.toString());
    }

    private Folder createFolder(Long folderSeq, Long userId, Long parentSeq, String folderName, Long folderOrder, String folderDeleteFlag) {
        return Folder.builder()
                .folderSeq(folderSeq)
                .userId(userId)
                .folderParentSeq(parentSeq)
                .folderOrder(folderOrder)
                .folderRootFlag("n")
                .folderName(folderName)
                .folderCaption("folderCaption")
                .folderScope("p")
                .folderRegDate(LocalDateTime.of(2023, 7, 6, 0, 0, 0))
                .folderModDate(LocalDateTime.of(2023, 7, 6, 0, 0, 0))
                .folderDelFlag(folderDeleteFlag)
                .build();
    }

    private Folder createFolder(Long folderSeq, Long userId, Long parentSeq, String folderName) {
        return createFolder(folderSeq, userId, parentSeq, folderName, null, "n");
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