package com.hyun.bookmarkshare.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PwdEncoderTest {

    PwdEncoder pwdEncoder = new PwdEncoder();

    @DisplayName("input pwd encode hash")
    @Test
    void encode() throws NoSuchAlgorithmException {

        // given
        String pwd = "1111";
        // when
        String encoded = pwdEncoder.encode(pwd);
        // then
        System.out.println("encoded = " + encoded);

    }

}