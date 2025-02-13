package young.blaybus.domain.member.security.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) { this.redisTemplate = redisTemplate; }

    public void saveRefreshToken(String userId, String refreshToken, long duration) {
        redisTemplate.opsForValue().set("refresh:" + userId, refreshToken, duration, TimeUnit.SECONDS);
    }

    public String getRefreshToken(String userId) {
        return (String) redisTemplate.opsForValue().get("refresh:" + userId);
    }
}
