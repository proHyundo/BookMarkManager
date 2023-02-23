package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
@Transactional
class FolderRepositorySelectQueryTest {

    @Autowired
    private FolderRepository folderRepository;

    @Test
    void findAllByUserIdAndParentSeq() {
        // given
        Long userId = 1L;
        Long parentSeq = 1L;
        // when
        List<Folder> resultList = folderRepository.findAllByUserIdAndParentSeq(userId, parentSeq);
        // then
        assertThat(resultList).hasSize(4);

    }

    @DisplayName("findAllByUserId - success")
    @Test
    void findAllByUserId() {
        // given
        Long userId = 1L;
        // when
        List<Folder> allByUserId = folderRepository.findAllByUserId(userId);
        System.out.println("allByUserId 결과 row 개수 >> "+allByUserId.size());
        // then
        assertThat(allByUserId).hasSize(15);
    }

    @DisplayName("findByFolderSeq - success")
    @Test
    void findByFolderSeq() {
        // given
        Long targetSeq = 1L;
        // when
        Optional<Folder> resultFolder = folderRepository.findByFolderSeq(targetSeq);
        // then
        assertThat(resultFolder.get().getFOLDER_SEQ()).isEqualTo(1L);
    }
}