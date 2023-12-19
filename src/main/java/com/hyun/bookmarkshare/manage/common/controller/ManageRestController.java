package com.hyun.bookmarkshare.manage.common.controller;

import com.hyun.bookmarkshare.manage.common.service.ManageService;
import com.hyun.bookmarkshare.manage.common.service.response.FolderWithIncludeBookmarksAndFolders;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ManageRestController {

    private final ManageService manageService;

    @GetMapping("/api/v1/manage/folders-bookmarks/tree")
    public ApiResponse<FolderWithIncludeBookmarksAndFolders> getAllFoldersAndBookmarks(@AuthenticationPrincipal LoginInfoDto loginInfoDto){
        return ApiResponse.of(HttpStatus.OK, manageService.getAllFoldersAndBookmarks(loginInfoDto.getUserId()));
    }
}
