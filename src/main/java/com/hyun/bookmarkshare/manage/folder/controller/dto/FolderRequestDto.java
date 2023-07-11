package com.hyun.bookmarkshare.manage.folder.controller.dto;

import com.hyun.bookmarkshare.manage.folder.service.request.FolderServiceRequestDto;
import lombok.*;

import javax.validation.constraints.*;

@ToString
@Getter
@NoArgsConstructor
public class FolderRequestDto {

    @NotNull
    @Positive
    private Long folderSeq;

    @NotNull
    @Positive
    private Long userId;

    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9_ ]{1,50}")
    @Size(min = 1, max = 50)
    private String folderName;

    @Size(max = 50)
    private String folderCaption;

    @NotBlank
    @Pattern(regexp = "[pou]")
    private String folderScope;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;

    @Builder
    public FolderRequestDto(Long folderSeq, Long userId, String folderName, String folderCaption, String folderScope, Long folderParentSeq) {
        this.folderSeq = folderSeq;
        this.userId = userId;
        this.folderName = folderName;
        this.folderCaption = folderCaption;
        this.folderScope = folderScope;
        this.folderParentSeq = folderParentSeq;
    }

    public FolderServiceRequestDto toServiceDto(){
        return FolderServiceRequestDto.builder()
                .folderSeq(folderSeq)
                .userId(userId)
                .folderName(folderName)
                .folderCaption(folderCaption)
                .folderScope(folderScope)
                .folderParentSeq(folderParentSeq)
                .build();
    }
}
