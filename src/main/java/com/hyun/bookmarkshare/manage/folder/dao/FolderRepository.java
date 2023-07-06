package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderCreateRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FolderRepository {

    // SELECT
    /**
     * select all folders contained within specific folder_parent_seq
     * @param userId USER_SEQ
     * @param folderParentSeq FOLDER_PARENT
     * @return list of folder
     * */
    List<Folder> findAllByUserIdAndParentSeq(Long userId, Long folderParentSeq);
    List<Folder> findAllByUserId(Long userId);
    Optional<Folder> findByFolderSeq(Long folderSeq);


    // INSERT
    int saveNewFolder(FolderCreateRequestDto requestDto);

    // UPDATE
    int deleteByFolderSeq(Long folderSeq);
    int updateByFolderRequestDto(FolderRequestDto requestDto);
    int updateOrderByFolderRequestDto(FolderReorderRequestDto folderReorderRequestDto);

    // ONLY FOR TEST
    int save(Folder folder);
}
