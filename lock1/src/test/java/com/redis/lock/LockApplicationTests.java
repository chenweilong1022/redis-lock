package com.redis.lock;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class LockApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        Object stock = redisTemplate.opsForValue().get("stock");
        System.out.println(stock);


        redisTemplate.opsForValue().set("stock",1000);

        Object stock1 = redisTemplate.opsForValue().get("stock");
        System.out.println(stock1);

    }




    private int MAXSIZE = 100;

    CountDownLatch countDownLatch = new CountDownLatch(MAXSIZE);


    @Test
    void stockTest() {

        for (int i = 0; i <= MAXSIZE; i++) {
            Thread thread = new Thread( () -> {
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                    String post = HttpUtil.get("http://localhost/deductInventory", new HashMap<>());

                    System.out.println(post);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });

            thread.start();
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
