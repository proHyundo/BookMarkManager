package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.service.request.FolderCreateServiceRequestDto;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FolderRequestValidatorTest {

    @InjectMocks
    FolderRequestValidator folderRequestValidator;

    @Mock
    FolderRepository folderRepository;

    @Mock
    UserRepository userRepository;

    @DisplayName("폴더 생성 요청 시, 요청 DTO의 userId와 로그인한 사용자의 식별 번호가 다를 경우 예외를 발생시킨다.")
    @Test
    void notSameUserIdBetween() {
        // given
        FolderCreateServiceRequestDto requestDto = FolderCreateServiceRequestDto.builder()
                .userId(1L)
                .build();

        LoginInfoDto loginInfoDto = LoginInfoDto.builder()
                .userId(2L)
                .build();
        // when
        boolean validateResult = folderRequestValidator.notSameUserIdBetween(requestDto.getUserId(), loginInfoDto.getUserId());

        // then
        assertThat(validateResult).isTrue();
    }

}