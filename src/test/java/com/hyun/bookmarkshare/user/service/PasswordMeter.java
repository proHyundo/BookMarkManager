package com.hyun.bookmarkshare.user.service;

public class PasswordMeter {
    public PasswordMeterResult meter(String input) {
        PasswordMeterResult result = PasswordMeterResult.STRONG;

        // null 값 INVALID
        if(input == null || input.isEmpty()) return PasswordMeterResult.INVALID;

        // 길이 조건 미충족시 NORMAL
        if(input.length() < 8) result = PasswordMeterResult.NORMAL;

        // 숫자 미포함시 NORMAL
        boolean containsNum = isContainsNum(input);
        if(!containsNum) result = PasswordMeterResult.NORMAL;

        // 대문자 미포함시 NORMAL
        boolean containsUpperChar = isContainsUpperChar(input);
        if(!containsUpperChar) result = PasswordMeterResult.NORMAL;
        return result;
    }

    private boolean isContainsUpperChar(String input) {
        boolean containsUpperChar = false;
        for (char c : input.toCharArray()) {
            if(Character.isUpperCase(c)){
                containsUpperChar = true;
                break;
            }
        }
        return containsUpperChar;
    }

    private boolean isContainsNum(String input) {
        boolean containsNum = false;
        for (char ch : input.toCharArray()) {
            if(ch >= '0' && ch <= '9'){
                containsNum = true;
                break;
            }
        }
        return containsNum;
    }
}
