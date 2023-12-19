package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSocialSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.user.service.response.UserSignoutResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    UserResponse getUserInfo(Long userId);

    UserSignoutResponse signOut(String refreshToken, String userEmail);

    @Transactional
    UserResponse signUpBySocialAccount(UserSocialSignUpServiceRequestDto requestDto);

    boolean checkDuplicateEmail(String userEmail);

    Boolean checkRegisteredEmail(String userEmail, String provider);
}
