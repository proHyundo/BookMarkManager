package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.service.request.*;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderReorderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderSeqResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderWithChildResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FolderService {

    FolderResponse findFolderInfo(FolderServiceRequestDto requestDto);

    List<FolderResponse> findFolderList(FolderListServiceRequestDto requestDto);

    FolderWithChildResponse findAllFoldersAsHierarchy(Long userId);

    /**@param requestDto
     * @return repository sql result - success : 1
     * @implNote explain
     * */
    FolderResponse createFolder(FolderCreateServiceRequestDto requestDto);

    FolderSeqResponse deleteFolder(FolderDeleteServiceRequestDto requestDto);

    FolderResponse updateFolder(FolderServiceRequestDto requestDto);

    List<FolderReorderResponse> updateFolderOrder(List<FolderReorderServiceRequestDto> requestDtoList);
}
