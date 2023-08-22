package com.hyun.bookmarkshare.manage.folder.controller.dto.request;

import com.hyun.bookmarkshare.manage.folder.service.request.FolderCreateServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class FolderCreateRequestDto {

    @Null
    private Long folderSeq;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;

    @Pattern(regexp = "[A-Za-z0-9_ ]{1,20}")
    @Size(min = 1, max = 20)
    private String folderName;

    private String folderCaption;

    @Pattern(regexp = "[pou]")
    private String folderScope;

    @Builder
    public FolderCreateRequestDto(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope) {
        this.folderSeq = folderSeq;
        this.userId = userId;
        this.folderParentSeq = folderParentSeq;
        this.folderName = folderName;
        this.folderCaption = folderCaption;
        this.folderScope = folderScope;
    }

    public FolderCreateServiceRequestDto toServiceRequestDto() {
        return FolderCreateServiceRequestDto.builder()
                .folderSeq(this.folderSeq)
                .userId(this.userId)
                .folderParentSeq(this.folderParentSeq)
                .folderName(this.folderName)
                .folderCaption(this.folderCaption)
                .folderScope(this.folderScope)
                .build();

    }
}
