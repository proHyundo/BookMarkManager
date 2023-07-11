package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class FolderRepositoryInsertQueryTest {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach(){
        jdbcTemplate.update("ALTER TABLE TFOLDER AUTO_INCREMENT = 2");
    }

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

    @DisplayName("새로운 폴더를 저장할 때, caption 을 입력하지 않으면 빈 문자열로 저장된다.")
    @Test
    void saveWhenCaptionIsNull() {
        // given
        Folder folder = Folder.builder()
                .folderSeq(null)
                .userId(1L)
                .folderParentSeq(1L)
                .folderRootFlag("n")
                .folderName("folderName")
                .folderCaption(null)
                .folderOrder(null)
                .folderScope("p")
                .folderRegDate(null)
                .folderModDate(null)
                .folderDelFlag("n")
                .build();
        // when
        folderRepository.save(folder);
        // then
        assertThat(folderRepository.findByFolderSeq(folder.getFolderSeq()).get())
                .extracting("folderSeq", "userId", "folderCaption", "folderOrder")
                .containsExactlyInAnyOrder(3L, 1L, "", 2L);
    }

    @DisplayName("새로운 폴더를 저장할 때, folderOrder 가 null 인 경우 동일 folderParentSeq 의 가장 큰 folderOrder 에 1 을 더한 값으로 저장된다.")
    @Test
    void saveWhenFolderOrderIsNull() {
        // given
        Folder folder = Folder.builder()
                .folderSeq(3L)
                .userId(1L)
                .folderParentSeq(2L)
                .folderRootFlag("n")
                .folderName("folderName")
                .folderCaption(null)
                .folderOrder(null)
                .folderScope("p")
                .folderRegDate(null)
                .folderModDate(null)
                .folderDelFlag("n")
                .build();

        // when
        folderRepository.save(folder);

        // then
        assertThat(folderRepository.findByFolderSeq(folder.getFolderSeq()).get())
                .extracting("folderSeq", "userId", "folderOrder")
                .containsExactlyInAnyOrder(3L, 1L, 1L);
    }

}