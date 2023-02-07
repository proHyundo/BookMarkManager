package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.common.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderExceptionErrorCode;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService{

    private final FolderRepository repository;
    private final FolderRequestValidator validator;

    @Override
    public List<Folder> findFolderList(FolderListRequestDto requestDto) {

        validator.check(requestDto);

        List<Folder> allByUserIdAndParentSeq = repository.findAllByUserIdAndParentSeq(requestDto.getUserId(), requestDto.getFolderParentSeq());
        System.out.print("FolderServiceImpl.findFolderList >> ");
        System.out.println("allByUserIdAndParentSeq = " + allByUserIdAndParentSeq);
        return allByUserIdAndParentSeq;
    }

    @Override
    public Folder createFolder(FolderRequestDto requestDto) {
        // 검증 ( 폴더명, 폴더레벨, 공유범위) - 미 충족 시 예외
        Long insertedRows = repository.saveNewFolder(requestDto);
        if(insertedRows != 1){
            throw new FolderRequestException(FolderExceptionErrorCode.CREATE_FOLDER_FAIL, "Create Folder Fail");
        }
        return repository.findByFolderSeq(requestDto.getFolderSeq())
                .orElseThrow(() -> new NoSuchElementException("Not Found Folder"));
    }

    @Override
    public Long deleteFolder(FolderRequestDto requestDto) {
        // 검증 ( 폴더식별번호가 혹시 0은 아닌지, 삭제할 대상이 있는지)
        validator.check(requestDto);

        int deletedRows = repository.deleteByFolderSeq(requestDto.getFolderSeq());

        // Duplicate method Refactoring Possible. (insert, delete, update => 1)
        if(deletedRows != 1){
            throw new FolderRequestException(FolderExceptionErrorCode.DELETE_FOLDER_FAIL, "Delete Folder Fail");
        }
        return requestDto.getFolderSeq();
    }

    @Override
    public Folder updateFolder(FolderRequestDto requestDto) {
        validator.check(requestDto);

        int updatedRows = repository.updateByFolderRequestDto(requestDto);
        if(updatedRows != 1){
            throw new FolderRequestException(FolderExceptionErrorCode.UPDATE_FOLDER_FAIL, "Update Folder Fail");
        }
        return repository.findByFolderSeq(requestDto.getFolderSeq())
                .orElseThrow(()->new NoSuchElementException("Not Found Folder"));
    }
}
