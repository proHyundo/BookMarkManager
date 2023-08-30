package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// TODO : REDIS 도입
@Mapper
public interface TokenRepository {

    Integer findByUserId(Long userId);

    Optional<UserRefreshToken> findByRefreshToken(String userRefreshToken);

    Integer countByRefreshToken(String userRefreshToken);

    Integer saveRefreshToken(Long userId, String userRefreshToken);

    Integer updateRefreshToken(Long userId, String userRefreshToken);

    int deleteRefreshTokenByUserId(Long userId);
}
