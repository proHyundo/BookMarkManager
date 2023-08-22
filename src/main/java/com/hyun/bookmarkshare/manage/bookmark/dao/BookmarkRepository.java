package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.request.BookmarkCreateRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookmarkRepository {

    // SELECT

    List<Bookmark> findAllByUserIdAndFolderSeqExcludeDeleted(Long userId, Long folderSeq);

    List<Bookmark> findAllByUserIdAndFolderSeq(Long userId, Long folderSeq);

    Optional<BookmarkResponseDto> findByUserIdAndBookmarkSeq(Long userId, Long bookmarkSeq);

    Optional<Bookmark> findByBookmarkSeq(Long bookmarkSeq);

    // INSERT

    int saveBookmark(BookmarkCreateServiceRequestDto bookmarkCreateServiceRequestDto);

    int save(Bookmark bookmark);

    // UPDATE

//    int updateByBookmarkUpdateRequestDto(BookmarkUpdateServiceRequestDto bookmarkUpdateServiceRequestDto);

    int updateOrderByBookmarkRequestDto(BookmarkReorderServiceRequestDto bookmarkReorderServiceRequestDto);

    int update(Bookmark bookmark);

    // DELETE

    int deleteByUserIdAndBookmarkSeq(Long userId, Long bookmarkSeq);
    int deleteAllByUserIdAndFolderSeq(Long userId, Long folderSeq);

    // ONLY FOR TEST

    Optional<Bookmark> findByBookmarkSeqForTest(Long bookmarkSeq);
    int resetAutoIncrementValue();
}
