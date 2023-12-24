package com.hyun.bookmarkshare.manage.common.service.response;

import com.hyun.bookmarkshare.manage.bookmark.service.response.BookmarkResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FolderWithIncludeBookmarksAndFolders {

    private Long folderSeq;
    private Long folderParentSeq;
    private Long userId;
    private String folderName;
    private String folderCaption;
    private Long folderOrder;
    private String folderScope;
    private LocalDateTime folderRegDate;
    private LocalDateTime folderModDate;
    private List<BookmarkResponseDto> includedBookmarks;
    private List<FolderWithIncludeBookmarksAndFolders> includedFolders;

    @Builder
    public FolderWithIncludeBookmarksAndFolders(Long folderSeq, Long folderParentSeq, Long userId, String folderName, String folderCaption, Long folderOrder, String folderScope, LocalDateTime folderRegDate, LocalDateTime folderModDate, List<BookmarkResponseDto> includedBookmarks, List<FolderWithIncludeBookmarksAndFolders> includedFolders) {
        this.folderSeq = folderSeq;
        this.folderParentSeq = folderParentSeq;
        this.userId = userId;
        this.folderName = folderName;
        this.folderCaption = folderCaption;
        this.folderOrder = folderOrder;
        this.folderScope = folderScope;
        this.folderRegDate = folderRegDate;
        this.folderModDate = folderModDate;
        this.includedBookmarks = includedBookmarks;
        this.includedFolders = includedFolders;
    }

    @Override
    public String toString() {
        return "FolderWithIncludeBookmarksAndFolders{" +
                "\n\r folderSeq=" + folderSeq +
                ",\n\r folderParentSeq=" + folderParentSeq +
                ",\n\r userId=" + userId +
                ",\n\r folderName='" + folderName + '\'' +
                ",\n\r folderCaption='" + folderCaption + '\'' +
                ",\n\r folderOrder=" + folderOrder +
                ",\n\r folderScope='" + folderScope + '\'' +
                ",\n\r folderRegDate=" + folderRegDate +
                ",\n\r folderModDate=" + folderModDate +
                ",\n\r includedBookmarks=" + includedBookmarks +
                ",\n\r includedFolders=" + includedFolders +
                "\n\r}";
    }
}
