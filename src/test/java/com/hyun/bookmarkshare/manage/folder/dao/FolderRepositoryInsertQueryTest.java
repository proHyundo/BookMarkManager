package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderCreateRequestDto;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
@Transactional
class FolderRepositoryInsertQueryTest {

    @Autowired
    private FolderRepository folderRepository;

    @DisplayName("FolderRepository.saveNewFolder > 새 폴더 생성 > 성공")
    @Test
    void saveNewFolder() {
        // given
        FolderCreateRequestDto targetRequestDto = FolderCreateRequestDto
                .builder()
                .folderSeq(null)
                .userId(1L)
                .folderParentSeq(0L)
                .folderName("unitTest0222_1252")
                .folderCaption("")
                .folderScope("p")
                .build();

        // when
        int insertedRows = folderRepository.saveNewFolder(targetRequestDto);

        // then
        assertThat(insertedRows).isEqualTo(1);
        Optional<Folder> resultFolder = folderRepository.findByFolderSeq(targetRequestDto.getFolderSeq());
        assertThat(targetRequestDto.getFolderName()).isEqualTo(resultFolder.get().getFolderName());

    }
}