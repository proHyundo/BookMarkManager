package com.hyun.bookmarkshare.manage.common.service;


import com.hyun.bookmarkshare.manage.common.dao.ManageRepository;
import com.hyun.bookmarkshare.manage.common.service.response.FolderWithIncludeBookmarksAndFolders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ManageServiceImpl implements ManageService{

    private final ManageRepository manageRepository;

    @Override
    public FolderWithIncludeBookmarksAndFolders getAllFoldersAndBookmarks(Long userId) {
        List<FolderWithIncludeBookmarksAndFolders> allFoldersAndBookmarks = manageRepository.getAllFoldersAndBookmarks(userId);
        return convertToHierarchicalFolderAndBookmarkTree(allFoldersAndBookmarks);
    }

    private FolderWithIncludeBookmarksAndFolders convertToHierarchicalFolderAndBookmarkTree(
                                                    List<FolderWithIncludeBookmarksAndFolders> allFoldersAndBookmarks) {
        // 각 폴더의 폴더식별번호(folderSeq)를 key로 하는 Map을 생성.
        Map<Long, FolderWithIncludeBookmarksAndFolders> folderMap = new HashMap<>();
        for (FolderWithIncludeBookmarksAndFolders folder : allFoldersAndBookmarks) {
            // 순회 대상 폴더에 포함된 북마크 식별번호가 null인 경우, 빈 리스트로 초기화.
            if (folder.getIncludedBookmarks().get(0).getBookmarkSeq() == null){
                folder.setIncludedBookmarks(new ArrayList<>());
            }
            folderMap.put(folder.getFolderSeq(), folder);
        }

        // 각 폴더의 부모 폴더를 Map에서 찾아서 트리 구조를 생성.
        // 조회 결과에서 각 폴더를 순회하면서
        for (FolderWithIncludeBookmarksAndFolders folder : allFoldersAndBookmarks) {
            FolderWithIncludeBookmarksAndFolders parentFolder = folderMap.get(folder.getFolderParentSeq());
            // 부모 폴더가 존재하면
            if (parentFolder != null) {
                // 부모 폴더의 자식 폴더 리스트에 자신(순회 대상 폴더)을 추가.
                if (parentFolder.getIncludedFolders() == null) {
                    parentFolder.setIncludedFolders(new ArrayList<>());
                }
                parentFolder.getIncludedFolders().add(folder);
            }
        }

        // 부모 폴더가 없는 폴더, 즉 트리의 루트를 찾는다.
        FolderWithIncludeBookmarksAndFolders rootFolderIncludeBookmarksAndFolders = allFoldersAndBookmarks.stream()
                .filter(folder -> folder.getFolderParentSeq() == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("루트 폴더가 존재하지 않습니다."));
        return rootFolderIncludeBookmarksAndFolders;
    }
}
