package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.BookmarkListRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookmarkRepository {

    Optional<List<Bookmark>> findAllByUserIdAndFolderParentSeq(BookmarkListRequestDto bookmarkListRequestDto);
}
