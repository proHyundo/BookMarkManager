package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.common.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface FolderService {

    /** @implSpec
     * @param requestDto
     * @return
     * */
    List<Folder> findFolderList(FolderListRequestDto requestDto);

    /**@param requestDto
     * @return repository sql result - success : 1
     * @implNote explain
     * */
    Folder createFolder(FolderRequestDto requestDto);

    Long deleteFolder(FolderRequestDto requestDto);

    Folder updateFolder(FolderRequestDto requestDto);
}
