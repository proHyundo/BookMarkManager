package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderReorderServiceRequestDto;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class FolderRepositoryUpdateQueryTest {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach(){
        jdbcTemplate.update("ALTER TABLE TFOLDER AUTO_INCREMENT = 2");
    }

    @DisplayName("폴더 정보를 수정한다.")
    @Test
    void updateByFolderRequestDto() {
        // given
        Folder folder = createFolder(null, 1L, 0L, "folder3", null, "n");
        folderRepository.save(folder);
        Long targetFolderSeq = folder.getFolderSeq();
        Folder targetFolder = createFolder(targetFolderSeq, 1L, 0L, "folder3_changed", null, "n");

        // when
        int updatedRows = folderRepository.update(targetFolder);

        // then
        assertThat(updatedRows).isEqualTo(1);
        assertThat(folderRepository.findByFolderSeq(targetFolderSeq).get())
                .extracting("folderSeq", "folderName")
                .containsExactlyInAnyOrder(targetFolderSeq, "folder3_changed");

    }

    @DisplayName("폴더 정렬 순서를 수정한다.")
    @Test
    void updateOrderByFolderRequestDto() {
        // given
        Folder folder1 = createFolder(3L, 1L, 1L, "folder3", 2L, "n");
        Folder folder2 = createFolder(4L, 1L, 1L, "folder4", 3L, "n");
        folderRepository.save(folder1);
        folderRepository.save(folder2);
        FolderReorderServiceRequestDto targetServiceRequestDto = FolderReorderServiceRequestDto.builder()
                .userId(1L)
                .folderParentSeq(1L)
                .folderSeqOrder(List.of(3L, 4L, 2L))
                .build();
        // when
        folderRepository.updateOrderByFolderServiceRequestDto(targetServiceRequestDto);
        // then
        List<Folder> resultAllByUserIdAndParentSeq = folderRepository.findAllByUserIdAndParentSeq(1L, 1L);
        assertThat(resultAllByUserIdAndParentSeq).extracting("folderSeq", "folderOrder")
                .containsExactly(
                        Tuple.tuple(3L, 1L),
                        Tuple.tuple(4L, 2L),
                        Tuple.tuple(2L, 3L)
                );
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
}