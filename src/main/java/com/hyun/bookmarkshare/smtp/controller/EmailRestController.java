package com.hyun.bookmarkshare.smtp.controller;

import com.hyun.bookmarkshare.smtp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class EmailRestController {

    private final EmailService emailService;

    /**
     * 이메일 인증 코드 발송 요청 처리.
     * @param email 이메일 인증 코드를 발송할 대상 이메일
     * @return ResponseEntity
     * */
    @PostMapping("/signup/email/verification")
    public ResponseEntity sendEmailRequest(@RequestBody String email) {
        System.out.println("EmailRestController.sendEmailRequest > email = " + email);
        emailService.sendEmailWithValidationCode(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/signup/email/verification")
    public ResponseEntity checkEmailValidationCode(@RequestBody String emailValidationCode, String email) {
        emailService.checkEmailValidationCode(emailValidationCode, email);
        return ResponseEntity.ok().build();
    }
}
