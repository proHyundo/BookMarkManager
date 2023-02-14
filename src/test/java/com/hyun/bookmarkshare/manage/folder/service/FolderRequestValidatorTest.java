package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.controller.dto.FolderRequestDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FolderRequestValidatorTest {

    // 1. 사용자의 상태가 활성화된 상태인가?
        // Y : 통과
        // N : 예외 발생
    // 2. 요청받은 폴더식별번호가 삭제되지 않은 상태인가?
        // Y : 통과
        // N : 예외 발생
    // 3. 조회가 아닌 제어 요청일 경우 폴더식별번호가 제어불가한 root(0) 인가?
        // Y : 예외 발생
        // N : 통과
    // 4. 요청받은 폴더식별번호가 해당 사용자의 소유인가?
        // Y : 통과
        // N : 4-1. 소유자가 아니라면, 접근권한이 있는가?
            // Y : 통과
            // N : 예외 발생

    private final UserRepository userRepository;

    FolderRequestValidatorTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("폴더 리스트 조회 요청")
    void checkFolderListRequestDto() {
        // given
        //

        // when

        // then
    }

    @Test
    @DisplayName("단일 폴더 제어 요청_사용자 활성 상태_정상")
    void checkFolderRequestDto_userState() {
        //
        final Long folderSeq = 3L;
        final Long userId = 1L;
        final String folderName = "folderName";
        final String folderCaption = "folderCaption";
        final String folderScope = "p";
        final Long folderParentSeq = 0L;
        // given
        FolderRequestDto folderRequestDto = new FolderRequestDto(folderSeq, userId, folderName, folderCaption, folderScope, folderParentSeq);

        // when
        userState(folderRequestDto.getFolderSeq(), folderRequestDto.getUserId());

        // then - 통과해야 하는 테스트 이기에 예외가 발생되지 않아야 한다.
        Assertions.assertThatExceptionOfType(null);

    }

    @Test
    @DisplayName("폴더 순서 재정렬 요청")
    void checkListFolderReorderRequestDto() {
    }

    private void userState(Long folderSeq, Long userId) {
    }

}