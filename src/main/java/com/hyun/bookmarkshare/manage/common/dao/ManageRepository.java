package com.hyun.bookmarkshare.manage.common.dao;

import com.hyun.bookmarkshare.manage.common.service.response.FolderWithIncludeBookmarksAndFolders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManageRepository {

    List<FolderWithIncludeBookmarksAndFolders> getAllFoldersAndBookmarks(Long userSeq);
}
