package com.hyun.bookmarkshare.manage.common.service;

import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.common.dao.ManageRepository;
import com.hyun.bookmarkshare.manage.common.service.response.FolderWithIncludeBookmarksAndFolders;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
class ManageServiceImplTest {

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

    @DisplayName("폴더와 북마크를 트리 구조로 만들기")
    @Test
    void parseResponseToTree() {
        // given
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

        Long userSeq = 1L;

        // when
        List<FolderWithIncludeBookmarksAndFolders> allFoldersAndBookmarks = manageRepository.getAllFoldersAndBookmarks(userSeq);

        // 각 폴더의 폴더식별번호(folderSeq)를 key로 하는 Map을 생성.
        Map<Long, FolderWithIncludeBookmarksAndFolders> folderMap = new HashMap<>();
        for (FolderWithIncludeBookmarksAndFolders folder : allFoldersAndBookmarks) {
            // 순회 대상 폴더에 포함된 북마크 식별번호가 null인 경우, 빈 리스트로 초기화.
            if (folder.getIncludedBookmarks().get(0).getBookmarkSeq() == null){
                folder.setIncludedBookmarks(new ArrayList<>());
            }
            folderMap.put(folder.getFolderSeq(), folder);
        }

        // 각 폴더의 부모 폴더를 Map에서 찾아서 트리 구조를 생성.
        // 조회 결과에서 각 폴더를 순회하면서
        for (FolderWithIncludeBookmarksAndFolders folder : allFoldersAndBookmarks) {
            FolderWithIncludeBookmarksAndFolders parentFolder = folderMap.get(folder.getFolderParentSeq());
            // 부모 폴더가 존재하면
            if (parentFolder != null) {
                // 부모 폴더의 자식 폴더 리스트에 자신(순회 대상 폴더)을 추가.
                if (parentFolder.getIncludedFolders() == null) {
                    parentFolder.setIncludedFolders(new ArrayList<>());
                }
                parentFolder.getIncludedFolders().add(folder);
            }
        }

        // 부모 폴더가 없는 폴더, 즉 트리의 루트를 찾는다.
        FolderWithIncludeBookmarksAndFolders rootFolderIncludeBookmarksAndFolders = allFoldersAndBookmarks.stream()
                .filter(folder -> folder.getFolderParentSeq() == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("루트 폴더가 존재하지 않습니다."));

        // then
        System.out.println("rootFolderIncludeBookmarksAndFolders = " + rootFolderIncludeBookmarksAndFolders.toString());
        assertThat(rootFolderIncludeBookmarksAndFolders.getIncludedFolders()).hasSize(3);
        assertThat(rootFolderIncludeBookmarksAndFolders.getIncludedBookmarks()).hasSize(2);

        // 부모 폴더가 없는 폴더, 즉 트리의 루트를 찾습니다.
//        List<FolderWithIncludeBookmarksAndFolders> rootFolderIncludeBookmarksAndFolders = allFoldersAndBookmarks.stream()
//                .filter(folder -> folder.getFolderParentSeq() == 0)
//                .collect(Collectors.toList());
//
//        // then
//        System.out.println("rootFolderIncludeBookmarksAndFolders = " + rootFolderIncludeBookmarksAndFolders);
//        assertThat(rootFolderIncludeBookmarksAndFolders.get(0).getIncludedFolders()).hasSize(3);
//        assertThat(rootFolderIncludeBookmarksAndFolders.get(0).getIncludedBookmarks()).hasSize(2);
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