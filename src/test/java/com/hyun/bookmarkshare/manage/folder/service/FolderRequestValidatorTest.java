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

    /* 무엇을 검증해야 하는가.
    * 1. Data types: Ensure that all fields in the DTO have the correct data type.
    * For example, if a field is supposed to be an integer or a date, make sure you validate that
    * it contains an appropriate value.
    *
    * 2. Required fields: Check that all mandatory fields have a value and that these values are not empty or null.
    * This includes fields like IDs, names, or other critical information necessary for the service layer to process
    * the request properly.
    *
    * 3. Value range and format: Verify that each field's value is within an acceptable range or follows a specific format.
    * For instance, dates should be in a valid format (e.g., YYYY-MM-DD), email addresses should follow the correct pattern,
    * and numerical values should be within an acceptable range.
    *
    * 4. Data consistency: Ensure that the relationships between different fields in the DTO are consistent.
    * For example, if there is a start date and end date, the start date should be earlier than or equal to the end date.
    *
    * 5. Security and sanitization: Be cautious about security vulnerabilities and validate that your fields do not
    * have any potential threats (like SQL injections or cross-site scripting attacks). Sanitize user input by eliminating
    * or escaping any hazardous characters.
    *
    * 6. Business rules: In some cases, validation might involve checking business-specific rules.
    * Ensure that the DTO adheres to any project-specific requirements that fall outside the previous validation categories.
    * Having a comprehensive validation process in place will ensure that your service layer operates on safe and
    * accurate data from the client, reducing the risk of errors, vulnerabilities, and unexpected behavior in your
    * application. It's important to keep in mind that validation should be done not only in the service layer but also
    * in the client-side and other layers when necessary. Having multiple layers of validation enhances the security and
    * integrity of your application.
    * */

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