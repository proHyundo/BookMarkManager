package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {

    Optional<User> findByLoginRequestDto(LoginRequestDto loginRequestDto);

    Optional<User> findByUserId(Long userId);
}
