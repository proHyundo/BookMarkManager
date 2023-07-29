package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class UserRepositoryInsertQueryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach() {
        jdbcTemplate.update("ALTER TABLE TUSERACCOUNT AUTO_INCREMENT = 2;");
    }

    @DisplayName("신규 가입한 사용자를 저장한다.")
    @Test
    void save() {
        // given
        User user = User.builder()
                .userId(null)
                .userEmail("test3@test.com")
                .userPwd("3333")
                .userName("username3")
                .userPhoneNum(null)
                .userRegDate(null)
                .userModDate(null)
                .userState("n")
                .userGrade("g")
                .userRole("ROLE_USER")
                .userSocial("n")
                .build();
        // when
        userRepository.saveNew(user);
        // then
        assertThat(userRepository.findByUserId(3L).get()).extracting("userEmail").isEqualTo("test3@test.com");
    }
   
}
