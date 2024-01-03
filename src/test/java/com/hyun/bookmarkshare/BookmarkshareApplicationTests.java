package com.hyun.bookmarkshare;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class BookmarkshareApplicationTests {

//	@Autowired
//	private DataSource dataSource;
//
//	@Test
//	public void printDBInfo() throws SQLException {
//		System.out.println(dataSource.toString()); // DataSource 정보 출력
//		Connection connection = dataSource.getConnection();
//		System.out.println(connection.getMetaData().getURL()); // 실제 연결된 DB URL 출력
//		System.out.println(connection.getMetaData().getUserName()); // DB 사용자 이름 출력
//	}

	@Test
	void contextLoads() {
	}

}
