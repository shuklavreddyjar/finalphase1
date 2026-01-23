package com.kirana.finalphase1.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * The type Rate limit service.
 */
@Slf4j
@Service
public class RateLimitService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Instantiates a new Rate limit service.
     *
     * @param redisTemplate the redis template
     */
    public RateLimitService(
            @Qualifier("rateLimitRedisTemplate")
            RedisTemplate<String, Object> redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    /**
     * Is allowed boolean.
     *
     * @param key   the key
     * @param limit the limit
     * @return the boolean
     */
    public boolean isAllowed(String key, int limit) {

        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(1));
            log.info("Rate limit window started for key={}", key);
        }

        boolean allowed = count != null && count <= limit;

        log.info("RateLimit check key={} count={} allowed={}", key, count, allowed);

        return allowed;
    }
}
