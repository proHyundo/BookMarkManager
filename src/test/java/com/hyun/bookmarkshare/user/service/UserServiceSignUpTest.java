package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import com.hyun.bookmarkshare.smtp.entity.EmailEntity;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceSignUpTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PwdEncoder pwdEncoder;
    @Mock
    private JwtTokenizer jwtTokenizer;
    @Mock
    private EmailRepository emailRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, pwdEncoder, jwtTokenizer, emailRepository);
    }

    @DisplayName("회원가입 성공 테스트")
    @Test
    void signUp() throws NoSuchAlgorithmException, ParseException {
        // given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .userId(null)
                .userEmail("test@example.com")
                .userPwd("password1234")
                .userName("test")
                .userPhoneNum("01012341234")
                .userGender("m")
                .emailValidationCode("abc123").build();

        User tempUser = User.builder()
                .userId(1L)
                .userEmail("test@example.com")
                .userName("test")
                .build();
        Date targetDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-04-17 13:12:33");

        // Mocking the behavior
        when(userRepository.countByUserEmail(signUpRequestDto.getUserEmail())).thenReturn(0);
        when(emailRepository.findByEmail(signUpRequestDto.getUserEmail())).thenReturn(
                Optional.of(EmailEntity.builder()
                            .email("test@example.com")
                            .emailCode("abc123")
                            .sendCnt(1)
                            .sendDate(targetDate)
                            .emailValidFlag(1)
                            .build())
                );

//        when(pwdEncoder.encode(signUpRequestDto.getUserPwd())).thenReturn("encoded_password");
        when(userRepository.saveBySignUpRequestDto(signUpRequestDto)).thenReturn(1);
        when(emailRepository.deleteByEmail(signUpRequestDto.getUserEmail())).thenReturn(1);
        when(userRepository.findByUserId(signUpRequestDto.getUserId())).thenReturn(Optional.of(tempUser));

        // when
        User signUpResultUser = userService.signUp(signUpRequestDto);

        // then
        assertThat(signUpResultUser).isNotNull();
        assertEquals(signUpRequestDto.getUserEmail(), signUpResultUser.getUserEmail());

        // Verify that the mocked methods were called with the expected arguments
        verify(userRepository).countByUserEmail(signUpRequestDto.getUserEmail());
        verify(emailRepository).findByEmail(signUpRequestDto.getUserEmail());
//        verify(pwdEncoder).encode(signUpRequestDto.getUserPwd());
        verify(userRepository).saveBySignUpRequestDto(signUpRequestDto);
        verify(emailRepository).deleteByEmail(signUpRequestDto.getUserEmail());
        verify(userRepository).findByUserId(signUpRequestDto.getUserId());
    }

}