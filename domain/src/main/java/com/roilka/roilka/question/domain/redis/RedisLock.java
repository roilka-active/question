package com.roilka.roilka.question.domain.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@Service
public class RedisLock {

    //锁键
    private String lock_key = "redis_lock";

    //锁过期时间
    private long internalLockLeaseTime = 30000;

    //获取锁超时时间
    private long timeout = 999999;

    //SET命令的参数

    private JedisPool jedisPool;

    /**
     *  加锁
     * @param id
     * @return
     */
    public boolean lock(String id){
        Jedis jedis = jedisPool.getResource();
        long start = System.currentTimeMillis();

        return true;
    }


}
