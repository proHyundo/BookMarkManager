package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class FolderRepositoryInsertQueryTest {

    @Autowired
    private FolderRepository folderRepository;

    @DisplayName("새로운 폴더를 저장한다.")
    @Test
    void saveNewFolder() {
        // given
        Folder folder = Folder.builder()
                .folderSeq(null)
                .userId(1L)
                .folderParentSeq(0L)
                .folderRootFlag("n")
                .folderName("unitTest0222_1252")
                .folderCaption("folderCaption")
                .folderScope("p")
                .folderRegDate(null)
                .folderModDate(null)
                .folderDelFlag("n")
                .folderOrder(null)
                .build();

        // when
        folderRepository.save(folder);
        Long savedFolderSeq = folder.getFolderSeq();
        // then
        assertThat(folderRepository.findByFolderSeq(savedFolderSeq).get())
                .extracting("folderSeq", "folderName")
                .containsExactlyInAnyOrder(3L, "unitTest0222_1252");

    }
}