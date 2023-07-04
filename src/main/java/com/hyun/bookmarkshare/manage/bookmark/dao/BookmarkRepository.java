package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookmarkRepository {

    List<Bookmark> findAllByUserIdAndFolderSeq(Long userId, Long folderSeq);

    Optional<BookmarkResponseDto> findByUserIdAndBookmarkSeq(Long userId, Long bookmarkSeq);

    Optional<Bookmark> findByBookmarkSeq(Long bookmarkSeq);

    int saveBookmark(BookmarkAddRequestDto bookmarkAddRequestDto);

    int updateByBookmarkUpdateRequestDto(BookmarkUpdateRequestDto bookmarkUpdateRequestDto);

    int deleteByUserIdAndBookmarkSeq(BookmarkRequestDto bookmarkRequestDto);

    int updateOrderByBookmarkRequestDto(BookmarkReorderRequestDto bookmarkReorderRequestDto);

    void save(Bookmark bookmark);
}
