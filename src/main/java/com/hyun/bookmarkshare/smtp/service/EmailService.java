package com.hyun.bookmarkshare.smtp.service;

import com.hyun.bookmarkshare.smtp.EmailEntity;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    /**
     * 이메일 인증 코드 발송 로직.
     * 1. 이메일 인증 코드 생성
     * 2. 이메일 발송을 위한 설정
     * 3. 이메일 발송.
     * 4. 이메일 발송 정보 저장.
     * @param targetEmail 이메일 발송 대상 이메일
     * */
    public void sendEmailWithValidationCode(String targetEmail){
        String validationCode = sendEmail(targetEmail, createEmailValidationCode());
        emailRepository.saveByEmailAndValidationCode(targetEmail, validationCode);
    }

    /**
     * 이메일 발송. 이메일 발송을 위한 설정을 마친 후, 이메일 발송.
     * @param targetEmail 이메일 발송 대상 이메일
     * @param validationCode 이메일 발송 시 함께 보낼 이메일 인증 코드
     * */
    private String sendEmail(String targetEmail, String validationCode){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(targetEmail);
            mimeMessageHelper.setSubject("[BookmarkShare] 회원가입 인증 메일입니다.");
            mimeMessageHelper.setText(createEmailContent(validationCode), true);
            javaMailSender.send(mimeMessage);
            log.info("[성공]회원가입 인증 메일 발송 완료: ");
        } catch (MessagingException e) {
            log.warn("[실패]회원가입 인증 메일 발송 실패: ");
            e.printStackTrace();
        }
        return validationCode;
    }

    /**
     * 이메일 인증에 사용될 8자리 랜덤 문자열 생성.
     * @return 생성된 랜덤 문자열
     * */
    private String createEmailValidationCode(){
        Random random = new Random();
        StringBuffer validationCode = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: validationCode.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: validationCode.append((char) ((int) random.nextInt(26) + 65)); break;
                default: validationCode.append(random.nextInt(9));
            }
        }
        return validationCode.toString();
    }

    private String createEmailContent(String validationCode){
        StringBuffer msg = new StringBuffer();
        msg.append("<h1>회원가입 인증 메일입니다.</h1>");
        msg.append("<p>아래의 인증 코드를 입력해주세요.</p>");
        msg.append("<p id=\"copyTxt\">인증 코드: " + validationCode + "</p>");
        msg.append("<div>");
            msg.append("<input type=\"button\" value=\"인증 코드 복사하기\" onclick=\"copyT()\"/>");
        msg.append("</div>");
        msg.append("<script>");
            msg.append("function copyT() {");
                msg.append("var obj = document.getElementById(\"copyTxt\");");
                msg.append("var range = document.createRange();");
                msg.append("range.selectNode(obj.childNodes[0]);");
                msg.append("var sel = window.getSelection();");
                msg.append("sel.removeAllRanges();");
                msg.append("sel.addRange(range);");
                msg.append("document.execCommand(\"copy\");");
                msg.append("alert(\"복사되었습니다.\");");
            msg.append("}");
        msg.append("</script>");
        return msg.toString();
    }

    public void checkEmailValidationCode(String emailValidationCode, String email) {
        EmailEntity emailEntity = emailRepository.findByEmailAndValidationCode(email, emailValidationCode).orElseThrow(()->{
            return new IllegalArgumentException("이메일 인증 코드가 유효하지 않습니다.");
        });

        int resultRows = emailRepository.deleteByValidatedEmail(emailEntity.getEmail());
        if (resultRows == 0) {
            throw new IllegalArgumentException("이메일 인증에 실패했습니다.");
        }
    }
}
