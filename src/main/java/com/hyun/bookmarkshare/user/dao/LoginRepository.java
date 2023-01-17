package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface LoginRepository {

    Optional<User> findByLoginRequestDto(LoginRequestDto loginRequestDto);
}
