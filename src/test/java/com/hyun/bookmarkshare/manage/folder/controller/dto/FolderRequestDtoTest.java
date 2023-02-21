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

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class FolderRequestDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDown(){
        validatorFactory.close();
    }

    @DisplayName("FolderRequestDto @Valid - arguments 성공 케이스")
    @MethodSource("paramsForFolderRequestDto_success")
    @ParameterizedTest
    void folderRequestDto_success(Long folderSeq, Long userId, String folderName, String folderCaption, String folderScope, Long folderParentSeq){
        // given
        FolderRequestDto targetDto = new FolderRequestDto(folderSeq, userId, folderName, folderCaption, folderScope, folderParentSeq);
        // when
        Set<ConstraintViolation<FolderRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        constraintViolations.forEach(v -> {
            log.info("targetDto >> {} \t message >> {}", targetDto.toString(), v.getMessage());
        });
        assertThat(constraintViolations).isEmpty();
    }

    static Stream<Arguments> paramsForFolderRequestDto_success(){
        return Stream.of(
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", "p", 1L)
        );
    }

    @DisplayName("FolderRequestDto @Valid - arguments 실패 케이스")
    @MethodSource("paramsForFolderRequestDto_fail")
    @ParameterizedTest
    void folderRequestDto_fail(Long folderSeq, Long userId, String folderName, String folderCaption, String folderScope, Long folderParentSeq){
        // given
        FolderRequestDto targetDto = new FolderRequestDto(folderSeq, userId, folderName, folderCaption, folderScope, folderParentSeq);
        // when
        Set<ConstraintViolation<FolderRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        constraintViolations.forEach(v -> {
            log.info("targetDto >> {} \t message >> {}", targetDto.toString(), v.getMessage());
        });
        // targetDto 옆에 숫자 넣고 싶음. => stream 을 더 학습해야 해결 가능할 듯.
        assertThat(constraintViolations).isNotEmpty();
    }

    static Stream<Arguments> paramsForFolderRequestDto_fail(){
        return Stream.of(
                Arguments.of(null, 1L, "dtoTestFolderName", "caption-0217", "p", 1L), // folderSeq 가 null
                Arguments.of(0L, 1L, "dtoTestFolderName", "caption-0217", "p", 1L), // folderSeq 가 0
                Arguments.of(-2L, 1L, "dtoTestFolderName", "caption-0217", "p", 1L), // folderSeq 가 음수
                Arguments.of(1L, null, "dtoTestFolderName", "caption-0217", "p", 1L), // userId 가 null
                Arguments.of(1L, 0L, "dtoTestFolderName", "caption-0217", "p", 1L), // userId 가 0
                Arguments.of(1L, 1L, null, "caption-0217", "p", 1L), // folderName 이 null
                Arguments.of(1L, 1L, "dtoTestFolderName@!", "caption-0217", "p", 1L), // folderName 에 특수문자
                Arguments.of(16L, 1L, "dtoTestFolderName", "012345678901234567890123456789012345678901234567891", "p", 1L), // folderCaption 길이 50 초과
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", null, 1L), // folderScope 가 null
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", "", 1L), // folderScope 가 빈문자열
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", "a", 1L), // folderScope 가 pou 외 문자
                Arguments.of(16L, 1L, "dtoTestFolderName", "caption-0217", "p", null) // folderParentSeq 가 null
        );
    }

}