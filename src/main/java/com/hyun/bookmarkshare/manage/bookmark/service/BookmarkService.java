package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkUpdateServiceRequestDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkSeqResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookmarkService {

    /**
     * userId, bookmarkSeq 로 특정 유저의 특정 bookmark 정보를 가져온다.
     * */
    BookmarkResponseDto getBookmark(BookmarkServiceRequestDto bookmarkServiceRequestDto);

    /**
     * userId, folderParentSeq 로 특정 유저의 특정 폴더에 속한 bookmark list 를 가져온다.
     * */
    List<BookmarkResponseDto> getBookList(BookmarkServiceRequestDto serviceRequestDto);

    /**
     * userId, folderParentSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 으로 새로운 bookmark 를 생성한다.
     * */
    BookmarkResponseDto createBookmark(BookmarkCreateServiceRequestDto bookmarkCreateServiceRequestDto, Long loginInfoUserId);

    /**
     * userId, bookmarkSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 으로 특정 bookmark 를 수정한다.
     * */
    BookmarkResponseDto updateBookmark(BookmarkUpdateServiceRequestDto bookmarkUpdateServiceRequestDto, Long loginInfoUserId);

    /**
     * userId, bookmarkSeq 로 특정 bookmark 를 삭제한다.
     * */
    BookmarkSeqResponse deleteBookmark(BookmarkServiceRequestDto bookmarkServiceRequestDto, Long loginInfoUserId);

    Integer deleteAllBookmarksInFolderSeqAndUserId(List<Long> folderSeqList, Long userId);

    Boolean updateBookmarkOrder(List<BookmarkReorderServiceRequestDto> bookmarkReorderServiceRequestDto);
}
