package com.info.redis.config;

import com.info.dto.constants.Constants;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class RedisConfig {

    // Cache Manager for @Cacheable
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(5))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        cacheConfigs.put(Constants.CACHE_NAME_USER, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigs.put(Constants.CACHE_NAME_BRACNH, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigs.put(Constants.CACHE_NAME_MBKBRN, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigs.put(Constants.CACHE_NAME_DIVISION, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigs.put(Constants.CACHE_NAME_DEPARTMENT, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigs.put(Constants.CACHE_NAME_API_TRACE, defaultConfig.entryTtl(Duration.ofMinutes(15)));
        cacheConfigs.put(Constants.CACHE_NAME_REMITTANCE_DATA, defaultConfig.entryTtl(Duration.ofMinutes(30)));
        cacheConfigs.put(Constants.CACHE_NAME_IC_CASH_REMITTANCE_DATA, defaultConfig.entryTtl(Duration.ofHours(5)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }

    // RedisTemplate for Redis operations
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
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
