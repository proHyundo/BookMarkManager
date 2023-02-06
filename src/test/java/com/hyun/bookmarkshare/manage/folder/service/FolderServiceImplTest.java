package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.common.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FolderServiceImplTest {

    @Autowired
    FolderRepository repository;

    @Test
    void findAllByUserIdFolderList() {
        List<Folder> allByUserId = repository.findAllByUserId("1");
        allByUserId.forEach(folder -> System.out.println(folder.toString()));
    }
}