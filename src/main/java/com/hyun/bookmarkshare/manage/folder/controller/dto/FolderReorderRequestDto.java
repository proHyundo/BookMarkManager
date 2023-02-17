package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FolderReorderRequestDto {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @PositiveOrZero
    private Long folderParentSeq;

    @NotNull
    @Size(min = 2)
    private List<Integer> folderSeqOrder;
}
