package com.travellog.travellog.services.impl;

import com.travellog.travellog.services.spec.IRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addValue(String key, String value, long timeout, TimeUnit timeUnit) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, timeout, timeUnit);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
