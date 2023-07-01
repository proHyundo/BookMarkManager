package com.hyun.bookmarkshare.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.exceptions.LoginProcessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    UserRepository userRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;

    @DisplayName("UserPermitRestController > signUp > 회원가입 성공")
    @Test
    void signUp() throws Exception {
        // given
        emailRepository.saveByEmailAndValidationCode("08hyundo@gmail.com", "abcd1234");
        emailRepository.updateEmailValidationFlag("08hyundo@gmail.com", "abcd1234");
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                                                            .userId(null)
                                                            .userEmail("08hyundo@gmail.com")
                                                            .userPwd("test1234")
                                                            .userName("hyun")
                                                            .userGender("m")
                                                            .userPhoneNum("01012341234")
                                                            .emailValidationCode("abcd1234")
                                                            .build();
        // when
        // jsonPath 문법 : https://github.com/json-path/JsonPath
        this.mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userEmail").exists())
                .andExpect(jsonPath("$..userEmail").value("08hyundo@gmail.com"));
    }

    @DisplayName("signUp > 회원가입 실패 > ALREADY_USER_EXIST 에러")
    @Test
    void signUpFail_AlreadyUserExist() throws Exception {
        // given
        userRepository.saveBySignUpRequestDto(SignUpRequestDto.builder()
                .userId(null)
                .userEmail("08hyundo@gmail.com")
                .userPwd("test1234")
                .userName("hyun")
                .userGender("m")
                .userPhoneNum("01012341234")
                .emailValidationCode("abcd1234")
                .build());
        
        emailRepository.saveByEmailAndValidationCode("08hyundo@gmail.com", "abcd1234");
        emailRepository.updateEmailValidationFlag("08hyundo@gmail.com", "abcd1234");
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .userId(null)
                .userEmail("08hyundo@gmail.com")
                .userPwd("test1234")
                .userName("hyun")
                .userGender("m").build();

        // when
        this.mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..message").exists())
                .andExpect(jsonPath("$..message").value("ALREADY_USER_EXIST"));


    }

    @DisplayName("loginProcess() > 로그인 성공")
    @Test
    void loginProcess() throws Exception {
        // given
        LoginRequestDto target = new LoginRequestDto("test@test.com", "1111");

        // when & then
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(target)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그인 성공"))
                .andExpect(jsonPath("$.userAccessToken").exists());

    }

    @DisplayName("loginProcess() > 로그인 실패 > NOT_FOUND_USER 에러")
    @Test
    void loginProcessFail_NotFoundUser() throws Exception {
        // given
        LoginRequestDto target = new LoginRequestDto("test@test999.com", "1111");

        // when & then
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(target)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    System.out.println("result getRequest= " + result.getRequest());
                    System.out.println("result getResponse= " + result.getResponse());
                    System.out.println("result getFlashMap= " + result.getFlashMap());
                    System.out.println("result getHandler= " + result.getHandler());
                    System.out.println("result getInterceptors= " + result.getInterceptors());

                    Assertions.assertThat(result.getResolvedException() instanceof LoginProcessException);
                })
                .andExpect(jsonPath("$.statusDescription").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Wrong Id or Pwd"));
    }


}
