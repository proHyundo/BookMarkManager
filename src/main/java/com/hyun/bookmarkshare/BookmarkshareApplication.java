package com.hyun.bookmarkshare;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@OpenAPIDefinition(servers = {
//		@Server(url = "http://localhost:5173", description = "Local Server"),
//		@Server(url = "https://bookmark-tool.com", description = "Live Server")
//})
@SpringBootApplication
public class BookmarkshareApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookmarkshareApplication.class, args);
	}
}
