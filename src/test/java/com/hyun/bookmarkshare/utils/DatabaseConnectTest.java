package com.hyun.bookmarkshare.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatabaseConnectTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
	private DataSource dataSource;

	@Test
	public void printDBInfo() throws SQLException {
		Connection connection = dataSource.getConnection();
        // DB 접속 정보 출력
        log.info("connection.getMetaData().getURL(): {}", connection.getMetaData().getURL());
        System.out.println("connection.getMetaData().getURL(): " + connection.getMetaData().getURL());
        // DB 접속 계정 정보 출력
        log.info("connection.getMetaData().getUserName(): {}", connection.getMetaData().getUserName());
        System.out.println("connection.getMetaData().getUserName(): " + connection.getMetaData().getUserName());
        // DB 접속 드라이버 정보 출력
        log.info("connection.getMetaData().getDriverName(): {}", connection.getMetaData().getDriverName());
        System.out.println("connection.getMetaData().getDriverName(): " + connection.getMetaData().getDriverName());
	}
}
