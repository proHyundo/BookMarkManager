package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkUpdateServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class BookmarkUpdateRequestDto {

    @NotNull
    private Long bookmarkSeq;
    @NotNull
    private Long userId;
    @NotNull
    private Long folderSeq;
    @NotEmpty
    private String bookmarkTitle;
    @NotEmpty
    private String bookmarkCaption;
    @NotEmpty
    private String bookmarkUrl;

    @Builder
    public BookmarkUpdateRequestDto(Long bookmarkSeq, Long userId, Long folderSeq, String bookmarkTitle, String bookmarkCaption, String bookmarkUrl) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkTitle = bookmarkTitle;
        this.bookmarkCaption = bookmarkCaption;
        this.bookmarkUrl = bookmarkUrl;
    }

    public BookmarkUpdateServiceRequestDto toServiceRequestDto() {
        return BookmarkUpdateServiceRequestDto.builder()
                .bookmarkSeq(this.bookmarkSeq)
                .userId(this.userId)
                .folderSeq(this.folderSeq)
                .bookmarkTitle(this.bookmarkTitle)
                .bookmarkCaption(this.bookmarkCaption)
                .bookmarkUrl(this.bookmarkUrl)
                .build();
    }
}
