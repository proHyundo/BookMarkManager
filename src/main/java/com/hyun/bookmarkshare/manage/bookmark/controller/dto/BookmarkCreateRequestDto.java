package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import com.hyun.bookmarkshare.manage.bookmark.service.request.BookmarkCreateServiceRequestDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class BookmarkCreateRequestDto {

    @Null
    private Long bookmarkSeq;

    @Positive
    private Long userId;

    @Positive
    private Long folderSeq;

    private String bookmarkTitle;
    private String bookmarkCaption;

    @NotEmpty
    private String bookmarkUrl;

    @Builder
    public BookmarkCreateRequestDto(Long bookmarkSeq, Long userId, Long folderSeq, String bookmarkTitle, String bookmarkCaption, String bookmarkUrl) {
        this.bookmarkSeq = bookmarkSeq;
        this.userId = userId;
        this.folderSeq = folderSeq;
        this.bookmarkTitle = bookmarkTitle;
        this.bookmarkCaption = bookmarkCaption;
        this.bookmarkUrl = bookmarkUrl;
    }

    public BookmarkCreateServiceRequestDto toServiceDto(){
        return BookmarkCreateServiceRequestDto.builder()
                .userId(this.userId)
                .folderSeq(this.folderSeq)
                .bookmarkTitle(this.bookmarkTitle)
                .bookmarkCaption(this.bookmarkCaption)
                .bookmarkUrl(this.bookmarkUrl)
                .build();
    }
}
