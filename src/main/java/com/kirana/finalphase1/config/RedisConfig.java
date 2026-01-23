package com.kirana.finalphase1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.math.BigDecimal;

/**
 * The type Redis config.
 */
@Configuration
public class RedisConfig {

    /**
     * Fx rate redis template redis template.
     *
     * @param connectionFactory the connection factory
     * @return the redis template
     */
// FX RATE CACHE
    @Bean
    public RedisTemplate<String, BigDecimal> fxRateRedisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, BigDecimal> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    /**
     * Rate limit redis template redis template.
     *
     * @param connectionFactory the connection factory
     * @return the redis template
     */
//  RATE LIMITING
    @Bean
    public RedisTemplate<String, Object> rateLimitRedisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
