package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.bookmark.service.BookmarkService;
import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderExceptionErrorCode;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import com.hyun.bookmarkshare.manage.folder.service.request.*;
import com.hyun.bookmarkshare.manage.folder.service.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService{

    private final FolderRepository folderRepository;
    private final BookmarkService bookmarkService;
    private final FolderRequestValidator validator;

    @Override
    public FolderResponse findFolderInfo(FolderServiceRequestDto requestDto) {
        Folder resultFolder = folderRepository.findByFolderSeqExcludeDeleted(requestDto.getFolderSeq())
                .orElseThrow(() -> new NoSuchElementException("Not Found Folder"));
        return FolderResponse.of(resultFolder);
    }

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

    // TODO : 메서드명 교체 > findAllFoldersAsHierarchy
    // 참고 : https://www.java-success.com/00-%E2%99%A6-creating-tree-list-flattening-back-list-java/
    @Override
    public FolderWithChildResponse findAllFoldersAsHierarchy(Long userId) {
        Folder rootFolder = folderRepository.findRootFolderByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Not Found Root Folder"));
        FolderWithChildResponse root = FolderWithChildResponse.of(rootFolder);

        List<FolderWithChildResponse> allFolders = folderRepository.findAllFolderWithSameAncestor(rootFolder.getFolderSeq(), userId).stream()
                .map(FolderWithChildResponse::of)
                .collect(Collectors.toList());
        allFolders.add(0, root);

        Map<Long, FolderWithChildResponse> nodeMap = new HashMap<>();

        //Save all nodes to a map
        for (FolderWithChildResponse current : allFolders) {
            current.setChildFolderList(new ArrayList<>());
            nodeMap.put(current.getFolderSeq(), current);
        }

        for (FolderWithChildResponse aData : allFolders) {
            if (aData.getFolderParentSeq() != 0L) {
                FolderWithChildResponse parentNode = nodeMap.get(aData.getFolderParentSeq());
                if(parentNode != null) {
                    parentNode.getChildFolderList().add(nodeMap.get(aData.getFolderSeq()));
                    nodeMap.put(aData.getFolderParentSeq(), parentNode);
                    // 왜 현재 노드를 다시 map 에 넣어줘야 하는가?
//                    nodeMap.put(aData.getFolderSeq(), aData);
                }
            }
        }
//        FolderWithChildResponse rootNode = null;
//        for (FolderWithChildResponse node : nodeMap.values()) {
//            if(node.getFolderParentSeq() == 0L) {
//                rootNode = node;
//                break;
//            }
//        }
        // 개선할 수 있지 않을까?
         FolderWithChildResponse rootNode2nd = nodeMap.get(root.getFolderSeq());

        return rootNode2nd;
    }


    @Override
    public FolderResponse createFolder(FolderCreateServiceRequestDto serviceRequestDto, Long userId) {
        if(validator.notSameUserIdBetween(serviceRequestDto.getUserId(), userId)) {
            throw new FolderRequestException(FolderExceptionErrorCode.CREATE_FOLDER_FAIL, "요청한 사용자의 식별번호와 로그인한 사용자의 식별번호가 일치하지 않습니다.");
        }
        Folder targetFolder = serviceRequestDto.toFolder();
        folderRepository.save(targetFolder);
        return FolderResponse.of(folderRepository.findByFolderSeqExcludeDeleted(targetFolder.getFolderSeq())
                .orElseThrow(() -> new NoSuchElementException("Not Found Folder")));
    }

    @Override
    public FolderDeleteResponse deleteFolder(FolderDeleteServiceRequestDto requestDto) {
        // 1. 삭제 요청 받은 폴더의 식별번호로 하위 폴더 식별번호 리스트를 조회.
        List<Long> deleteTargetList = folderRepository.findAllFolderSeqWithSameAncestor(requestDto.getFolderSeq(), requestDto.getUserId());
        // 2. 리스트에 요청받은 폴더의 식별번호를 추가.
        deleteTargetList.add(requestDto.getFolderSeq());
        // 3. 리스트에 담긴 폴더 식별번호로 폴더 삭제 처리
        deleteTargetList.stream().forEach(folderRepository::deleteByFolderSeq);
        // 4. 각 폴더 내부의 북마크 삭제 처리
        Integer deletedBookmarksCnt = bookmarkService.deleteAllBookmarksInFolderSeqAndUserId(deleteTargetList, requestDto.getUserId());
        return FolderDeleteResponse.builder()
                .userId(requestDto.getUserId())
                .folderSeqList(deleteTargetList)
                .deleteBookmarksCount(deletedBookmarksCnt)
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
