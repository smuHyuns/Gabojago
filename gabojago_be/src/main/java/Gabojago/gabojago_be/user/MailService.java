package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.user.redis.RedisService;
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
    private final RedisService redisService;

    @Value("${hostEmail}")
    private String fromAddress;
    private static final String TITLE = "Gabojago 인증번호 이메일입니다";
    private static final long AUTH_NUMBER_EXPIRE_TIME = 300;

    public void mailSend(String toAddress) {
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
            log.info("auth:{}", toAddress);
            log.info("authNumber : {}", authNumber);
            redisService.save("auth:" + toAddress, authNumber, AUTH_NUMBER_EXPIRE_TIME);
        } catch (Exception e) {
            log.error("메일 전송 중 오류가 발생했습니다: {}", e.getMessage());
            throw new GabojagoException(ErrorCode.MAIL_SEND_FAILED);
        }
    }

    private String getAuthenticateNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    public boolean verifyAuthNumber(String email, String authNumber) {
        String storedAuthNumber = redisService.get("auth:" + email);
        log.info("요청 이메일 : {}", email);
        log.info("요청 인증번호 : {}", authNumber);

        if (storedAuthNumber == null || !storedAuthNumber.equals(authNumber)) {
            throw new GabojagoException(ErrorCode.AUTH_NUMBER_INVALID);
        }
        redisService.delete("auth:" + email);
        return true;
    }
}
