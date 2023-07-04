package com.hyun.bookmarkshare.smtp.service;

import com.hyun.bookmarkshare.smtp.entity.EmailEntity;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import com.hyun.bookmarkshare.smtp.exception.EmailExceptionErrorCode;
import com.hyun.bookmarkshare.smtp.exception.EmailProcessException;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final @Value("${email.value.property}") String fromEmail;

    /**
     * 이메일 인증 코드 발송 로직.
     * 1. 이메일 발송 횟수 제한 검증
     * 2. 이메일 인증 코드 생성
     * 3. 이메일 발송을 위한 설정
     * 4. 이메일 발송.
     * 5. 이메일 발송 정보 저장.
     * @param targetEmail 이메일 발송 대상 이메일
     * */
    public void sendEmailWithValidationCode(String targetEmail){
        // 이미 회원가입된 이메일인지 확인
        if(userRepository.countByUserEmail(targetEmail) > 0){
            throw new EmailProcessException(EmailExceptionErrorCode.ALREADY_USER_EXIST);
        }

        // 이메일 발송 횟수 체크
        if(checkEmailSendCntLimit(targetEmail)){
            throw new IllegalArgumentException("이메일 발송 횟수가 초과되었습니다.");
        };

        // 이메일 발송
        String validationCode = sendEmail(targetEmail, createEmailValidationCode());

        // 이메일 발송 정보 저장 :
        if(emailRepository.findByEmail(targetEmail).isEmpty()){
            emailRepository.saveByEmailAndValidationCode(targetEmail, validationCode);
        }else{
            emailRepository.updateByEmailAndValidationCode(targetEmail, validationCode);
        }
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
            mimeMessageHelper.setFrom(fromEmail);
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
//        String htmlFilePath = "src/main/resources/static/email/signupTemplate.html";
//        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)), StandardCharsets.UTF_8);

        StringBuffer msg = new StringBuffer();
        msg.append("<h1 style=\"color: dodgerblue\">회원가입 인증 메일입니다.</h1>");
        msg.append("<p>아래의 인증 코드를 입력해주세요.</p>");
        msg.append("<p id=\"copyTxt\">" + validationCode + "</p>");
        msg.append("<div>");

        msg.append("<input type=\"button\" value=\"인증 코드 복사하기\" onclick=\"alert('!')\" />");
        msg.append("</div>");
        msg.append("<script>");
        msg.append("<script>\n" +
                "    function copyT() {\n" +
                "        let obj = document.getElementById(\"copyTxt\");\n" +
                "        let range = document.createRange();\n" +
                "        range.selectNode(obj.childNodes[0]);\n" +
                "        let sel = window.getSelection();\n" +
                "        sel.removeAllRanges();\n" +
                "        sel.addRange(range);\n" +
                "        document.execCommand(\"copy\");\n" +
                "        alert(\"복사되었습니다.\");\n" +
                "    };\n" +
                "</script>");
        msg.append("</script>");
        return msg.toString();
    }

    private boolean checkEmailSendCntLimit(String targetEmail) {
        Optional<EmailEntity> emailEntity = emailRepository.findByEmail(targetEmail);
        if(emailEntity.isEmpty()){
            return false;
        }
        int timeLimit = 10;
        Date now = new Date();
        Date sendDate = emailEntity.get().getSendDate();
        Date after10Min = new Date(sendDate.getTime() + timeLimit * 60 * 1000);

        if (emailEntity.isEmpty()) { // 최초 발송
            return false;
        } else if (emailEntity.get().getSendCnt() >= 5 && after10Min.after(now)) { // 5회 발송 && 10분 안지남
            log.info("5회 발송 && 10분 안지남");
            return true;
        } else if (emailEntity.get().getSendCnt() >= 5 && after10Min.before(now)) { // 5회 발송 && 10분 지남
            log.info("5회 발송 && 10분 지남 >> 초기화 진행");
            // 발송시간 및 발송 횟수 초기화, False 반환
            emailRepository.deleteByEmail(targetEmail);
            return false;
        } else {
            return false;
        }
    }

    public void checkEmailValidationCode(String emailValidationCode, String email) {
        EmailEntity emailEntity = emailRepository.findByEmailAndValidationCode(email, emailValidationCode).orElseThrow(()->{
            return new IllegalArgumentException("이메일 인증 코드가 유효하지 않습니다.");
        });

        int resultRows = emailRepository.updateEmailValidationFlag(emailEntity.getEmail(), emailEntity.getEmailCode());
        if (resultRows == 0) {
            throw new IllegalArgumentException("이메일 인증에 실패했습니다.");
        }
    }
}
