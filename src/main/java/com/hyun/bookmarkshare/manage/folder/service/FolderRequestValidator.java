package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderExceptionErrorCode;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FolderRequestValidator {

    private final FolderRepository repository;

    /** #Overload */
    public void check(FolderListRequestDto requestDto){
//        existUser();
        availableFolderSeq();
//        haveAuthority(requestDto.getFolderParentSeq(), requestDto.getUserId());
    }

    /** #Overload */
    public void check(FolderRequestDto requestDto){
        haveAuthority(requestDto.getFolderSeq(), requestDto.getUserId());
        availableFolderSeq();
    }

    /** #Overload */
    public void check(List<FolderReorderRequestDto> requestDtoList) {

    }

    // 해당 유저가 접근 권한이 있는지
    private void haveAuthority(Long folderSeq, Long userId) {
    }

    // 제어 가능한 폴더인지 (루트폴더가 아니어야 한다, 존재하는 (부모)폴더여야 한다)
    private void availableFolderSeq(){

    }

    // 존재하는 유저인지
    private void existUser() {
    }


}
