package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.exceptions.LoginInputValidateFailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    // TODO) 길이 조건 : 4 이상 20 이하
    //  문자열 형식 조건 : " '@'  '.'  "

    private InputValidator validator = new InputValidator();

    @DisplayName("길이 조건 T & 이메일 문자열 T")
    @Test
    void emailMeterAllTrue() {
        // given
        String emailInput = "abcd@efg.hij";
        // when
        // then
        Assertions.assertThatCode(()->{
            validator.emailMeter(emailInput);
        }).doesNotThrowAnyException();
    }

    @DisplayName("길이 조건 F & 이메일 문자열 T")
    @Test
    void emailMeterLengthFailFormatTrue() {
        // given
        String emailInput = "a@b.c";
        // when
        // then
        Assertions.assertThatThrownBy(()->{
            validator.emailMeter(emailInput);
        }).isInstanceOf(LoginInputValidateFailException.class).hasMessage("Bad Input Length");
    }

    @DisplayName("길이 조건 T & 이메일 문자열 F")
    @Test
    void emailMeterLengthTrueFormatFail(){
        // given
        String emailInput = "a!@b.cd";
        // when
        // then
        Assertions.assertThatThrownBy(()->{
            validator.emailMeter(emailInput);
        }).isInstanceOf(LoginInputValidateFailException.class).hasMessage("Bad Input Email");
    }

    @DisplayName("null 또는 공백 이메일")
    @Test
    void emailMeterNullOrBlankInput(){
        // given
        String emailInput1 = "";
        String emailInput2 = null;
        // when
        // then
        Assertions.assertThatThrownBy(()->{
            validator.emailMeter(emailInput1);
        }).isInstanceOf(LoginInputValidateFailException.class).hasMessage("Bad Input Length");

        Assertions.assertThatThrownBy(()->{
            validator.emailMeter(emailInput2);
        }).isInstanceOf(LoginInputValidateFailException.class).hasMessage("Bad Input Length");
    }
    
    @DisplayName("길이 조건 Y - 통과")
    @Test
    void pwdMeterSuccess(){
        
    }
    
    @DisplayName("길이 조건 F - 4 미만")
    @Test
    void pwdMeterLess4(){

    }

    @DisplayName("길이 조건 F - 20 초과")
    @Test
    void pwdMeterMore20(){

    }
    
    @DisplayName("길이 조건 F - null or blank")
    @Test
    void pwdMeter(){

    }
}