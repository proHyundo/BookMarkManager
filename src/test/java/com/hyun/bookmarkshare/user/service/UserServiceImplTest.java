package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;

    @DisplayName("loginProcess 성공 케이스")
    @Test
    void loginProcess(){
        // given
        LoginRequestDto targetDto = new LoginRequestDto("test@test.com", "1111");

        // when
        User resultUser = userService.loginProcess(targetDto);

        // then
        assertThat(resultUser.getUserEmail()).isEqualTo(targetDto.getEmail());
        assertThat(resultUser.getUserAccessToken()).isNotNull();
    }

}