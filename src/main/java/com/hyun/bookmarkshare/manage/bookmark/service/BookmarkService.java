package com.hyun.bookmarkshare.manage.bookmark.service;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkReorderServiceRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookmarkService {

    /**
     * userId, folderParentSeq 로 특정 유저의 특정 폴더에 속한 bookmark list 를 가져온다.
     * */
    List<Bookmark> getBookList(BookmarkListRequestDto bookmarkListRequestDto);

    /**
     * userId, bookmarkSeq 로 특정 유저의 특정 bookmark 정보를 가져온다.
     * */
    BookmarkResponseDto getBookmark(BookmarkRequestDto bookmarkRequestDto);

    /**
     * userId, folderParentSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 으로 새로운 bookmark 를 생성한다.
     * */
    BookmarkResponseDto createBookmark(BookmarkAddRequestDto bookmarkAddRequestDto);

    /**
     * userId, bookmarkSeq, bookmarkUrl, bookmarkTitle, bookmarkCaption 으로 특정 bookmark 를 수정한다.
     * */
    BookmarkResponseDto updateBookmark(BookmarkUpdateRequestDto bookmarkUpdateRequestDto);

    /**
     * userId, bookmarkSeq 로 특정 bookmark 를 삭제한다.
     * */
    BookmarkResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto);

    List<Long> updateBookmarkOrder(List<BookmarkReorderServiceRequestDto> bookmarkReorderServiceRequestDto);
}
