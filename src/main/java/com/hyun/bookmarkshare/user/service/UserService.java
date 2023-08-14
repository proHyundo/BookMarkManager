package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * @implSpec Login Request Input data Validation & call DB Sql to find User with Id and Pwd.
     * @param loginRequestDto Login request Form data. Include Id, Pwd.
     * @return User Entity from DB SELECT Sql result.
     * */
    UserLoginResponse loginProcess(LoginServiceRequestDto loginRequestDto);

    UserResponse signUp(UserSignUpServiceRequestDto userSignUpServiceRequestDto);

    void logoutProcess(String refreshToken);

    String extendLoginState(String refreshToken);

    UserResponse getUserInfo(String token);

    UserResponse signOut(String token, String userEmail);

    boolean checkDuplicateEmail(String userEmail);
}
