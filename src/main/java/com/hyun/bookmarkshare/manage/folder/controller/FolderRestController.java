package com.hyun.bookmarkshare.manage.folder.controller;

import com.hyun.bookmarkshare.manage.common.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.*;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FolderRestController {

    private final FolderService folderService;

    // 특정 부모폴더 내부에 속한 폴더 List 조회
    @GetMapping("/manage/folder/list")
    public ResponseEntity<FolderListResponseEntity> getFolderListRequest(@Valid @RequestBody FolderListRequestDto requestDto){
        return FolderListResponseEntity.toResponseEntity(folderService.findFolderList(requestDto));
    }

    // 특정 부모폴더 내부에 신규 폴더 생성
    @PostMapping("/manage/folder/addFolder")
    public ResponseEntity<FolderResponseEntity> addFolderRequest(@RequestBody FolderRequestDto requestDto){
        Folder resultFolder = folderService.createFolder(requestDto);
        return FolderResponseEntity.toResponseEntity(resultFolder);
    }

    // 특정 폴더 삭제
    @DeleteMapping("/manage/folder/delete")
    public ResponseEntity<FolderSeqResponseEntity> deleteFolderRequest(@RequestBody FolderRequestDto requestDto){
        return FolderSeqResponseEntity.toResponseEntity(folderService.deleteFolder(requestDto));
    }

    // 특정 폴더 정보 수정
    @PatchMapping("/manage/folder/update")
    public ResponseEntity<FolderResponseEntity> updateFolderRequest(@RequestBody FolderRequestDto requestDto){
        return FolderResponseEntity.toResponseEntity(folderService.updateFolder(requestDto));
    }

    // 특정 부모폴더들 내부의 순서 수정
    @PostMapping("/manage/folder/reorder")
    public ResponseEntity<FolderReorderResponseEntity> reorderFolderRequest(@RequestBody List<FolderReorderRequestDto> requestDtoList){
        /*
        * JSON DATA REQUEST FORMAT EXAMPLE
        *
            [
                {"folderParentSeq": 1, "userId": 1, "folderSeqOrder":[3,14,15,16]},
                {"folderParentSeq": 2, "userId": 1, "folderSeqOrder":[5,14,42,43]}
            ]
        *
        * */
        return FolderReorderResponseEntity.toResponseEntity(folderService.updateFolderOrder(requestDtoList));
    }

}
