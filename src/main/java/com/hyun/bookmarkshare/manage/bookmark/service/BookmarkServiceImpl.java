package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.exceptions.domain.manage.BookmarkException;
import com.hyun.bookmarkshare.exceptions.errorcode.BookmarkErrorCode;
import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkUpdateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkSeqResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService{

    private final BookmarkRepository bookmarkRepository;
    private final UrlParser urlParser;

    @Override
    public BookmarkResponseDto getBookmark(BookmarkServiceRequestDto requestDto) {
        return bookmarkRepository.findByUserIdAndBookmarkSeq(requestDto.getUserId(), requestDto.getBookmarkSeq())
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<BookmarkResponseDto> getBookList(BookmarkServiceRequestDto serviceRequestDto) {
        List<Bookmark> allByUserIdAndFolderParentSeq = bookmarkRepository.findAllByUserIdAndFolderSeqExcludeDeleted(
                serviceRequestDto.getUserId(),
                serviceRequestDto.getFolderSeq());

        if(allByUserIdAndFolderParentSeq.isEmpty()) throw new BookmarkException(BookmarkErrorCode.BOOKMARK_NOT_FOUND, "Bookmark List is Empty");
        return allByUserIdAndFolderParentSeq.stream().map(bookmark -> BookmarkResponseDto.of(bookmark)).collect(Collectors.toList());
    }

    @Override
    public BookmarkResponseDto createBookmark(BookmarkCreateServiceRequestDto serviceRequestDto, Long loginInfoUserId) {
        if(serviceRequestDto.getUserId() != loginInfoUserId)
            throw new BookmarkException(BookmarkErrorCode.BOOKMARK_INVALID_ACCESS, "Bookmark Create Request's userId is not equal to loginInfo's userId.");

        // bookmark url disunite & set units to bookmarkAddRequestDto
        BookmarkCreateServiceRequestDto afterSetUrlUnitsBookmarkServiceRequestDto = urlParser.assignUrlFields(serviceRequestDto);
        Bookmark targetBookmark = afterSetUrlUnitsBookmarkServiceRequestDto.toBookmarkEntity();
        bookmarkRepository.save(targetBookmark);
        return BookmarkResponseDto.of(bookmarkRepository.findByBookmarkSeq(targetBookmark.getBookmarkSeq()).get());
    }

    @Override
    public BookmarkResponseDto updateBookmark(BookmarkUpdateServiceRequestDto requestDto, Long loginInfoUserId) {
        if(requestDto.getUserId() != loginInfoUserId)
            throw new BookmarkException(BookmarkErrorCode.BOOKMARK_INVALID_ACCESS, "Bookmark Update Request's userId is not equal to loginInfo's userId.");

        Bookmark targetBookmark = bookmarkRepository.findByBookmarkSeq(requestDto.getBookmarkSeq())
                                                    .orElseThrow(() -> new NoSuchElementException());
        // 기존 북마크와 다른 url 이 요청되었을 경우, url 을 파싱 작업을 수행한다.
        BookmarkUpdateServiceRequestDto assignedRequestDto = doUrlAssignWhenUrlIsDifferentBetween(requestDto, targetBookmark);
        // dto to entity
        Bookmark freshBookmark = targetBookmark.updateEntityBy(assignedRequestDto.toEntity());
        // 덮어쓴 북마크 객체를 저장한다.
        int updatedRows = bookmarkRepository.update(freshBookmark);
        validateSqlUpdatedRows(updatedRows);
        // 저장한 북마크 객체를 응답하기 위해 반환객체로 변환한다.
        return BookmarkResponseDto.of(bookmarkRepository.findByBookmarkSeq(freshBookmark.getBookmarkSeq())
                .orElseThrow(() -> new NoSuchElementException()));
    }

    private BookmarkUpdateServiceRequestDto doUrlAssignWhenUrlIsDifferentBetween(BookmarkUpdateServiceRequestDto requestDto, Bookmark targetBookmark) {
        BookmarkUpdateServiceRequestDto assignedRequestDto = null;
        if(!requestDto.getBookmarkUrl().equals(targetBookmark.getBookmarkUrl())){
            assignedRequestDto = urlParser.assignUrlFields(requestDto, requestDto.getBookmarkUrl());
        }else {
            assignedRequestDto = requestDto;
        }
        return assignedRequestDto;
    }

    @Override
    public BookmarkSeqResponse deleteBookmark(BookmarkServiceRequestDto bookmarkServiceRequestDto, Long loginInfoUserId) {
        if(bookmarkServiceRequestDto.getUserId() != loginInfoUserId)
            throw new BookmarkException(BookmarkErrorCode.BOOKMARK_INVALID_ACCESS, "Bookmark Delete Request's userId is not equal to loginInfo's userId.");

        int updatedRows = bookmarkRepository.deleteByUserIdAndBookmarkSeq(bookmarkServiceRequestDto.getUserId(),
                                                                          bookmarkServiceRequestDto.getBookmarkSeq());
        validateSqlUpdatedRows(updatedRows);
        return BookmarkSeqResponse.builder()
                .userId(bookmarkServiceRequestDto.getUserId())
                .bookmarkSeq(bookmarkServiceRequestDto.getBookmarkSeq())
                .build();
    }

    @Override
    public Boolean updateBookmarkOrder(List<BookmarkReorderServiceRequestDto> serviceRequestDtoList) {
        for (BookmarkReorderServiceRequestDto serviceRequestDto : serviceRequestDtoList) {
            bookmarkRepository.updateOrderByBookmarkRequestDto(serviceRequestDto);
        }
        return true;
    }

    @Override
    public Integer deleteAllBookmarksInFolderSeqAndUserId(List<Long> targetFolderSeqList, Long userId) {
        Integer deletedBookmarksCnt = 0;
        for(Long targetFolderSeq : targetFolderSeqList){
            deletedBookmarksCnt = bookmarkRepository.deleteAllByUserIdAndFolderSeq(userId, targetFolderSeq);
        }
        return deletedBookmarksCnt;
    }

    private void validateSqlUpdatedRows(int updatedRows) {
        if(updatedRows != 1) throw new IllegalStateException("sql update error");
    }

    private void validateSqlAffectedRows(int affectedRows, SqlAffectedType affectedType){
        switch (affectedType){
            case INSERT_ONE:
            case UPDATE_ONE:
            case DELETE_ONE:
                if(affectedRows != 1) throw new IllegalStateException("sql "+affectedType.name()+" error");
                break;
            case INSERT_MANY:
            case UPDATE_MANY:
            case DELETE_MANY:
                if(affectedRows < 1) throw new IllegalStateException("sql "+affectedType.name()+" error");
                break;
        }
    }

    enum SqlAffectedType{
        INSERT_ONE, UPDATE_ONE, DELETE_ONE,
        INSERT_MANY, UPDATE_MANY, DELETE_MANY,
    }

}
