package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class FolderRepositoryUpdateQueryTest {

    @Autowired
    private FolderRepository folderRepository;

    @Test
    void deleteByFolderSeq() {
        // g
        Long targetFolderSeq = 16L;
        // w
        int deletedRows = folderRepository.deleteByFolderSeq(targetFolderSeq);
        // t
        assertThat(deletedRows).isEqualTo(1);
        Optional<Folder> deletedFolderMustBeNull = folderRepository.findByFolderSeq(targetFolderSeq);
        assertThat(deletedFolderMustBeNull).isEmpty();
    }

    @Test
    void updateByFolderRequestDto() {
        // g
        FolderRequestDto targetFolderRequestDto = FolderRequestDto.builder()
                .folderSeq(16L)
                .userId(1L)
                .folderParentSeq(1L)
                .folderName("unitTestChange0222")
                .folderCaption("unitTest-caption8")
                .folderScope("p")
                .build();
        // w
        int updatedRows = folderRepository.updateByFolderRequestDto(targetFolderRequestDto);
        // t
        assertThat(updatedRows).isEqualTo(1);
        Optional<Folder> resultFolder = folderRepository.findByFolderSeq(targetFolderRequestDto.getFolderSeq());
        assertThat(resultFolder.get().getFOLDER_NAME()).isEqualTo(targetFolderRequestDto.getFolderName());

    }

    @Test
    void updateOrderByFolderRequestDto() {
        // g
        FolderReorderRequestDto targetFolderRequestDto = FolderReorderRequestDto.builder()
                .userId(1L)
                .folderParentSeq(1L)
                .folderSeqOrder(Arrays.asList(3,14,15,16))
                .build();
        // w
        folderRepository.updateOrderByFolderRequestDto(targetFolderRequestDto);
        // t
        List<Folder> resultAllByUserIdAndParentSeq = folderRepository.findAllByUserIdAndParentSeq(targetFolderRequestDto.getUserId(), targetFolderRequestDto.getFolderParentSeq());
        assertThat(resultAllByUserIdAndParentSeq).hasSize(4);
        assertThat(resultAllByUserIdAndParentSeq).extracting(Folder::getFOLDER_SEQ).containsExactly(3L, 14L, 15L, 16L);


    }
}