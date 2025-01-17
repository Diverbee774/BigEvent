package org.diverbee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest //会先初始化spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet(){
        //往redis中存一个键值对
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        operations.set("username","zhangsan");

        operations.set("id","1",15, TimeUnit.SECONDS);
    }

    @Test void testGet(){
        //从redis获取键值对
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        System.out.println(operations.get("username"));
        System.out.println(operations.get("id"));
    }
}
