package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import org.springframework.stereotype.Component;

@Component
public class FolderRequestValidator {

    // Overload
    public void check(FolderListRequestDto requestDto){

//        existUser();
        existParentFolder();
        haveAuthority();
    }

    // Overload
    public void check(FolderRequestDto requestDto){
        haveAuthority();
        availableFolderSeq();
    }

    // 해당 유저가 접근 권한이 있는지
    private void haveAuthority() {
    }

    // 존재하는 부모폴더인지
    private void existParentFolder() {
    }

    // 제어 가능한 폴더인지 (루트폴더가 아니어야 한다, 존재하는 폴더여야 한다)
    private void availableFolderSeq(){

    }


    // 존재하는 유저인지
    private void existUser() {
    }



}
