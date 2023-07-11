package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.service.request.FolderCreateServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderListServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderReorderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderSeqResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FolderService {

    /** 사용자의 특정 부모 폴더 내부에 속한 모든 폴더들을 조회한다.
     * @param requestDto FolderListRequestDto
     * @return list of folder
     * */
    List<FolderResponse> findFolderList(FolderListServiceRequestDto requestDto);

    /**@param requestDto
     * @return repository sql result - success : 1
     * @implNote explain
     * */
    FolderResponse createFolder(FolderCreateServiceRequestDto requestDto);

    FolderSeqResponse deleteFolder(FolderServiceRequestDto requestDto);

    FolderResponse updateFolder(FolderServiceRequestDto requestDto);

    List<FolderReorderResponse> updateFolderOrder(List<FolderReorderServiceRequestDto> requestDtoList);
}
