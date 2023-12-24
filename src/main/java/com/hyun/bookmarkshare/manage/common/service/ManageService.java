package com.hyun.bookmarkshare.manage.common.service;

import com.hyun.bookmarkshare.manage.common.service.response.FolderWithIncludeBookmarksAndFolders;

public interface ManageService {
    FolderWithIncludeBookmarksAndFolders getAllFoldersAndBookmarks(Long userId);
}
