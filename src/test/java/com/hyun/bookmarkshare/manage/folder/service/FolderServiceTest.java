package com.hyun.bookmarkshare.manage.folder.service;

import com.hyun.bookmarkshare.manage.folder.dao.FolderRepository;
import com.hyun.bookmarkshare.manage.folder.entity.Folder;
import com.hyun.bookmarkshare.manage.folder.exceptions.FolderRequestException;
import com.hyun.bookmarkshare.manage.folder.service.request.*;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderReorderResponse;
import com.hyun.bookmarkshare.manage.folder.service.response.FolderResponse;
import com.hyun.bookmarkshare.security.jwt.util.LoginInfoDto;
import com.hyun.bookmarkshare.utils.WithCustomAuthUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
public class FolderServiceTest {

    @Autowired
    FolderService folderService;
    @Autowired
    FolderRepository folderRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach(){
        jdbcTemplate.update("ALTER TABLE TFOLDER AUTO_INCREMENT = 2");
    }
    
    @DisplayName("폴더를 생성한다. 폴더 식별 번호는 가장 최근 폴더의 식별 번호에서 1 증가한 값이다.")
    @Test
    void createNewFolder() {
        // given
        Long userId = 1L;
        FolderCreateServiceRequestDto requestDto = FolderCreateServiceRequestDto.builder()
                .folderSeq(null)
                .userId(1L)
                .folderParentSeq(1L)
                .folderName("folderName")
                .folderCaption(null)
                .folderScope("p")
                .build();

        // when
        FolderResponse folderResponse = folderService.createFolder(requestDto, userId);

        // then
        assertThat(folderResponse)
                .extracting("folderSeq", "userId", "folderCaption", "folderOrder")
                .containsExactlyInAnyOrder(3L, 1L, "", 2L);
    }

    @DisplayName("폴더 생성 시, 요청DTO와 로그인한 사용자의 식별 번호가 다를 경우 예외를 발생시킨다.")
    @Test
    void createNewFolderThrowExceptionWhenNotSameUserId(){
        // given
        FolderCreateServiceRequestDto requestDto = FolderCreateServiceRequestDto.builder()
                .userId(1L)
                .build();
        LoginInfoDto loginInfoDto = LoginInfoDto.builder()
                .userId(2L)
                .build();
        // when // then
        assertThatThrownBy(() -> folderService.createFolder(requestDto, loginInfoDto.getUserId()))
                .isInstanceOf(FolderRequestException.class)
                .hasMessageContaining("요청한 사용자의 식별번호와 로그인한 사용자의 식별번호가 일치하지 않습니다.");
    }
    
    @DisplayName("특정 사용자의 특정 폴더에 존재하는 모든 폴더 리스트를 조회한다.")
    @Test
    void findFolderList() {
        // given
        FolderListServiceRequestDto requestDto = FolderListServiceRequestDto.builder()
                .userId(1L)
                .folderParentSeq(1L)
                .build();
        // when // then
        assertThat(folderRepository.findAllByUserIdAndParentSeq(requestDto.getUserId(), requestDto.getFolderParentSeq()))
                .hasSize(1)
                .extracting("folderSeq", "userId", "folderParentSeq")
                .containsExactlyInAnyOrder(
                        tuple(2L, 1L, 1L)
                );
    }

    @WithCustomAuthUser(email = "test@test.com", userId = 1, role = "ROLE_USER")
    @DisplayName("특정 사용자의 모든 폴더를 계층구조로 응답객체에 담는다.")
    @Test
    void findAllFolderList() {
        // given
        Folder folder3 = createFolder(1L, 2L, "folder3");
        Folder folder4 = createFolder(1L, 2L, "folder4");
        Folder folder5 = createFolder(1L, 2L, "folder5");

        // when
        folderService.findAllFoldersAsHierarchy(1L);
        // then
    }

