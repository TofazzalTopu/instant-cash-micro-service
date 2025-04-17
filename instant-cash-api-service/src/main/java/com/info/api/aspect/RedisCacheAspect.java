package com.info.api.aspect;

import com.info.api.annotation.RedisCacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(redisCacheable)")
    public Object handleCache(ProceedingJoinPoint joinPoint, RedisCacheable redisCacheable) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String customKey = redisCacheable.key();
        long ttl = redisCacheable.ttl();

        Object[] args = joinPoint.getArgs();
        String defaultKey = method.getDeclaringClass().getSimpleName() + "." + method.getName() + Arrays.toString(args);

        String key = (customKey.isEmpty()) ? defaultKey : customKey + Arrays.toString(args);

        logger.info(" Checking Redis for key: {}", key);
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            logger.info(" Cache hit!");
            return cached;
        }

        logger.info(" Cache miss, calling method...");
        Object result = joinPoint.proceed();

        if (result != null) {
            redisTemplate.opsForValue().set(key, result, ttl, TimeUnit.SECONDS);
            logger.info(" Cached with TTL {}s, key: {}", ttl, key);
        }

        return result;
    }
}
