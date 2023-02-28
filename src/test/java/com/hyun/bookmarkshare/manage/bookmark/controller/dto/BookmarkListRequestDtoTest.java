package com.hyun.bookmarkshare.manage.bookmark.controller.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkListRequestDtoTest {

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

    @DisplayName("BookmarkListRequestDto validation - 성공 케이스")
    @MethodSource("paramsForBookmarkListRequestDto_success")
    @ParameterizedTest
    void bookmarkListRequestDto_success(Long userId, Long folderParentSeq){
        // given
        BookmarkListRequestDto targetDto = BookmarkListRequestDto.builder()
                .userId(userId)
                .folderParentSeq(folderParentSeq)
                .build();
        // when
        Set<ConstraintViolation<BookmarkListRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        Assertions.assertThat(constraintViolations).isEmpty();
    }

    static Stream<Arguments> paramsForBookmarkListRequestDto_success(){
        return Stream.of(
                Arguments.of(1L, 49L)
        );
    }

    @DisplayName("BookmarkListRequestDto validation - 실패 케이스")
    @MethodSource("paramsForBookmarkListRequestDto_fail")
    @ParameterizedTest
    void bookmarkListRequestDto_fail(Long userId, Long folderParentSeq){
        // given
        BookmarkListRequestDto targetDto = BookmarkListRequestDto.builder()
                .userId(userId)
                .folderParentSeq(folderParentSeq)
                .build();
        // when
        Set<ConstraintViolation<BookmarkListRequestDto>> constraintViolations = validator.validate(targetDto);
        // then
        Assertions.assertThat(constraintViolations).isNotEmpty();
    }

    static Stream<Arguments> paramsForBookmarkListRequestDto_fail(){
        return Stream.of(
                Arguments.of(null, 49L), // userId 가 null
                Arguments.of(-1L, 49L), // userId 가 음수
                Arguments.of(1L, null), // folderParentSeq 가 null
                Arguments.of(1L, 0L), // folderParentSeq 가 0
                Arguments.of(1L, -49L) // folderParentSeq 가 음수

        );
    }

}