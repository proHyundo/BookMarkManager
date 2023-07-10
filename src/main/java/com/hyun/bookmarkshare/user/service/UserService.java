package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRefreshResponseDto;
import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.SignUpRequestDto;
import com.hyun.bookmarkshare.user.controller.dto.UserRequestDto;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * @implSpec Login Request Input data Validation & call DB Sql to find User with Id and Pwd.
     * @param loginRequestDto Login request Form data. Include Id, Pwd.
     * @return User Entity from DB SELECT Sql result.
     * */
    User loginProcess(LoginServiceRequestDto loginRequestDto);

    User signUp(SignUpRequestDto signUpRequestDto);

    void logoutProcess(String refreshToken);

    String extendLoginState(String refreshToken);

    User getUserInfo(String token);

    User signOut(String token);

    boolean checkDuplicateEmail(String userEmail);
}
