package com.hyun.bookmarkshare.manage.bookmark.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
public class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    @DisplayName("폴더 삭제 시, 대상 폴더에 포함된 북마크들의 deleteFlag 는 'y'로 변경된다.")
    @Test
    void whenDeleteFolderExecutedDeleteAllBookmarks() {
        // given

        // when

        // then
    }
}
