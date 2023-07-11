package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderExceptionErrorCode;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderCreateServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderListServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderReorderServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderServiceRequestDto;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderReorderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderSeqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService{

    private final FolderRepository folderRepository;
    private final BookmarkService bookmarkService;
    private final FolderRequestValidator validator;

    @Override
    public List<FolderResponse> findFolderList(FolderListServiceRequestDto requestDto) {
        List<Folder> resultFolders = folderRepository.findAllByUserIdAndParentSeq(
                requestDto.getUserId(),
                requestDto.getFolderParentSeq()
        );
        return resultFolders.stream()
                .map(FolderResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public FolderResponse createFolder(FolderCreateServiceRequestDto serviceRequestDto) {
        Folder targetFolder = serviceRequestDto.toFolder();
        folderRepository.save(targetFolder);
        return FolderResponse.of(folderRepository.findByFolderSeqExcludeDeleted(targetFolder.getFolderSeq())
                .orElseThrow(() -> new NoSuchElementException("Not Found Folder")));
    }

    @Override
    public FolderSeqResponse deleteFolder(FolderServiceRequestDto requestDto) {
        // TODO : [검증] 1) requestDto 에 대한 도메인 정책 검증, 2) 각 쿼리 수행결과에 대한 검증

        // 1. 삭제 요청 받은 폴더의 식별번호로 하위 폴더 식별번호 리스트를 조회.
        List<Long> deleteTarget = folderRepository.findAllFolderSeqWithSameAncestor(requestDto.getFolderSeq(), requestDto.getUserId());
        // 2. 리스트에 요청받은 폴더의 식별번호를 추가.
        deleteTarget.add(requestDto.getFolderSeq());
        // 3. 리스트에 담긴 폴더 식별번호로 폴더 삭제 처리
        deleteTarget.stream().forEach(folderRepository::deleteByFolderSeq);
        // 4. 각 폴더 내부의 북마크 삭제 처리
//        bookmarkService.deleteAllBookmarkInFolderList(deleteTarget);
        return FolderSeqResponse.builder()
                .userId(requestDto.getUserId())
                .folderSeqList(deleteTarget)
                .build();
    }

    @Override
    public FolderResponse updateFolder(FolderServiceRequestDto requestDto) {
        // 1. 요청 받은 폴더 식별번호로 폴더 조회
        Folder updateTarget = folderRepository.findByFolderSeqExcludeDeleted(requestDto.getFolderSeq())
                .orElseThrow(() -> new NoSuchElementException("Not Found Folder"));
        // 2. 조회된 폴더 엔티티를 요청받은 폴더 엔티티로 업데이트
        Folder updatedTarget = updateTarget.updateEntityBy(requestDto.toEntity());
        // 3. 업데이트된 폴더 엔티티를 저장
        folderRepository.update(updatedTarget);
//        validateSqlUpdatedRows(updatedRows);
        return FolderResponse.of(folderRepository.findByFolderSeqExcludeDeleted(requestDto.getFolderSeq())
                                    .orElseThrow(()->new NoSuchElementException("Not Found Folder"))
        );
    }

    @Override
    public List<FolderReorderResponse> updateFolderOrder(List<FolderReorderServiceRequestDto> serviceRequestDtoList) {
        for (FolderReorderServiceRequestDto folderReorderServiceRequestDto : serviceRequestDtoList) {
            folderRepository.updateOrderByFolderServiceRequestDto(folderReorderServiceRequestDto);
        }
        // TODO : data 를 포함하지 않고 성공 여부만 응답하는 것이 좋을지 고민해보자.
        return serviceRequestDtoList.stream()
                .map(dto -> FolderReorderResponse.builder()
                            .userId(dto.getUserId())
                            .folderParentSeq(dto.getFolderParentSeq())
                            .folderSeqOrder(
                                folderRepository.findAllByUserIdAndParentSeq(dto.getUserId(), dto.getFolderParentSeq())
                                    .stream()
                                    .map(Folder::getFolderSeq)
                                    .collect(Collectors.toList())
                            )
                            .build())
                .collect(Collectors.toList());
    }

    /** Validate (INSERT, UPDATE, DELETE) Result Rows */
    private void validateSqlUpdatedRows(int updatedRows) {
        if(updatedRows != 1){
            throw new FolderRequestException(FolderExceptionErrorCode.UPDATE_FOLDER_FAIL, "Update Folder Fail");
        }
    }
}
