package com.roilka.roilka.question.api.redis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RedisController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/20 20:28
 **/
@Api(tags = "Redis 控制器")
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired

    private RedisTemplate redisTemplate = null;

    @Autowired
    private StringRedisTemplate stringRedisTemplate = null;

    @ApiOperation(value = "redis 测试String和Hash")
    @GetMapping("/testStringAndHash")
    @ResponseBody
    public Map<String, Object> testStringAndHash() {
        redisTemplate.opsForValue().set("key1", "Value1");
        //注意这里使用的是JDK的序列化器，所以Redis 保存时不是整数，不能运算
        redisTemplate.opsForValue().set("int_key", "1");
        stringRedisTemplate.opsForValue().set("int", "1");
        //使用运算
        stringRedisTemplate.opsForValue().increment("int", 1);

        //获取Jedis 底层连接
        Jedis jedis = (Jedis) stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        jedis.decr("int");
        Map<String, String> hash = new HashMap<>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");
        hash.put("field3", "value3");
        //存入一个散列数据类型
        stringRedisTemplate.opsForHash().putAll("hash", hash);
        // 新增一个字段

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    @ApiOperation(value = "redis 测试List")
    @GetMapping("/testList")
    @ResponseBody
    public Map<String, Object> testList() {
        // 插入两个列表，注意他们在链表中的顺序
        stringRedisTemplate.opsForList().leftPushAll("List:list1", "V2","V4","V6","V8","V10");
        stringRedisTemplate.opsForList().leftPushAll("List:list2", "V1","V2","V3","V4","V5");
        // 绑定list2 链表操作
        BoundListOperations listOps = stringRedisTemplate.boundListOps("List");
       // 从右边弹出一个成员
        Object r1 = listOps.rightPop();
        // 获取定位元素，Redis从0开始计算，这里值为V2
        Object r2 = listOps.index(1);
        //从左边插入
        listOps.leftPush("V0");
        //求链表长度
        Long size = listOps.size();
        // 求链表下标区间成员，整个链表下标范围为 0 到 size - 1,这里不去最后一个元素
        List elements = listOps.range(0, size - 2);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }


    @ApiOperation(value = "redis 测试PipeLine")
    @GetMapping("/testPipeLine")
    @ResponseBody
    public Map<String, Object> testPipeLine() {
        Long start = System.currentTimeMillis();
        List list = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) {
                for (int i = 0; i <= 5000; i++) {
                    operations.opsForValue().set("pipeline:pipeline_" + i, "value_" + i);
                    String value = (String) operations.opsForValue().get("pipeline_" + i);
                    if (i == 5000) {
                        System.out.println("命令指示进入队列，所以值为空【" + value + "】");
                    }
                }
                return null;
            }

        });
        Long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - start)+"毫秒");

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }
}
