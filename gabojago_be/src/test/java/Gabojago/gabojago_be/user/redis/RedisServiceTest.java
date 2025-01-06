package Gabojago.gabojago_be.user.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testRedis() {
        redisService.save("key1", "value1", 60);
        assert "value1".equals(redisService.get("key1"));
        redisService.delete("key1");
        assert redisService.get("key1") == null;
    }

    @Test
    public void testSaveAndGet() {
        String key = "testKey";
        String value = "testValue";
        redisService.save(key, value, 300);

        String result = redisService.get(key);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(value, result);

        redisService.delete(key);
    }
}