package Gabojago.gabojago_be.user.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String key, String value, long expireTime) {
        try {
            log.info("Redis 저장 요청 - Key: {}, Value: {}, ExpireTime: {}초", key, value, expireTime);
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            log.info("Redis 저장 성공 - Key: {}", key);
        } catch (Exception e) {
            log.error("Redis 저장 실패 - Key: {}, 에러 메시지: {}", key, e.getMessage());
        }
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
