package com.zhouyf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.Objects;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(
            @Autowired @Qualifier("cacheTemplate") RedisTemplate<String, Object> cacheTemplate
    ) {
        RedisCacheWriter redisCacheWriter =
                RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(cacheTemplate.getConnectionFactory()));
        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig().
                        serializeValuesWith(RedisSerializationContext.
                                SerializationPair.fromSerializer(cacheTemplate.getValueSerializer()));

        //可以在这里添加更多的配置，例如entry的过期时间等
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
}
