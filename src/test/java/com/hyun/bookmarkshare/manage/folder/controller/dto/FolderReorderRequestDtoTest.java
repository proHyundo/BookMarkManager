package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class FolderReorderRequestDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        log.info("ValidatorFactory 에서 validator 생성 -> {}", validator);
    }

    @AfterAll
    static void tearDown() {
        validatorFactory.close();
        log.info("ValidatorFactory 자원 반납");
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderReorderRequestDto_success")
    @DisplayName("FolderReorderRequestDto @Valid - arguments 성공 케이스")
    void folderReorderRequestDto_success(Long userId, Long folderParentSeq, List<Long> folderSeqOrder) throws Exception {
        // given
        FolderReorderRequestDto targetDto = new FolderReorderRequestDto(userId, folderParentSeq, folderSeqOrder);
        // when
        Set<ConstraintViolation<FolderReorderRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        constraintViolations.forEach(v -> {
            log.info("targetDto >> {} \t message >> {}", targetDto.toString(), v.getMessage());
        });
        assertThat(constraintViolations).isEmpty();

    }

    static Stream<Arguments> paramsForFolderReorderRequestDto_success(){
        return Stream.of(
                Arguments.of(1L, 0L, Arrays.asList(3,14,15,16)),
                Arguments.of(1L, 1L, Arrays.asList(1,2,4,6,7,8,9,10,11,12,13))
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForFolderReorderRequestDto_fail")
    @DisplayName("FolderReorderRequestDto @Valid - arguments 실패 케이스")
    void folderReorderRequestDto_fail(Long userId, Long folderParentSeq, List<Long> folderSeqOrder) throws Exception {
        // given
        FolderReorderRequestDto targetDto = new FolderReorderRequestDto(userId, folderParentSeq, folderSeqOrder);
        // when
        Set<ConstraintViolation<FolderReorderRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        constraintViolations.forEach(v -> {
            log.info("targetDto >> {} \t message >> {}", targetDto.toString(), v.getMessage());
        });
        assertThat(constraintViolations).isNotEmpty();

    }

    static Stream<Arguments> paramsForFolderReorderRequestDto_fail(){
        List<Long> seqOrders1 = Arrays.asList(3L,14L,15L,16L);
        List<Long> seqOrders2 = Arrays.asList(1L);

        return Stream.of(
                Arguments.of(null, 0L, seqOrders1), // userId null
                Arguments.of(-1L, 1L, seqOrders1), // userId 음수
                Arguments.of(1L, null, seqOrders1), // folderParentSeq null
                Arguments.of(1L, -1L, seqOrders1), // folderParentSeq 음수
                Arguments.of(1L, 1L, seqOrders2) // folderSeqOrder 길이 2 미만
        );
    }

}