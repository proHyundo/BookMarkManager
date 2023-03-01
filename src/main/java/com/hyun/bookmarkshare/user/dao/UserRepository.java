package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {

    /* TUSERACCOUNT TABLE */
    Optional<User> findByLoginRequestDto(LoginRequestDto loginRequestDto);

    Optional<User> findByUserId(Long userId);

    /* TREFRESHTOKEN TABLE */
    int saveUserRefreshToken(Long userSeq, String refreshToken);

    Optional<Long> findByRefreshToken(String refreshToken);

    int deleteRefreshTokenByUserSeq(Long userSeq);
}
