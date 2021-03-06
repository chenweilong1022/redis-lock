package com.redis.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LockApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockApplication.class, args);
    }

    @Autowired
    private RedissonClient redissonClient;



    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/deductInventory")
    public String deductInventory() {

        RLock stock1 = redissonClient.getLock("stock");

        try {


            stock1.lock();
                Integer stock = (Integer) redisTemplate.opsForValue().get("stock");

                if (stock <= 0) {
                    return "error";
                }

                stock = stock - 1;
                redisTemplate.opsForValue().set("stock",stock);

                System.out.println("剩余库存 : " + stock);
            }finally {
            stock1.unlock();
            }

        return "end : 8081";
    }

}
