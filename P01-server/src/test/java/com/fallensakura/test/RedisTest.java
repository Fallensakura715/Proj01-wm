package com.fallensakura.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate);
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        ListOperations<Object, Object> listOperations = redisTemplate.opsForList();
        SetOperations<Object, Object> setOperations = redisTemplate.opsForSet();
        ZSetOperations<Object, Object> zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("name", "小明");
        String city = (String) redisTemplate.opsForValue().get("name");

        System.out.println(city);

        redisTemplate.opsForValue().set("code", "1234", 3, TimeUnit.SECONDS);
        redisTemplate.opsForValue().setIfAbsent("lock", "1");
    }

    @Test
    public void testHash() {
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();

        hashOperations.put("100", "name", "tom");
        hashOperations.put("100", "age", "20");
        String name = (String) hashOperations.get("100", "name");
        System.out.println(name);

        String age = (String) hashOperations.get("100", "age");
        System.out.println(age);

        hashOperations.delete("100", "age");
    }

    @Test
    public void testList() {
        ListOperations<Object, Object> listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll("mylist", "a", "b");
        listOperations.leftPush("mylist", "d");
        List<Object> mylist = listOperations.range("mylist", 0, -1);
        System.out.println(mylist);
        Long size = listOperations.size("mylist");
        System.out.println(size);
    }

    @Test
    public void testSet() {
        SetOperations<Object, Object> setOperations = redisTemplate.opsForSet();

        setOperations.add("set1", "a", "b");
        setOperations.add("set2", "c", "d", "a");

        Set<Object> members = setOperations.members("set1");
        System.out.println(members);

        Long size = setOperations.size("set1");
        System.out.println(size);

        Set<Object> intersect = setOperations.intersect("set1", "set2");
        System.out.println(intersect);

        Set<Object> union = setOperations.union("set1", "set2");
        System.out.println(union);

        setOperations.remove("set1", "a", "b");
    }

    @Test
    public void testZSet() {
        ZSetOperations<Object, Object> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset1", "a", 10);
        zSetOperations.add("zset1", "b", 9);
        zSetOperations.add("zset1", "c", 12);

        Set<Object> zset = zSetOperations.range("zset1", 0, -1);
        System.out.println(zset);

        zSetOperations.incrementScore("zset1", "a", 11);
        zSetOperations.remove("zset1", "b", "a");

        zset = zSetOperations.range("zset1", 0, -1);
        System.out.println(zset);
    }

    @Test
    public void testCommon() {
        Set<Object> keys = redisTemplate.keys("*");
        System.out.println(keys);

        Boolean name = redisTemplate.hasKey("name");
        Boolean set1 = redisTemplate.hasKey("set1");

        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
            redisTemplate.delete(key);
        }

        redisTemplate.delete("mylist");
    }
}
