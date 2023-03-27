package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService{

    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<Bookmark> getBookList(BookmarkListRequestDto bookmarkListRequestDto) {
        return bookmarkRepository.findAllByUserIdAndFolderParentSeq(bookmarkListRequestDto).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto getBookmark(BookmarkRequestDto bookmarkRequestDto) {
        return bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkRequestDto).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto createBookmark(BookmarkAddRequestDto bookmarkAddRequestDto) {
        return bookmarkRepository.saveBookmark(bookmarkAddRequestDto).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto updateBookmark(BookmarkUpdateRequestDto bookmarkUpdateRequestDto) {
        return bookmarkRepository.updateByBookmarkUpdateRequestDto(bookmarkUpdateRequestDto).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {
        return bookmarkRepository.deleteByUserIdAndBookmarkSeq(bookmarkRequestDto).orElseThrow(
                () -> new NoSuchElementException()
        );
    }


}
