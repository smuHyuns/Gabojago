package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${hostEmail}")
    private String fromAddress;
    private static final String TITLE = "Gabojago 인증번호 이메일입니다";

    public String mailSend(String toAddress) {
        String authNumber = getAuthenticateNumber();

        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setFrom(fromAddress);
            mailHandler.setTo(toAddress);
            mailHandler.setSubject(TITLE);

            Context context = new Context();
            context.setVariable("authNumber", authNumber);
            String htmlContent = templateEngine.process("verification-email", context);

            mailHandler.setText(htmlContent, true);
            mailHandler.send();

            log.info("메일이 성공적으로 전송되었습니다: {}", toAddress);
        } catch (Exception e) {
            log.error("메일 전송 중 오류가 발생했습니다: {}", e.getMessage());
            throw new GabojagoException(ErrorCode.MAIL_SEND_FAILED);
        }

        return authNumber;
    }

    private String getAuthenticateNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    private String getAuthMessage(String number) {
        return String.format("인증번호는 %s 입니다.", number);
    }
}
