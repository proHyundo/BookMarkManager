package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class UserRepositorySelectQueryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach() {
        jdbcTemplate.update("ALTER TABLE TUSERACCOUNT AUTO_INCREMENT = 2;");
    }

    @DisplayName("사용자 email과 Encoded password로 사용자 정보를 조회한다.")
    @Test
    void findByEmailAndEncodedPassword() {
        // given
        User user = User.builder()
                .userEmail("test3@test.com")
                .userPwd("3333")
                .userName("username3")
                .userPhoneNum("01033333333")
                .userRegDate(null)
                .userModDate(null)
                .userState("n")
                .userGrade("g")
                .userRole("ROLE_USER")
                .userSocial("n")
                .build();
        userRepository.save(user);
        // when
        Optional<User> resultUser = userRepository.findByLoginServiceRequestDto(LoginServiceRequestDto.builder()
                .email("test3@test.com")
                .pwd("3333")
                .build());
        // then
        assertThat(resultUser.get())
                .extracting("userId", "userEmail")
                .containsExactly(3L, "test3@test.com");
    }

    @DisplayName("사용자 id로 사용자를 조회한다.")
    @Test
    void findByUserId() {
        // given
        Long targetUserId = 1L;
        // when
        Optional<User> byUserId = userRepository.findByUserId(targetUserId);
        // then
        assertThat(byUserId.get())
                .extracting("userId", "userEmail")
                .containsExactly(1L, "test@test.com");
    }


    @DisplayName("특정 email로 등록된 사용자가 몇 행 있는지 조회한다.")
    @Test
    void countByUserEmail() {
        // given
        String targetEmail = "test@test.com";
        // when
        Integer count = userRepository.countByUserEmail(targetEmail);
        // then
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("userId 와 userState 으로 사용자를 조회한다.")
    @Test
    void findByUserIdAndState() {
        // given
        User user = User.builder()
                .userEmail("test3@test.com")
                .userPwd("3333")
                .userName("username3")
                .userPhoneNum("01033333333")
                .userRegDate(null)
                .userModDate(null)
                .userState(null)
                .userGrade("g")
                .userRole("ROLE_USER")
                .userSocial("n")
                .build();
        userRepository.save(user);
        userRepository.deleteByUserId(user.getUserId());
        // when
        Optional<User> resultUser = userRepository.findByUserIdAndUserState(user.getUserId(), "e");
        // then
        assertThat(resultUser.get()).extracting("userId", "userEmail", "userState")
                .containsExactly(3L, "test3@test.com", "e");
    }

}
