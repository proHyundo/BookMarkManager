package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookmarkRepository {

    // SELECT

    List<Bookmark> findAllByUserIdAndFolderSeq(Long userId, Long folderSeq);

    Optional<BookmarkResponseDto> findByUserIdAndBookmarkSeq(Long userId, Long bookmarkSeq);

    Optional<Bookmark> findByBookmarkSeq(Long bookmarkSeq);

    // INSERT

    int saveBookmark(BookmarkAddRequestDto bookmarkAddRequestDto);

    int save(Bookmark bookmark);

    // UPDATE

    int updateByBookmarkUpdateRequestDto(BookmarkUpdateRequestDto bookmarkUpdateRequestDto);

    int updateOrderByBookmarkRequestDto(BookmarkReorderServiceRequestDto bookmarkReorderServiceRequestDto);

    int update(Bookmark bookmark);

    // DELETE

    int deleteByUserIdAndBookmarkSeq(Long userId, Long bookmarkSeq);

    // ONLY FOR TEST

    Optional<Bookmark> findByBookmarkSeqForTest(Long bookmarkSeq);
}
