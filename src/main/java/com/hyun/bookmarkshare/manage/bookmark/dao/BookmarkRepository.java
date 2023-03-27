package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookmarkRepository {

    Optional<List<Bookmark>> findAllByUserIdAndFolderParentSeq(BookmarkListRequestDto bookmarkListRequestDto);

    Optional<BookmarkResponseDto> findByUserIdAndBookmarkSeq(BookmarkRequestDto bookmarkRequestDto);

    Optional<BookmarkResponseDto> saveBookmark(BookmarkAddRequestDto bookmarkAddRequestDto);

    Optional<BookmarkResponseDto> updateByBookmarkUpdateRequestDto(BookmarkUpdateRequestDto bookmarkUpdateRequestDto);

    Optional<BookmarkResponseDto> deleteByUserIdAndBookmarkSeq(BookmarkRequestDto bookmarkRequestDto);
}
