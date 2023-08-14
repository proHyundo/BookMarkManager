package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import com.hyun.bookmarkshare.smtp.entity.EmailEntity;
import com.hyun.bookmarkshare.user.controller.dto.UserSignUpRequestDto;
import com.hyun.bookmarkshare.user.dao.TokenRepository;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// service layer unit test. (markdown.md 참고)
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
    @Mock
    private TokenRepository tokenRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, pwdEncoder, jwtTokenizer, emailRepository, tokenRepository);
    }
/*
    @DisplayName("회원가입 성공 테스트")
    @Test
    void signUp() throws NoSuchAlgorithmException, ParseException {
        // given
        UserSignUpRequestDto userSignUpRequestDto = UserSignUpRequestDto.builder()
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
        when(userRepository.countByUserEmail(userSignUpRequestDto.getUserEmail())).thenReturn(0);
        when(emailRepository.findByEmail(userSignUpRequestDto.getUserEmail())).thenReturn(
                Optional.of(EmailEntity.builder()
                            .email("test@example.com")
                            .emailCode("abc123")
                            .sendCnt(1)
                            .sendDate(targetDate)
                            .emailValidFlag(1)
                            .build())
                );

//        BDDMockito.given(userRepository.saveBySignUpRequestDto(userSignUpRequestDto)).willReturn(1);
//        BDDMockito.given(emailRepository.deleteByEmail(userSignUpRequestDto.getUserEmail())).willReturn(1);
//        BDDMockito.given(userRepository.findByUserId(userSignUpRequestDto.getUserId())).willReturn(Optional.of(tempUser));

        when(userRepository.saveBySignUpRequestDto(userSignUpRequestDto)).thenReturn(1);
        when(emailRepository.deleteByEmail(userSignUpRequestDto.getUserEmail())).thenReturn(1);
        when(userRepository.findByUserId(userSignUpRequestDto.getUserId())).thenReturn(Optional.of(tempUser));
        when(userRepository.saveNew(tempUser)).thenReturn(1);

        // when
        UserResponse signUpResultUser = userService.signUp(userSignUpRequestDto.toServiceDto());

        // then
        assertThat(signUpResultUser).isNotNull();
        assertEquals(userSignUpRequestDto.getUserEmail(), signUpResultUser.getUserEmail());

        // Verify that the mocked methods were called with the expected arguments
        verify(userRepository).countByUserEmail(userSignUpRequestDto.getUserEmail());
        verify(emailRepository).findByEmail(userSignUpRequestDto.getUserEmail());
        verify(userRepository).saveBySignUpRequestDto(userSignUpRequestDto);
        verify(emailRepository).deleteByEmail(userSignUpRequestDto.getUserEmail());
        verify(userRepository).findByUserId(userSignUpRequestDto.getUserId());
    }
*/
}