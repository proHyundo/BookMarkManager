package com.hyun.bookmarkshare.user.controller;

import com.hyun.bookmarkshare.user.controller.dto.UserResponseEntity;
import com.hyun.bookmarkshare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    // TODO localhost:9090/logout/ 으로 요청해야 받아지는 문제 발생.
    // "/user/logout/" 으로 변경 시도.
//    @DeleteMapping("/user/logout")
//    public ResponseEntity<LogoutResponseEntity> logoutRequest(@RequestHeader("Authorization") String token,
//                                                              @RequestBody @NotEmpty HashMap<String, String> map,
//                                                              BindingResult bindingResult) {
//
//        if(bindingResult.hasErrors()){
//            return LogoutResponseEntity.toResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//
//        // token repository 에서 refresh Token 에 해당하는 값을 삭제한다.
//        userService.logoutProcess(map.get("refreshToken"));
//        return LogoutResponseEntity.toResponseEntity(HttpStatus.OK);
//    }

    @GetMapping("/user/info")
    public ResponseEntity<UserResponseEntity> getUserInfoRequest(@RequestHeader(value = "Authorization") String accessToken){
        return UserResponseEntity.toResponseEntity(userService.getUserInfo(accessToken));
    }

    @DeleteMapping("/user/sign-out")
    public ResponseEntity<UserResponseEntity> signOutRequest(@RequestHeader("Authorization") String token){
        return UserResponseEntity.toResponseEntity(userService.signOut(token));
    }





}
