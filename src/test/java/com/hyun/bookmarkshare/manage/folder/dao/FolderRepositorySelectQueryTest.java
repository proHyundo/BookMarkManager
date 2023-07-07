package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class FolderRepositorySelectQueryTest {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    void initEach(){
        jdbcTemplate.update("ALTER TABLE TFOLDER AUTO_INCREMENT = 2");
    }

    @DisplayName("사용자 id와 부모폴더 id로 부모폴더 내부의 폴더 리스트를 조회한다.")
    @Test
    void findAllByUserIdAndParentSeq() {
        // given
        Folder folder1 = createFolder(3L, 1L, 1L, "folder3");
        Folder folder2 = createFolder(4L, 1L, 1L, "folder4");
        Folder folder3 = createFolder(5L, 1L, 1L, "folder5");
        folderRepository.save(folder1);
        folderRepository.save(folder2);
        folderRepository.save(folder3);

        // when
        // then
        assertThat(folderRepository.findAllByUserIdAndParentSeq(1L, 1L))
                .extracting("folderSeq")
                .containsExactlyInAnyOrder(2L, 3L, 4L, 5L);

    }

    @DisplayName("사용자 id로 사용자의 모든 폴더 리스트를 조회할 때, 상위폴더 순서인 동시에 빠른 정렬 순으로 조회한다.")
    @Test
    void findAllByUserId() {
        // given
        Folder folder1 = createFolder(3L, 1L, 0L, "folder3");
        Folder folder2 = createFolderWithFolderOrder(4L, 1L, 1L, "folder4", 3L);
        Folder folder3 = createFolderWithFolderOrder(5L, 1L, 1L, "folder5", 2L);
        folderRepository.save(folder1);
        folderRepository.save(folder2);
        folderRepository.save(folder3);

        // when
        // then
        assertThat(folderRepository.findAllByUserId(1L)).extracting("folderSeq", "folderName", "folderOrder")
                .containsExactly(
                        tuple(1L, "p_folder1", 1L),
                        tuple(3L, "folder3", 2L),
                        tuple(2L, "folder2", 1L),
                        tuple(5L, "folder5", 2L),
                        tuple(4L, "folder4", 3L)
                );
    }

    @DisplayName("폴더 id로 삭제되지 않은 단일 폴더를 조회한다.")
    @Test
    void findByExistFolderSeq() {
        // given
        Folder folder1 = createFolderWithDeleteFlag(3L, 1L, 1L, "n");
        folderRepository.save(folder1);
        // when
        Optional<Folder> result = folderRepository.findByFolderSeq(3L);
        // then
        assertThat(result).isNotEmpty();
    }

    @DisplayName("폴더 id로 삭제된 단일 폴더를 조회하면 비어있는 Optional 객체가 반환된다.")
    @Test
    void findByDeletedFolderSeq() {
        // given
        Folder folder1 = createFolderWithDeleteFlag(3L, 1L, 1L, "y");
        folderRepository.save(folder1);
        // when
        Optional<Folder> result = folderRepository.findByFolderSeq(3L);
        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("폴더 id로 삭제여부와 관계없이 폴더를 조회한다.")
    @Test
    void findByFolderSeqEvenIfDeleted() {
        // given
        Folder folder1 = createFolderWithDeleteFlag(3L, 1L, 1L, "y");
        folderRepository.save(folder1);

        // when
        Optional<Folder> result = folderRepository.findByFolderSeqEvenIfDeleted(3L);
        // then
        assertThat(result.get().getFolderSeq()).isEqualTo(folder1.getFolderSeq());
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

    private Folder createFolderWithFolderOrder(Long folderSeq, Long userId, Long parentSeq, String folderName, Long folderOrder){
        return createFolder(folderSeq, userId, parentSeq, folderName, folderOrder, "n");
    }

    private Folder createFolderWithDeleteFlag(Long folderSeq, Long userId, Long parentSeq, String folderDeleteFlag){
        return createFolder(folderSeq, userId, parentSeq, "folderName", null, folderDeleteFlag);
    }

}