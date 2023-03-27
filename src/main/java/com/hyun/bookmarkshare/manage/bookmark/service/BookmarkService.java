package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookmarkService {

    List<Bookmark> getBookList(BookmarkListRequestDto bookmarkListRequestDto);

    BookmarkResponseDto getBookmark(BookmarkRequestDto bookmarkRequestDto);

    BookmarkResponseDto createBookmark(BookmarkAddRequestDto bookmarkAddRequestDto);

    BookmarkResponseDto updateBookmark(BookmarkUpdateRequestDto bookmarkUpdateRequestDto);

    BookmarkResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto);
}
