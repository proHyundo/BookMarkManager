package com.hyun.bookmarkshare.manage.folder.controller;

import com.hyun.bookmarkshare.manage.folder.controller.dto.request.*;
import com.hyun.bookmarkshare.manage.folder.service.FolderService;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderReorderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderSeqResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderWithChildResponse;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FolderRestController {

    private final FolderService folderService;

    // 특정 부모폴더 내부에 신규 폴더 생성
    @PostMapping("/api/v1/manage/folder/new")
    public ApiResponse<FolderResponse> addFolderRequest(@Valid @RequestBody FolderCreateRequestDto requestDto,
                                                        @AuthenticationPrincipal LoginInfoDto loginInfoDto){
        FolderResponse resultFolderResponse = folderService.createFolder(requestDto.toServiceRequestDto(), loginInfoDto.getUserId());
        return ApiResponse.of(HttpStatus.OK, "신규 폴더 생성 완료", resultFolderResponse);
    }

    // 특정 폴더 정보 조회
    @GetMapping("/api/v1/manage/folder/{folderSeq}")
    public ApiResponse<FolderResponse> getFolderRequest(@PathVariable("folderSeq") @NotNull @Positive Long folderSeq,
                                                        @AuthenticationPrincipal LoginInfoDto loginInfoDto){
        return ApiResponse.of(HttpStatus.OK, "폴더 조회 완료", folderService.findFolderInfo(FolderRequestDto.builder()
                .userId(loginInfoDto.getUserId())
                .folderSeq(folderSeq)
                .build()
                .toServiceDto()));
    }

    // 특정 부모폴더 내부에 속한 폴더 List 조회
    @GetMapping("/api/v1/manage/folders/{folderParentSeq}")
    public ApiResponse<List<FolderResponse>> getFolderListRequest(@PathVariable("folderParentSeq") @NotNull @PositiveOrZero Long folderParentSeq,
                                                                  @AuthenticationPrincipal LoginInfoDto loginInfoDto){
        return ApiResponse.of(HttpStatus.OK,
                "폴더 리스트 조회 완료",
                folderService.findFolderList(FolderListRequestDto.builder()
                        .folderParentSeq(folderParentSeq)
                        .userId(loginInfoDto.getUserId())
                        .build()
                        .toServiceDto())
        );
    }

    // 사용자의 모든 폴더 조회
    @GetMapping("/api/v1/manage/folders")
    public ApiResponse<FolderWithChildResponse> getAllFolderListRequest(@AuthenticationPrincipal LoginInfoDto loginInfoDto){
        return ApiResponse.of(HttpStatus.OK,
                "폴더 리스트 조회 완료",
                folderService.findAllFoldersAsHierarchy(loginInfoDto.getUserId())
        );
    }

    // 특정 폴더 정보 수정
    @PatchMapping("/api/v1/manage/folder/update")
    public ApiResponse<FolderResponse> updateFolderRequest(@Valid @RequestBody FolderRequestDto requestDto){
        return ApiResponse.of(HttpStatus.OK, "폴더 수정 완료", folderService.updateFolder(requestDto.toServiceDto()));
    }

    // 특정 폴더 삭제
    @DeleteMapping("/api/v1/manage/folder/delete")
    public ApiResponse<FolderSeqResponse> deleteFolderRequest(@Valid @RequestBody FolderDeleteRequestDto requestDto){
        return ApiResponse.of(HttpStatus.OK, "폴더 삭제 완료", folderService.deleteFolder(requestDto.toServiceDto()));
    }

    // TODO : 폴더 순서 변경 로직 비교 필요.
    //  1. 1024 Index 알고리즘  2. Linked-List 방식  3. 현재 방법(배열) 개선
    // 특정 부모폴더들 내부의 순서 수정
    @PatchMapping("/api/v1/manage/folder/reorder/multi")
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
