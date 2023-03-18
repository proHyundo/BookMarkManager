package com.hyun.bookmarkshare.user.controller.dto;

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


class LoginRequestDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    @DisplayName("정상 요청 값")
    @ParameterizedTest
    @MethodSource("loginRequestValuesSuccess")
    void loginRequest_Success(String email, String pwd){
        // given
        LoginRequestDto targetDto = new LoginRequestDto(email, pwd);
        // when
        Set<ConstraintViolation<LoginRequestDto>> constraintViolationSet = validator.validate(targetDto);
        // then
        Assertions.assertThat(constraintViolationSet).isEmpty();
    }

    static Stream<Arguments> loginRequestValuesSuccess(){
        return Stream.of(
                Arguments.of("test@test.com", "1111")
        );
    }

}