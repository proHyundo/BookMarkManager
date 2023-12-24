package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.service.request.*;
import com.hyun.bookmarkshare.manage.folder.service.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FolderService {

    FolderResponse findFolderInfo(FolderServiceRequestDto requestDto, Long userId);

    List<FolderResponse> findFolderList(FolderListServiceRequestDto requestDto);

    FolderWithChildResponse findAllFoldersAsHierarchy(Long userId);

    /**@param requestDto
     * @return repository sql result - success : 1
     * @implNote explain
     * */
    FolderResponse createFolder(FolderCreateServiceRequestDto requestDto, Long userId);

    FolderDeleteResponse deleteFolder(FolderDeleteServiceRequestDto requestDto);

    FolderResponse updateFolder(FolderServiceRequestDto requestDto);

    List<FolderReorderResponse> updateFolderOrder(List<FolderReorderServiceRequestDto> requestDtoList);
}
