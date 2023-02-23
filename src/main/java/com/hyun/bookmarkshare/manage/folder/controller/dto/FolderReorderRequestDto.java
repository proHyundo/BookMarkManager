package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
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
    private List<Integer> folderSeqOrder;
}
