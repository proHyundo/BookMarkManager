package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService{

    private final BookmarkRepository bookmarkRepository;
    private final UrlParser urlParser;

    @Override
    public List<Bookmark> getBookList(BookmarkListRequestDto bookmarkListRequestDto) {
        // https://stackoverflow.com/questions/11201420/which-exception-to-throw-if-list-is-empty-in-java
        List<Bookmark> allByUserIdAndFolderParentSeq = bookmarkRepository.findAllByUserIdAndFolderParentSeq(bookmarkListRequestDto);
        if(allByUserIdAndFolderParentSeq.isEmpty()) throw new IllegalStateException();
//        return allByUserIdAndFolderParentSeq.stream().map(Bookmark::toBookmarkResponseDto).collect(Collectors.toList());
        return allByUserIdAndFolderParentSeq;
    }

    @Override
    public BookmarkResponseDto getBookmark(BookmarkRequestDto bookmarkRequestDto) {
        return bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkRequestDto.getUserId(), bookmarkRequestDto.getBookmarkSeq()).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto createBookmark(BookmarkAddRequestDto bookmarkAddRequestDto) {
        // bookmark url disunite & set units to bookmarkAddRequestDto
        urlParser.assignUrlFields(bookmarkAddRequestDto);
        int resultRows = bookmarkRepository.saveBookmark(bookmarkAddRequestDto);
        if(resultRows != 1) throw new IllegalStateException();
        return bookmarkAddRequestDto.toBookmarkResponseDto(bookmarkAddRequestDto);
    }

    @Override
    public BookmarkResponseDto updateBookmark(BookmarkUpdateRequestDto bookmarkUpdateRequestDto) {
        int resultRows = bookmarkRepository.updateByBookmarkUpdateRequestDto(bookmarkUpdateRequestDto);
        if(resultRows != 1) throw new IllegalStateException();
        return bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkUpdateRequestDto.getUserId(), bookmarkUpdateRequestDto.getBookmarkSeq()).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {
        BookmarkResponseDto resultDto = BookmarkResponseDto.builder().build();
        int resultRows = bookmarkRepository.deleteByUserIdAndBookmarkSeq(bookmarkRequestDto);
        if (resultRows != 1) throw new IllegalStateException();
        return resultDto;
    }


}
