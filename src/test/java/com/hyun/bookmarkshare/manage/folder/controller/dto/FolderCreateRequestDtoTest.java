package com.hyun.bookmarkshare.manage.folder.controller.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class FolderCreateRequestDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        // System.out.println("validator >> " + validator.getClass().getName());
        // validator >> org.hibernate.validator.internal.engine.ValidatorImpl
    }

    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    // 1.65초 -> 단위테스트 0.2초
    @DisplayName("FolderCreateRequestDto validation - 성공 케이스")
    @ParameterizedTest
    @MethodSource("paramsForFolderCreateRequestDto_success")
    void fuc(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope){
        // given
        FolderCreateRequestDto targetDto = new FolderCreateRequestDto(folderSeq, userId, folderParentSeq, folderName, folderCaption, folderScope);
        // when
        Set<ConstraintViolation<FolderCreateRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        assertThat(constraintViolations).isEmpty();
    }

    static Stream<Arguments> paramsForFolderCreateRequestDto_success(){
        return Stream.of(
                Arguments.of(null, 1L, 0L, "folderName0216", null, "p"),
                Arguments.of(null, 1L, 1L, "folderName0217", null, "p"),
                Arguments.of(null, 1L, 1L, "folderName_0217", null, "p"), // folderName에 대소문자+언더바가 들어간 경우
                Arguments.of(null, 1L, 1L, "folderName 0217", null, "p"), // folderName에 대소문자+공백 경우
                Arguments.of(null, 1L, 1L, "folderName0217", null, "p"), // folderName에 대소문자+숫자 경우
                Arguments.of(null, 1L, 1L, " ", null, "p"), // folderName에 공백인 경우
                Arguments.of(null, 1L, 1L, null, null, "p") // #6 folderName이 Null인 경우
        );
    }

    @DisplayName("FolderCreateRequestDto validation - 실패 케이스")
    @ParameterizedTest
    @MethodSource("paramsForFolderCreateRequestDto_fail")
    void fuc_fail(Long folderSeq, Long userId, Long folderParentSeq, String folderName, String folderCaption, String folderScope){
        // given
        FolderCreateRequestDto targetDto = new FolderCreateRequestDto(folderSeq, userId, folderParentSeq, folderName, folderCaption, folderScope);
        // when
        Set<ConstraintViolation<FolderCreateRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        constraintViolations.stream().forEach(System.out::print);
        assertThat(constraintViolations)
                .extracting(ConstraintViolation::getMessage)
                .isNotEmpty();
    }

    static Stream<Arguments> paramsForFolderCreateRequestDto_fail(){
        return Stream.of(
                Arguments.of(999L, 1L, 0L, "folderName0216", null, "p")
        );
    }

}