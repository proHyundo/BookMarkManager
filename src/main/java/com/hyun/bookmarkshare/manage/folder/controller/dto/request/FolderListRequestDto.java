package com.hyun.bookmarkshare.manage.folder.controller.dto.request;

import com.hyun.bookmarkshare.manage.folder.service.request.FolderListServiceRequestDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor
public class FolderListRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;

    @Builder
    public FolderListRequestDto(Long userId, Long folderParentSeq) {
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
    }

    public FolderListServiceRequestDto toServiceDto(){
        return FolderListServiceRequestDto.builder()
                .userId(userId)
                .folderParentSeq(folderParentSeq)
                .build();
    }

}
