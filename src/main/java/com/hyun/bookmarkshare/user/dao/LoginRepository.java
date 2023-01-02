package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginRepository {

    User findByLoginRequestDto(LoginRequestDto loginRequestDto);
}
