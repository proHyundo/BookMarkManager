package com.hyun.bookmarkshare.manage.folder.controller;

import com.hyun.bookmarkshare.manage.common.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.*;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FolderRestController {

    private final FolderService folderService;

    // 특정 부모폴더 내부에 속한 폴더 List 조회
    @GetMapping("/manage/folder/list")
    public ResponseEntity<FolderListResponseEntity> getFolderListRequest(@RequestBody FolderListRequestDto requestDto){
        return FolderListResponseEntity.toResponseEntity(folderService.findFolderList(requestDto));
    }

    // 특정 부모폴더 내부에 신규 폴더 생성
    @PostMapping("/manage/folder/addFolder")
    public ResponseEntity<FolderResponseEntity> addFolderRequest(@RequestBody FolderRequestDto requestDto){
        Folder resultFolder = folderService.createFolder(requestDto);
        return FolderResponseEntity.toResponseEntity(resultFolder);
    }

    // 특정 폴더 삭제
    @PostMapping("/manage/folder/delete")
    public ResponseEntity<FolderSeqResponseEntity> deleteFolderRequest(FolderRequestDto requestDto){
        Long deletedFolderSeq = folderService.deleteFolder(requestDto);
        return FolderSeqResponseEntity.toResponseEntity(deletedFolderSeq);
    }

    // 특정 폴더명 수정
//    @PostMapping("/manage/folder/update")
//    public ResponseEntity<FolderResponseEntity> updateFolderRequest(FolderRequestDto requestDto){
//        return FolderResponseEntity.toResponseEntity(folderService.updateFolder);
//    }

    // 특정 부모폴더 내부의 순서 수정
//    @PostMapping("/manage/folder/reorder")
//    public ResponseEntity<FolderResponseEntity> reorderFolderRequest(FolderReOrderRequestDto requestDto){
//        return null;
//    }

}
