package com.hyun.bookmarkshare.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PasswordStrengthMeterTest {

    private PasswordMeter passwordMeter = new PasswordMeter();
    
    // 메소드 분리
    private void assertStrength(String pwd, PasswordMeterResult expectStrength){
        assertThat(passwordMeter.meter(pwd)).isEqualTo(expectStrength);
    }; 

    @DisplayName("암호 모든 조건 충족 - 강도 강함")
    @Test
    void allTrueStrong(){
        // given
        // when
        PasswordMeterResult result = passwordMeter.meter("abc123!!ZZ");
        // then
        assertThat(result).isEqualTo(PasswordMeterResult.STRONG);
    }

    @DisplayName("길이 조건 미충족 외 충족 - 강도 중간")
    @Test
    void lengthFalseThenNormal(){
        // given
        // when
        PasswordMeterResult result = passwordMeter.meter("a1!Z");
        // then
        assertThat(result).isEqualTo(PasswordMeterResult.NORMAL);
    }

    @DisplayName("숫자 미포함 외 충족 - 강도 중간")
    @Test
    void noNumThenNormal(){
        // given
        // when
        PasswordMeterResult result = passwordMeter.meter("abcd!!ZZ");
        // then
        assertThat(result).isEqualTo(PasswordMeterResult.NORMAL);
    }
    
    @DisplayName("대문자 미포함 외 충족 - 강도 중간")
    @Test
    void noUpperChar(){
        assertStrength("abcd1234!!", PasswordMeterResult.NORMAL);
        // method extract가 가능하지만, given when then 형식이 더 명확해 보인다.
    }

    @DisplayName("null")
    @Test
    void nullInputThenInvalid(){
        // given
        // when
        PasswordMeterResult result1 = passwordMeter.meter("");
        PasswordMeterResult result2 = passwordMeter.meter(null);
        // then
        assertThat(result1).isEqualTo(PasswordMeterResult.INVALID);
        assertThat(result2).isEqualTo(PasswordMeterResult.INVALID);
    }
}
