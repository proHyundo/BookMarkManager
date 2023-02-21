package com.hyun.bookmarkshare.manage.folder.controller.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
class FolderListRequestDtoTest {

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

    @DisplayName("FolderCreateRequestDto validation - 성공 케이스")
    @MethodSource("paramsForFolderListRequestDto_success")
    @ParameterizedTest
    void folderListRequestDto_success(Long userId, Long folderParentSeq){
        // given
        FolderListRequestDto targetDto = new FolderListRequestDto(userId, folderParentSeq);
        // when
        Set<ConstraintViolation<FolderListRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        assertThat(constraintViolations).isEmpty();
    }

    static Stream<Arguments> paramsForFolderListRequestDto_success(){
        return Stream.of(
                Arguments.of(1L, 0L),
                Arguments.of(1L, 1L),
                Arguments.of(1L, 2L)
        );
    }


    @DisplayName("FolderCreateRequestDto validation - 실패 케이스")
    @MethodSource("paramsForFolderListRequestDto_fail")
    @ParameterizedTest
    void folderListRequestDto_fail(Long userId, Long folderParentSeq){
        // given
        FolderListRequestDto targetDto = new FolderListRequestDto(userId, folderParentSeq);
        // when
        Set<ConstraintViolation<FolderListRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        constraintViolations.forEach(v -> {
            log.info("targetDto >> {} \t message >> {}", targetDto.toString(), v.getMessage());
        });
        assertThat(constraintViolations).isNotEmpty();
    }

    static Stream<Arguments> paramsForFolderListRequestDto_fail(){
        return Stream.of(
                Arguments.of(null, 1L), // userId 가 null 인 경우
                Arguments.of(0L, 1L), // userId 가 0 인 경우
                Arguments.of(-1L, 1L), // userId 가 음수 인 경우
                Arguments.of(1L, null), // folderParentSeq 가 null 인 경우
                Arguments.of(1L, -1L) // folderParentSeq 가 음수 인 경우
        );
    }
}