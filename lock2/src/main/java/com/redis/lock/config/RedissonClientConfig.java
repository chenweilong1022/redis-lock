package com.redis.lock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2020-04-28 20:04
 */
@Configuration
public class RedissonClientConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
//        config.setTransportMode(TransportMode.EPOLL);
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
