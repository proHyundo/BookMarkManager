package com.hyun.bookmarkshare.user.service;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PwdEncoder {

    public String encode(String password) throws NoSuchAlgorithmException {
        // MD2, MD4, MD5, SHA-1, SHA-256, SHA-512
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.reset();

        // salt 바이트 데이터를 사용해 다이제스트를 갱신
        String salt = "book!";
        md.update(salt.getBytes());

        // 바이트배열로 해쉬를 반환, 패딩 등의 최종 처리를 행해 해시 계산을 완료
        byte[] digested = md.digest(password.getBytes());

        return String.format("%064x", new BigInteger(1, digested));
    }
}