    @DisplayName("폴더 삭제 시, 해당 폴더의 deleteFlag 는 'y'로 변경되며, 해당 폴더의 하위폴더들도 모두 deleteFlag 가 'y'로 변경된다.")
    @Test
    void deleteFolder() {
        // given
        Folder folder3 = createFolder(1L, 2L, "folder3");
        Folder folder4 = createFolder(1L, 2L, "folder4");
        Folder folder5 = createFolder(1L, 2L, "folder5");
        folderRepository.save(folder3);
        folderRepository.save(folder4);
        folderRepository.save(folder5);

        FolderDeleteServiceRequestDto requestDto = FolderDeleteServiceRequestDto.builder()
                .folderSeq(2L)
                .userId(1L)
                .build();

        // when
        folderService.deleteFolder(requestDto);

        // then
        assertThat(folderRepository.findByFolderSeq(requestDto.getFolderSeq()).get().getFolderDelFlag()).isEqualTo("y");
        assertThat(folderRepository.findByFolderSeq(folder3.getFolderSeq()).get().getFolderDelFlag()).isEqualTo("y");
        assertThat(folderRepository.findByFolderSeq(folder4.getFolderSeq()).get().getFolderDelFlag()).isEqualTo("y");
        assertThat(folderRepository.findByFolderSeq(folder5.getFolderSeq()).get().getFolderDelFlag()).isEqualTo("y");
    }

    @DisplayName("폴더 수정 시, 해당 폴더의 정보가 수정된다. 수정 가능한 속성은 폴더 이름, 폴더 설명, 폴더 공개 범위이다.")
    @Test
    void updateFolder() {
        // given
        Folder folder3 = createFolder(1L, 2L, "folder3", "folder3Caption", "p");
        folderRepository.save(folder3);
        FolderServiceRequestDto requestDto = FolderServiceRequestDto.builder()
                .folderSeq(folder3.getFolderSeq())
                .userId(folder3.getUserId())
                .folderParentSeq(folder3.getFolderParentSeq())
                .folderName("folder3Update")
                .folderCaption("folder3CaptionUpdate")
                .folderScope("o")
                .build();
        // when
        folderService.updateFolder(requestDto);
        // then
        assertThat(folderRepository.findByFolderSeq(requestDto.getFolderSeq()).get())
                .extracting("folderSeq", "folderName", "folderCaption", "folderScope")
                .containsExactlyInAnyOrder(requestDto.getFolderSeq(), "folder3Update", "folder3CaptionUpdate", "o");
    }

    @DisplayName("폴더 정렬 순서를 변경한다.")
    @Test
    void updateFolderOrder() {
        // given
        Folder folder3 = createFolder(1L, 2L, "folder3");
        Folder folder4 = createFolder(1L, 2L, "folder4");
        Folder folder5 = createFolder(1L, 2L, "folder5");
        Folder folder6 = createFolder(1L, 1L, "folder6");
        folderRepository.save(folder3);
        folderRepository.save(folder4);
        folderRepository.save(folder5);
        folderRepository.save(folder6);
        FolderReorderServiceRequestDto requestDto1 = FolderReorderServiceRequestDto.builder()
                .userId(1L)
                .folderParentSeq(2L)
                .folderSeqOrder(List.of(5L, 3L, 4L))
                .build();
        FolderReorderServiceRequestDto requestDto2 = FolderReorderServiceRequestDto.builder()
                .userId(1L)
                .folderParentSeq(1L)
                .folderSeqOrder(List.of(6L, 2L))
                .build();
        // when
        List<FolderReorderResponse> resultList = folderService.updateFolderOrder(List.of(requestDto1, requestDto2));
        // then
        assertThat(resultList).extracting("folderParentSeq", "folderSeqOrder")
                .containsExactlyInAnyOrder(
                        tuple(2L, List.of(5L, 3L, 4L)),
                        tuple(1L, List.of(6L, 2L))
                );
    }

    private Folder createFolder(Long userId, Long parentSeq, String folderName, String folderCaption, String folderScope){
        return Folder.builder()
                .folderSeq(null)
                .userId(userId)
                .folderParentSeq(parentSeq)
                .folderOrder(null)
                .folderRootFlag("n")
                .folderName(folderName)
                .folderCaption(folderCaption)
                .folderScope(folderScope)
                .folderRegDate(null)
                .folderModDate(null)
                .folderDelFlag("n")
                .build();
    }

    private Folder createFolder(Long userId, Long parentSeq, String folderName) {
        return createFolder(userId, parentSeq, folderName, "folderCaption", "p");
    }



}
