package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FolderRepository {

    // SELECT
    List<Folder> findAllByUserIdAndParentSeq(String userId, String folderParentSeq);
    List<Folder> findAllByUserId(String userId);
    Optional<Folder> findByFolderSeq(Long folderSeq);


    // INSERT
    Long saveNewFolder(FolderRequestDto requestDto);

    int deleteByFolderSeq(Long folderSeq);
}
