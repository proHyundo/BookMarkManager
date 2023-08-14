package com.hyun.bookmarkshare.manage.folder.controller;

import com.hyun.bookmarkshare.manage.folder.controller.dto.*;
import com.hyun.bookmarkshare.manage.folder.service.FolderService;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderReorderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderSeqResponse;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FolderRestController {

    private final FolderService folderService;

    // 특정 부모폴더 내부에 신규 폴더 생성
    @PostMapping("/api/v1/manage/folder/new")
    public ApiResponse<FolderResponse> addFolderRequest(@Valid @RequestBody FolderCreateRequestDto requestDto){
        FolderResponse resultFolderResponse = folderService.createFolder(requestDto.toServiceRequestDto());
        return ApiResponse.of(HttpStatus.OK, "신규 폴더 생성 완료", resultFolderResponse);
    }

    // 특정 부모폴더 내부에 속한 폴더 List 조회
    @GetMapping("/api/v1/manage/folders")
    public ApiResponse<List<FolderResponse>> getFolderListRequest(@Valid @RequestBody FolderListRequestDto requestDto){
        return ApiResponse.of(HttpStatus.OK,
                "폴더 리스트 조회 완료",
                folderService.findFolderList(requestDto.toServiceDto())
        );
    }


    // 특정 폴더 삭제
    @DeleteMapping("/manage/folder/delete")
    public ApiResponse<FolderSeqResponse> deleteFolderRequest(@Valid @RequestBody FolderRequestDto requestDto){
        return ApiResponse.of(HttpStatus.OK, "폴더 삭제 완료", folderService.deleteFolder(requestDto.toServiceDto()));
    }

    // 특정 폴더 정보 수정
    @PatchMapping("/manage/folder/update")
    public ApiResponse<FolderResponse> updateFolderRequest(@Valid @RequestBody FolderRequestDto requestDto){
        return ApiResponse.of(HttpStatus.OK, "폴더 수정 완료", folderService.updateFolder(requestDto.toServiceDto()));
    }

    // 특정 부모폴더들 내부의 순서 수정
    @PostMapping("/manage/folder/reorder")
    public ApiResponse<List<FolderReorderResponse>> reorderFolderRequest(@Valid @RequestBody List<FolderReorderRequestDto> requestDtoList){
        /*
        * JSON DATA REQUEST FORMAT EXAMPLE
        *
            [
                {"folderParentSeq": 1, "userId": 1, "folderSeqOrder":[3,14,15,16]},
                {"folderParentSeq": 2, "userId": 1, "folderSeqOrder":[5,14,42,43]}
            ]
        *
        * */
        List<FolderReorderServiceRequestDto> serviceRequestDtoList = requestDtoList.stream()
                .map(dto -> dto.toServiceRequestDto())
                .collect(Collectors.toList());
        List<FolderReorderResponse> reorderResponseList = folderService.updateFolderOrder(serviceRequestDtoList);
        return ApiResponse.of(HttpStatus.OK, "폴더 순서 수정 완료", reorderResponseList);
    }

}
