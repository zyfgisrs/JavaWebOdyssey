package com.zhouyf.service.impl;

import com.zhouyf.service.ITokenService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements ITokenService {

    private static final String TOKEN_PREFIX = "zhouyfToken:";

    private static final long EXPIRE_TIME = 30; //设置过期时间


    final StringRedisTemplate stringRedisTemplate;

    public TokenServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String createToken(String code) {
        String key = TOKEN_PREFIX + code;
        if(this.stringRedisTemplate.hasKey(key)){
            return null;
        }
        String value = UUID.randomUUID().toString();
        this.stringRedisTemplate.opsForValue().setIfAbsent(
                key, value, EXPIRE_TIME, TimeUnit.SECONDS
        );
        return value;
    }

    @Override
    public boolean checkToken(String code, String token) {
        String key = TOKEN_PREFIX + code;
        String value = this.stringRedisTemplate.opsForValue().get(key);
        if(value != null){
            if(value.equals(token)){
                this.stringRedisTemplate.delete(key);
                return true;
            }
        }
        return false;
    }
}
