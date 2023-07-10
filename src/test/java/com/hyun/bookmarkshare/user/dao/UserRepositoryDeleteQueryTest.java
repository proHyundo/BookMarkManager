package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class UserRepositoryDeleteQueryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initEach() {
        jdbcTemplate.update("ALTER TABLE TUSERACCOUNT AUTO_INCREMENT = 2;");
    }

    @DisplayName("사용자 id로 회원 탈퇴 flag 를 exit 으로 수정 한다.")
    @Test
    void deleteByUserId() {
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
        userRepository.save(user);
        // when
        userRepository.deleteByUserId(user.getUserId());
        // then
        assertThat(userRepository.findByUserId(user.getUserId()).get()).extracting("userState").isEqualTo("e");
    }

}
