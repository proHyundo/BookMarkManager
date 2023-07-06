package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
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

    // TODO : 신규 북마크 저장 로직 변경
    @Override
    public BookmarkResponseDto updateBookmark(BookmarkUpdateRequestDto bookmarkUpdateRequestDto) {
        // 요청받은 북마크 식별번호로 기존 북마크 객체를 꺼내온다.

        // 존재하는 북마크라면, 기존 북마크 객체의 정보를 수정 요청받은 정보로 덮어 쓴다.

        // 덮어쓴 북마크 객체를 저장한다.

        // 저장한 북마크 객체를 응답하기 위해 반환객체로 변환한다.

        // 반환객체를 반환한다.
        int updatedRows = bookmarkRepository.updateByBookmarkUpdateRequestDto(bookmarkUpdateRequestDto);
        validateSqlUpdatedRows(updatedRows);
        return bookmarkRepository.findByUserIdAndBookmarkSeq(bookmarkUpdateRequestDto.getUserId(), bookmarkUpdateRequestDto.getBookmarkSeq()).orElseThrow(
                () -> new NoSuchElementException()
        );
    }

    @Override
    public BookmarkResponseDto deleteBookmark(BookmarkServiceRequestDto bookmarkServiceRequestDto) {
        int updatedRows = bookmarkRepository.deleteByUserIdAndBookmarkSeq(bookmarkServiceRequestDto.getUserId(),
                                                                          bookmarkServiceRequestDto.getBookmarkSeq());
        validateSqlUpdatedRows(updatedRows);
        return BookmarkResponseDto.builder()
                .build();
    }

    // TODO : 완성 안됨.
    @Override
    public List<Long> updateBookmarkOrder(List<BookmarkReorderServiceRequestDto> serviceRequestDtos) {
        for ( BookmarkReorderServiceRequestDto bookmarkReorderServiceRequestDto : serviceRequestDtos) {
            int updatedRows = bookmarkRepository.updateOrderByBookmarkRequestDto(bookmarkReorderServiceRequestDto);
            validateSqlUpdatedRows(updatedRows);
        }
        List<List<Long>> resultList = new ArrayList<>();
        serviceRequestDtos.forEach(serviceRequestDto ->
                resultList.add(serviceRequestDto.getBookmarkSeqOrder())
        );
        return null;
    }

    private void validateSqlUpdatedRows(int updatedRows) {
        if(updatedRows != 1) throw new IllegalStateException();
    }

}
