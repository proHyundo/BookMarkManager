package com.hyun.bookmarkshare.smtp.controller;

import com.hyun.bookmarkshare.smtp.controller.dto.EmailResponseEntity;
import com.hyun.bookmarkshare.smtp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class EmailRestController {

    private final EmailService emailService;

    /**
     * 이메일 인증 코드 발송 요청 처리.
     * @param requestMap 이메일 인증 코드를 발송할 대상 이메일
     * @return ResponseEntity
     * */
    @PostMapping("/signup/email/verification")
    public ResponseEntity<EmailResponseEntity> sendEmailRequest(@RequestBody Map<String, Object> requestMap) {
        emailService.sendEmailWithValidationCode((String) requestMap.get("email"));
        return EmailResponseEntity.toResponseEntity(HttpStatus.OK, "이메일 인증 코드가 발송되었습니다.");
    }

    @PostMapping("/signup/email/verification/check")
    public ResponseEntity<EmailResponseEntity> checkEmailValidationCode(@RequestBody Map<String, Object> requestMap) {
        emailService.checkEmailValidationCode((String) requestMap.get("emailValidationCode"),
                                              (String) requestMap.get("email"));
        return EmailResponseEntity.toResponseEntity(HttpStatus.OK, "이메일 인증 성공.");
    }
}
