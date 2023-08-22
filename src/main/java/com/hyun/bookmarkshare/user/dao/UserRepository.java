package com.hyun.bookmarkshare.user.dao;

import com.hyun.bookmarkshare.user.controller.dto.request.UserSignUpRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {

    /* ================================================================================================ */
    /* ============================================ SELECT ============================================ */

    /* TUSERACCOUNT TABLE */
    Optional<User> findByLoginServiceRequestDto(LoginServiceRequestDto loginServiceRequestDto);
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserIdAndUserState(@Param("userId") Long userId, @Param("userState") String userState);

    /* TUSERACCOUNT TABLE */
    Integer countByUserEmail(String userEmail);

    /* TREFRESHTOKEN TABLE */
    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);

    /* ================================================================================================ */
    /* ============================================ INSERT ============================================ */

    /* TUSERACCOUNT TABLE */
    Integer saveBySignUpRequestDto(UserSignUpRequestDto userSignUpRequestDto);
    int saveNew(User user);

    /* TREFRESHTOKEN TABLE */
    int saveUserRefreshToken(Long userId, String refreshToken);

    /* ================================================================================================ */
    /* ============================================ DELETE ============================================ */

    int deleteByUserId(Long userId);


    // ONLY FOR TEST
    int save(User user);
}
