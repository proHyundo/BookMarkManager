package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FolderReorderRequestDto {

    @NotBlank
    @Positive
    private Long userId;

    @NotBlank
    @PositiveOrZero
    private Long folderParentSeq;

    @NotBlank
    private List<Integer> folderSeqOrder;
}
