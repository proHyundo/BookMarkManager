package com.hyun.bookmarkshare.manage.folder.controller.dto;

import com.hyun.bookmarkshare.manage.folder.service.request.FolderReorderServiceRequestDto;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@NoArgsConstructor
public class FolderReorderRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;

    @NotNull
    @Size(min = 2)
    private List<Long> folderSeqOrder;

    @Builder
    public FolderReorderRequestDto(Long userId, Long folderParentSeq, List<Long> folderSeqOrder) {
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
        this.folderSeqOrder = folderSeqOrder;
    }

    public FolderReorderServiceRequestDto toServiceRequestDto() {
        return FolderReorderServiceRequestDto.builder()
                .userId(userId)
                .folderParentSeq(folderParentSeq)
                .folderSeqOrder(folderSeqOrder)
                .build();
    }

    @Override
    public String toString() {
        return "FolderReorderRequestDto{" +
                "userId=" + userId +
                ", folderParentSeq=" + folderParentSeq +
                ", folderSeqOrder=" + folderSeqOrder +
                '}';
    }
}
