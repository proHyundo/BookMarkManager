package com.hyun.bookmarkshare.manage.bookmark.dao;

import com.hyun.bookmarkshare.manage.bookmark.controller.dto.*;
import com.hyun.bookmarkshare.manage.bookmark.entity.Bookmark;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookmarkRepository {

    List<Bookmark> findAllByUserIdAndFolderParentSeq(BookmarkListRequestDto bookmarkListRequestDto);

    Optional<BookmarkResponseDto> findByUserIdAndBookmarkSeq(Long userId, Long bookmarkSeq);

    Optional<Bookmark> findByBookmarkSeq(Long bookmarkSeq);

    int saveBookmark(BookmarkAddRequestDto bookmarkAddRequestDto);

    int updateByBookmarkUpdateRequestDto(BookmarkUpdateRequestDto bookmarkUpdateRequestDto);

//    @Update("UPDATE TBOOKMARK SET BOOK_CAPTION = #{bookmarkCaption}, BOOK_SCHEME = #{bookmarkScheme}," +
//            " BOOK_HOST = #{bookmarkHost}, BOOK_DOMAIN = #{bookmarkDomain}, BOOK_PATH = #{bookmarkPath}," +
//            " BOOK_URL = #{bookmarkUrl} WHERE BOOK_SEQ = #{bookmarkSeq} RETURNING BOOK_TITLE, BOOK_CAPTION, BOOK_URL;")
//    @Results({
//            @Result(property = "bookmarkSeq", column = "BOOK_SEQ"),
//            @Result(property = "bookmarkTitle", column = "BOOK_TITLE"),
//            @Result(property = "bookmarkCaption", column = "BOOK_CAPTION"),
//            @Result(property = "bookmarkUrl", column = "BOOK_URL")
//    })
    BookmarkResponseDto updateByBookmarkUpdateRequestDtoAndReturnLockUpdate(BookmarkUpdateRequestDto bookmarkUpdateRequestDto);

    int deleteByUserIdAndBookmarkSeq(BookmarkRequestDto bookmarkRequestDto);

}
