package com.kirana.finalphase1.cache;

import com.kirana.finalphase1.enums.CurrencyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Slf4j
@Service
public class FxRateCacheService {

    private final RedisTemplate<String, BigDecimal> redisTemplate;

    public FxRateCacheService(RedisTemplate<String, BigDecimal> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String key(CurrencyType from, CurrencyType to) {
        return "fx:" + from + ":" + to;
    }

    public BigDecimal getRate(CurrencyType from, CurrencyType to) {
        BigDecimal rate = redisTemplate.opsForValue().get(key(from, to));
        log.debug("Redis GET key={} value={}", key(from, to), rate);
        return rate;
    }

    public void saveRate(
            CurrencyType from,
            CurrencyType to,
            BigDecimal rate,
            long ttlSeconds) {

        redisTemplate.opsForValue().set(
                key(from, to),
                rate,
                Duration.ofSeconds(ttlSeconds)
        );

        log.info("FX RATE SAVED → {} → {} = {} (TTL={}s)",
                from, to, rate, ttlSeconds);
    }
}
