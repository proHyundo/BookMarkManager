package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.exceptions.errorcode.UserErrorCode;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import com.hyun.bookmarkshare.user.dao.TokenRepository;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.exceptions.domain.user.LoginProcessException;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.user.service.response.UserSignoutResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TokenRepository tokenRepository;

    @BeforeEach
    void initEach(){
        jdbcTemplate.update("ALTER TABLE TUSERACCOUNT AUTO_INCREMENT = 2");
    }

    @DisplayName("회원가입 시 역할은 ROLE_USER, 상태는 n, 등급은 g 이다.")
    @Test
    void signUp() {
        // given
        emailRepository.saveByEmailAndValidationCode("test3@test.com", "33333");
        emailRepository.updateEmailValidationFlag("test3@test.com", "33333");
        UserSignUpServiceRequestDto targetDto = UserSignUpServiceRequestDto.builder()
                .userId(null)
                .userEmail("test3@test.com")
                .userPwd("3333")
                .userName("test3")
                .userPhoneNum(null)
                .userGender("m")
                .emailValidationCode("33333")
                .build();
        // when
        userService.signUp(targetDto);
        // then
        assertThat(userRepository.findByUserEmail("test3@test.com").get())
                .extracting("userId", "userRole", "userState", "userGrade")
                .containsExactlyInAnyOrder(3L, "ROLE_USER", "n", "g");
    }

    @DisplayName("회원가입하려는 이메일이 중복되는지 확인한다. 중복되지 않는 경우 false를 반환한다.")
    @Test
    void checkDuplicateEmail() {
        // given
        String targetEmail = "test3@test.com";
        // when
        boolean result = userService.checkDuplicateEmail(targetEmail);
        // then
        assertThat(result).isFalse();
    }

    @DisplayName("회원가입하려는 이메일이 중복되는지 확인한다. 중복되는 경우 예외를 던진다.")
    @Test
    void ifCheckDuplicateEmailIsTrueMakeException() {
        // given
        String targetEmail = "test@test.com";
        // when // then
        assertThatThrownBy(() -> userService.checkDuplicateEmail(targetEmail))
                .isInstanceOf(LoginProcessException.class)
                .hasMessageContaining(UserErrorCode.USER_SIGNIN_ALREADY_EXIST.getMessage());
    }

    @DisplayName("로그인 처리 과정에서, 요청받은 비밀번호는 암호화 한다.")
    @Test
    void duringLoginProcessPwdMustEncrypt(){
        // given
        User user3 = User.builder()
                .userId(3L)
                .userEmail("test3@test.com")
                .userPwd("296d85ce7f16267bc97d9184b130e03392b25afdcf0a21d010f5ed2236d95f6d")
                .userName("")
                .build();
        userRepository.save(user3);
        LoginServiceRequestDto targetDto = LoginServiceRequestDto.builder()
                .email("test3@test.com")
                .pwd("3333")
                .build();
        // when
        User resultUser = userRepository.findByUserEmail(targetDto.getEmail()).get();

        // then
        assertThat(resultUser.getUserPwd()).isNotEqualTo("3333");
    }

    @DisplayName("로그인 처리 성공 시, 응답 객체에 JWT 토큰이 저장된다.")
    @Test
    void loginProcessReturnUserResponseIncludeJwtTokens() {
        // given
        User user3 = User.builder()
                .userId(3L)
                .userEmail("test3@test.com")
                .userPwd("296d85ce7f16267bc97d9184b130e03392b25afdcf0a21d010f5ed2236d95f6d")
                .userName("")
                .build();
        userRepository.save(user3);
        LoginServiceRequestDto targetDto = LoginServiceRequestDto.builder()
                .email("test3@test.com")
                .pwd("3333")
                .build();
        // when
        UserLoginResponse userLoginResponse = userService.loginProcess(targetDto);

        // then
        assertThat(userLoginResponse.getUserAccessToken()).isNotNull();
        assertThat(userLoginResponse.getUserRefreshToken()).isNotNull();
        log.info("userLoginResponse.getUserAccessToken() = {}", userLoginResponse.getUserAccessToken());
        log.info("userLoginResponse.getUserRefreshToken() = {}", userLoginResponse.getUserRefreshToken());
    }

    @DisplayName("로그인 처리 성공 시, 해당 사용자의 Jwt Refresh Token 은 DB에 저장된다.")
    @Test
    void afterLoginProcessRefreshTokenStoreInDatabase() {
        // given
        User user3 = User.builder()
                .userId(3L)
                .userEmail("test3@test.com")
                .userPwd("296d85ce7f16267bc97d9184b130e03392b25afdcf0a21d010f5ed2236d95f6d")
                .userName("")
                .build();
        userRepository.save(user3);
        LoginServiceRequestDto targetDto = LoginServiceRequestDto.builder()
                .email("test3@test.com")
                .pwd("3333")
                .build();
        // when
        UserLoginResponse userLoginResponse = userService.loginProcess(targetDto);
        // then
        assertThat(userRepository.findByRefreshToken(userLoginResponse.getUserRefreshToken())).isNotEmpty();
    }

    @DisplayName("로그아웃 성공 시, 해당 사용자의 Jwt Refresh Token 은 DB에서 삭제된다.")
    @Test
    void afterLogoutProcessRefreshTokenDeleteInDatabase() {
        // given
        User user3 = User.builder()
                .userId(3L)
                .userEmail("test3@test.com")
                .userPwd("296d85ce7f16267bc97d9184b130e03392b25afdcf0a21d010f5ed2236d95f6d")
                .userName("")
                .build();
        userRepository.save(user3);
        UserLoginResponse loggedInUser = userService.loginProcess(LoginServiceRequestDto.builder().email("test3@test.com").pwd("3333").build());
        String refreshToken = loggedInUser.getUserRefreshToken();
        // when
        userService.logoutProcess(refreshToken);
        // then
        assertThat(userRepository.findByRefreshToken(refreshToken)).isEmpty();
    }

    @DisplayName("refreshToken 으로 새로운 accessToken 을 발급받아 로그인 연장을 수행한다.")
    @Test
    void extendLoginStateWithRefreshToken() {
        // given
        User user3 = User.builder()
                .userId(3L)
                .userEmail("test3@test.com")
                .userPwd("296d85ce7f16267bc97d9184b130e03392b25afdcf0a21d010f5ed2236d95f6d")
                .userName("")
                .build();
        userRepository.save(user3);
        UserLoginResponse loggedInUser = userService.loginProcess(LoginServiceRequestDto.builder().email("test3@test.com").pwd("3333").build());
        String refreshToken = loggedInUser.getUserRefreshToken();

        // when
        String accessToken = userService.extendLoginState(refreshToken);
        // then
        assertThat(accessToken).isNotNull();
    }

    @DisplayName("회원탈퇴 시, 해당 사용자의 userState 는 'e' 로 변경된다.")
    @Test
    void signOut() {
        // given
        User user3 = User.builder()
                .userId(3L)
                .userEmail("test3@test.com")
                .userPwd("296d85ce7f16267bc97d9184b130e03392b25afdcf0a21d010f5ed2236d95f6d")
                .userName("")
                .build();
        userRepository.save(user3);
        UserLoginResponse loginUser = userService.loginProcess(LoginServiceRequestDto.builder().email("test3@test.com").pwd("3333").build());
        String token = "Authorization "+loginUser.getUserAccessToken();
        // when
        UserSignoutResponse exitedUser = userService.signOut(token, loginUser.getUserEmail());
        // then
        assertThat(exitedUser.getUserState()).isEqualTo("e");
    }

    @DisplayName("사용자 정보를 조회한다.")
    @Test
    void getUserInfo() {
        // given
        User user3 = User.builder()
                .userId(3L)
                .userEmail("test3@test.com")
                .userPwd("296d85ce7f16267bc97d9184b130e03392b25afdcf0a21d010f5ed2236d95f6d")
                .userName("")
                .build();
        userRepository.save(user3);
        UserLoginResponse loggedInUser = userService.loginProcess(LoginServiceRequestDto.builder().email("test3@test.com").pwd("3333").build());
        String refreshToken = "Authorization " + loggedInUser.getUserRefreshToken();
        // when
        UserResponse userInfo = userService.getUserInfo(refreshToken);
        // then
        assertThat(userInfo).extracting("userId", "userEmail")
                .containsExactlyInAnyOrder(3L, "test3@test.com");
    }

}