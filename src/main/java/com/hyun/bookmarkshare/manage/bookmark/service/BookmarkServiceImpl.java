package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService{

    private final BookmarkRepository bookmarkRepository;
    private final UrlParser urlParser;

    @Override
    public List<Bookmark> getBookList(BookmarkListRequestDto bookmarkListRequestDto) {
        // https://stackoverflow.com/questions/11201420/which-exception-to-throw-if-list-is-empty-in-java
        List<Bookmark> allByUserIdAndFolderParentSeq = bookmarkRepository.findAllByUserIdAndFolderSeq(bookmarkListRequestDto.getUserId(), bookmarkListRequestDto.getFolderSeq());
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
        int updatedRows = bookmarkRepository.saveBookmark(bookmarkAddRequestDto);
        validateSqlUpdatedRows(updatedRows);
        return bookmarkAddRequestDto.toBookmarkResponseDto(bookmarkAddRequestDto);
    }

    @Override
    public BookmarkResponseDto updateBookmark(BookmarkUpdateRequestDto bookmarkUpdateRequestDto) {
        int updatedRows = bookmarkRepository.updateByBookmarkUpdateRequestDto(bookmarkUpdateRequestDto);
        validateSqlUpdatedRows(updatedRows);
        return bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkUpdateRequestDto.getUserId(), bookmarkUpdateRequestDto.getBookmarkSeq()).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {
        BookmarkResponseDto resultDto = BookmarkResponseDto.builder().build();
        int updatedRows = bookmarkRepository.deleteByUserIdAndBookmarkSeq(bookmarkRequestDto);
        validateSqlUpdatedRows(updatedRows);
        return resultDto;
    }

    @Override
    public List<Long> updateBookmarkOrder(List<BookmarkReorderRequestDto> requestDtoList) {
        for ( BookmarkReorderRequestDto bookmarkReorderRequestDto : requestDtoList) {
            int updatedRows = bookmarkRepository.updateOrderByBookmarkRequestDto(bookmarkReorderRequestDto);
            validateSqlUpdatedRows(updatedRows);
        }
        List<List<Long>> resultList = new ArrayList<>();
        requestDtoList.forEach(bookmarkReorderRequestDto ->
                resultList.add(bookmarkReorderRequestDto.getBookmarkSeqOrder())
        );
        return null;
    }

    private void validateSqlUpdatedRows(int updatedRows) {
        if(updatedRows != 1) throw new IllegalStateException();
    }

}
