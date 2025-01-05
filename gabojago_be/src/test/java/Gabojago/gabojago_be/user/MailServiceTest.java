package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.user.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisService redisService;

    @Test
    public void testAuthNumberFlow() {
        String email = "test@example.com";

        mailService.mailSend(email);

        String storedAuthNumber = redisService.get("auth:" + email);

        Assertions.assertNotNull(storedAuthNumber, "Redis에 저장된 인증번호가 null입니다.");
        log.info("Redis에 저장된 인증번호: {}", storedAuthNumber);

        boolean isValid = mailService.verifyAuthNumber(email, storedAuthNumber);
        Assertions.assertTrue(isValid, "인증번호 검증에 실패했습니다.");
        String afterVerification = redisService.get("auth:" + email);
        Assertions.assertNull(afterVerification, "인증번호가 삭제되지 않았습니다.");
    }

}
