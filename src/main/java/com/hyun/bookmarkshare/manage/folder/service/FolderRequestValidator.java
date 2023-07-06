package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderExceptionErrorCode;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FolderRequestValidator {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

//    1. 사용자 존재 유무 user_state = 'n'
//    2. 사용자 권한 확인 user

    /** #Overload */
    public void check(FolderListRequestDto requestDto){
        // requestDto.getUserId 으로 조회한 사용자 존재 유무, 사용자 권한, 사용자 식별번호와
        // requestDto.getFolderParentSeq 으로 조회한 사용자 식별번호가 일치하는지?
        existUser(requestDto.getUserId());
        availableSelectFolderSeq(requestDto.getFolderParentSeq());
//        haveAuthority(requestDto.getFolderParentSeq(), requestDto.getUserId());
    }

    /** #Overload */
    public void check(FolderRequestDto requestDto){
        haveAuthority(requestDto.getFolderSeq(), requestDto.getUserId());
        availableUpdateFolderSeq(requestDto.getFolderSeq());
    }

    /** #Overload */
    public void check(List<FolderReorderRequestDto> requestDtoList) {

    }

    // 해당 유저가 접근 권한이 있는지
    private void haveAuthority(Long folderSeq, Long userId) {
    }


    // 제어 가능한 폴더인지 (제어하려는 폴더식별번호가 루트(0)폴더가 아니며 & 존재하는/접근가능한 폴더여야 한다)
    private void availableUpdateFolderSeq(Long folderSeq){
        folderRepository.findByFolderSeq(folderSeq)
                .filter(folder -> !folder.getFolderSeq().equals(0))
                .orElseThrow(() -> {
                    throw new FolderRequestException(FolderExceptionErrorCode.NOT_FOUND_FOLDER, "존재 하지 않거나 제어 불가한 폴더 입니다");
                });
    }

    // 조회 가능한 폴더인지 (조회하려는 폴더식별번호가 존재하는/접근가능한 폴더여야 한다)
    private void availableSelectFolderSeq(Long folderParentSeq){
        folderRepository.findByFolderSeq(folderParentSeq)
                .orElseThrow(()->{
                    throw new NoSuchElementException("");
                });
    }

    // 존재하는 유저인지
    private void existUser(Long userId) {
        userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new NoSuchElementException("존재 하지 않는 사용자 입니다.");
        });
    }


}
