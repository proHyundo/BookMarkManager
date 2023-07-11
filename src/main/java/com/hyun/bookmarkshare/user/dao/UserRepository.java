package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface UserRepository {

    /* ================================================================================================ */
    /* ============================================ SELECT ============================================ */

    /* TUSERACCOUNT TABLE */
    Optional<User> findByLoginServiceRequestDto(LoginServiceRequestDto loginServiceRequestDto);

    /* TUSERACCOUNT TABLE */
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserIdAndUserState(@Param("userId") Long userId, @Param("userState") String userState);

    /* TUSERACCOUNT TABLE */
    Integer countByUserEmail(String userEmail);

    /* TREFRESHTOKEN TABLE */
    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);

    /* ================================================================================================ */
    /* ============================================ INSERT ============================================ */

    /* TUSERACCOUNT TABLE */
    Integer saveBySignUpRequestDto(SignUpRequestDto signUpRequestDto);

    /* TREFRESHTOKEN TABLE */
    int saveUserRefreshToken(Long userId, String refreshToken);

    /* ================================================================================================ */
    /* ============================================ DELETE ============================================ */

    /* TREFRESHTOKEN TABLE */
    int deleteRefreshTokenByUserId(Long userId);

    int deleteByUserId(Long userId);


    // ONLY FOR TEST
    int save(User user);
}
