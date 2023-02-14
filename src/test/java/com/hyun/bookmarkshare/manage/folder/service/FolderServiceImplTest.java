package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.common.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FolderServiceImplTest {

    @Autowired
    FolderRepository repository;

    @Test
    void findAllByUserIdFolderList() {
        final String name = "상품명";
        final int price = 1000;

        final AddProductRequest request = new AddProductRequest(name, price);
    }

    private class AddProductRequest {
        public AddProductRequest(final String name, final int price) {
            Assert.hasText("");
        }
    }
}