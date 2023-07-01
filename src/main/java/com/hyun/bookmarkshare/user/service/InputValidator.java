package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.user.controller.dto.LoginRequestDto;
import com.hyun.bookmarkshare.user.exceptions.LoginExceptionErrorCode;
import com.hyun.bookmarkshare.user.exceptions.LoginInputValidateFailException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class InputValidator {

    /**
     * Deprecated : @Valid 어노테이션을 통한 DTO 검증방식으로 변경.
     * */
    public void emailMeter(String emailInput) {

        // 길이 조건 검증
        if(emailInput == null || emailInput.isEmpty() || emailInput.length()<6 || emailInput.length()>20 ){
            throw new LoginInputValidateFailException(LoginExceptionErrorCode.BAD_INPUT_LENGTH, "Bad Input Length");
        }

        // 이메일 문자열 검증
        if( Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", emailInput) == false){
            throw new LoginInputValidateFailException(LoginExceptionErrorCode.BAD_INPUT_EMAIL, "Bad Input Email");
        }

//         왜 이건 안될까?
//        if( Pattern.matches("/[^a-zA-Z0-9]/", emailInput.split("@")[0]) == false){
//            throw new LoginInputValidateFailException(LoginExceptionErrorCode.BAD_INPUT_EMAIL);
//        }

    }

    public void pwdMeter(String pwdInput){
        if(pwdInput == null || pwdInput.isEmpty() || pwdInput.length()<4 || pwdInput.length()>20 ){
            throw new LoginInputValidateFailException(LoginExceptionErrorCode.BAD_INPUT_PWD, "Bad Input Pwd");
        }
    }
}
