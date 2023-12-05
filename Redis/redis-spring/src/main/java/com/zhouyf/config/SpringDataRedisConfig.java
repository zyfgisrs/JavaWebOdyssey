package com.zhouyf.config;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

@Configuration
@PropertySource("classpath:redis.properties")
public class SpringDataRedisConfig {
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(
            @Value("${redis.host}") String hostName,
            @Value("${redis.port}") int port,
            @Value("${redis.username}") String username,
            @Value("${redis.password}") String password,
            @Value("${redis.database}") int database) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(hostName);
        config.setUsername(username);
        config.setPassword(RedisPassword.of(password));
        config.setDatabase(database);
        return config;
    }

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig(
            @Value("${redis.pool.maxTotal}") int maxTotal,
            @Value("${redis.pool.maxIdle}") int maxIdle,
            @Value("${redis.pool.minIdle}") int minIdle,
            @Value("${redis.pool.testOnBorrow}") boolean testOnBorrow
    ) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }

    @Bean
    public LettuceClientConfiguration lettuceClientConfiguration(
            @Autowired GenericObjectPoolConfig genericObjectPoolConfig
    ) {
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(genericObjectPoolConfig).build();
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(
            @Autowired RedisStandaloneConfiguration redisStandaloneConfiguration,
            @Autowired LettuceClientConfiguration lettuceClientConfiguration
    ) {

        LettuceConnectionFactory factory =
                new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        return factory;
    }

}
