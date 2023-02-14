package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.common.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface FolderService {

    /** 사용자의 특정 부모 폴더 내부에 속한 모든 폴더들을 조회한다.
     * @param requestDto FolderListRequestDto
     * @return list of folder
     * */
    List<Folder> findFolderList(FolderListRequestDto requestDto);

    /**@param requestDto
     * @return repository sql result - success : 1
     * @implNote explain
     * */
    Folder createFolder(FolderRequestDto requestDto);

    Long deleteFolder(FolderRequestDto requestDto);

    Folder updateFolder(FolderRequestDto requestDto);

    List<Long> updateFolderOrder(List<FolderReorderRequestDto> requestDtoList);
}
