package com.hyun.bookmarkshare.manage.folder.dao;

import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderReorderServiceRequestDto;
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
    Optional<Folder> findByFolderSeqExcludeDeleted(Long folderSeq);
    List<Long> findAllFolderSeqWithSameAncestor(long ancestor, long userId);

    // INSERT
    int save(Folder folder);
    int saveFolderAsCustom(Folder folder);

    // UPDATE
    int update(Folder folder);

    int updateOrderByFolderServiceRequestDto(FolderReorderServiceRequestDto folderReorderServiceRequestDto);

    // DELETE

    int deleteByFolderSeq(Long folderSeq);

    Optional<Folder> findRootFolderByUserId(Long userId);


    List<Folder> findAllFolderWithSameAncestor(Long folderSeq, Long userId);
}
