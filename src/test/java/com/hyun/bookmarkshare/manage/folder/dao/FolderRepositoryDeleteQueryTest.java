package com.hyun.bookmarkshare.manage.folder.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class FolderRepositoryDeleteQueryTest {
    @Autowired
    FolderRepository folderRepository;

    @DisplayName("폴더 id로 폴더를 삭제처리 한다.")
    @Test
    void deleteFolderBy() {
        // given by data.sql

        // when
        int deletedRows = folderRepository.deleteByFolderSeq(2L);

        // then
        assertThat(deletedRows).isEqualTo(1);
        assertThat(folderRepository.findByFolderSeqEvenIfDeleted(2L).get().getFolderDelFlag()).isEqualTo("y");
    }
}
