package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.dao.LoginRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LoginInputValidateFailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceImplTest {

    private InputValidator validator = new InputValidator();

    @Description("login input validation Success")
    @Test
    void loginInputNormal(){
        // given
        LoginRequestDto loginRequestDto1 = new LoginRequestDto("abcd@", "1111");
        // when
        validator.emailMeter(loginRequestDto1.getEmail());
        // then
        // ?

    }

    @Test
    void loginEmailInputLengthFail(){
        // given
        LoginRequestDto loginRequestDto1 = new LoginRequestDto("a@", "1111");
        // when & then
        Assertions.assertThatThrownBy(()->{
            validator.emailMeter(loginRequestDto1.getEmail());
        }).isInstanceOf(LoginInputValidateFailException.class);
    }

    @Test
    void loginEmailInputUnKnownChar(){
        // given
        LoginRequestDto loginRequestDto1 = new LoginRequestDto("test!!@", "1111");
        // when & then
        Assertions.assertThatThrownBy(()->{
           validator.emailMeter(loginRequestDto1.getEmail());
        }).isInstanceOf(LoginInputValidateFailException.class).hasMessage("Bad Input Email");
    }
}