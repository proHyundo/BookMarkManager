package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.bookmark.dao.BookmarkRepository;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderCreateRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderListRequestDto;
import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderReorderRequestDto;
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

    private final FolderRepository folderRepository;
    private final FolderRequestValidator validator;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<Folder> findFolderList(FolderListRequestDto requestDto) {
//        validator.check(requestDto);
        return folderRepository.findAllByUserIdAndParentSeq(requestDto.getUserId(), requestDto.getFolderParentSeq());
    }

    @Override
    public Folder createFolder(FolderCreateRequestDto requestDto) {
        // 검증 ( 폴더명, 폴더레벨, 공유범위) - 미 충족 시 예외
        int insertedRows = folderRepository.saveNewFolder(requestDto);
        validateSqlUpdatedRows(insertedRows);
        return folderRepository.findByFolderSeq(requestDto.getFolderSeq())
                .orElseThrow(() -> new NoSuchElementException("Not Found Folder"));
    }

    @Override
    public Long deleteFolder(FolderRequestDto requestDto) {
        // 검증 ( 폴더식별번호가 혹시 0은 아닌지, 삭제할 대상이 있는지)
        validator.check(requestDto);
        // 폴더 삭제 처리
        int deletedRows = folderRepository.deleteByFolderSeq(requestDto.getFolderSeq());
        validateSqlUpdatedRows(deletedRows);
        // 폴더 내부의 북마크 삭제 처리
//        int folderDeletedRows = bookmarkRepository.deleteByFolderSeq(requestDto.getFolderSeq());
        return requestDto.getFolderSeq();
    }

    @Override
    public Folder updateFolder(FolderRequestDto requestDto) {
        validator.check(requestDto);

        int updatedRows = folderRepository.updateByFolderRequestDto(requestDto);
        validateSqlUpdatedRows(updatedRows);
        return folderRepository.findByFolderSeq(requestDto.getFolderSeq())
                .orElseThrow(()->new NoSuchElementException("Not Found Folder"));
    }

    @Override
    public List<Long> updateFolderOrder(List<FolderReorderRequestDto> requestDtoList) {
        validator.check(requestDtoList);
        for ( FolderReorderRequestDto folderReorderRequestDto : requestDtoList) {
            int updatedRows = folderRepository.updateOrderByFolderRequestDto(folderReorderRequestDto);
            validateSqlUpdatedRows(updatedRows);
        }
        List<Long> result = new ArrayList<>();

        requestDtoList.forEach(folderReorderRequestDto -> {
            result.add(folderReorderRequestDto.getFolderParentSeq());
        });
        return result;
    }

    /** Validate (INSERT, UPDATE, DELETE) Result Rows */
    private void validateSqlUpdatedRows(int updatedRows) {
        if(updatedRows != 1){
            throw new FolderRequestException(FolderExceptionErrorCode.UPDATE_FOLDER_FAIL, "Update Folder Fail");
        }
    }
}
