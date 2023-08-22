package com.hyun.bookmarkshare.manage.folder.controller.dto.request;

import com.hyun.bookmarkshare.manage.folder.service.request.FolderDeleteServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class FolderDeleteRequestDto {

    @NotNull
    @Positive
    private Long folderSeq;

    @NotNull
    @Positive
    private Long userId;

    @Builder
    public FolderDeleteRequestDto(Long folderSeq, Long userId) {
        this.folderSeq = folderSeq;
        this.userId = userId;
    }

    public FolderDeleteServiceRequestDto toServiceDto(){
        return FolderDeleteServiceRequestDto.builder()
                .folderSeq(folderSeq)
                .userId(userId)
                .build();
    }
}
