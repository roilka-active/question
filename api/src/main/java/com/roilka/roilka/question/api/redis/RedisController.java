package com.roilka.roilka.question.api.redis;

import com.roilka.roilka.question.api.testbeaninit.BussinessPerson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @ClassName RedisController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/20 20:28
 **/
@Slf4j
@Api(tags = "Redis 控制器")
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired

    private RedisTemplate redisTemplate = null;

    @Autowired
    private BussinessPerson bussinessPerson;

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
        bussinessPerson.service();
        log.info("MDC- url ={}", MDC.get("url"));
        // 插入两个列表，注意他们在链表中的顺序
        stringRedisTemplate.opsForList().leftPushAll("List:list1", "V2", "V4", "V6", "V8", "V10");
        stringRedisTemplate.opsForList().leftPushAll("List:list2", "V1", "V2", "V3", "V4", "V5");
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

    @ApiOperation(value = "redis 测试Zset")
    @GetMapping("/testZset")
    @ResponseBody
    public Map<String, Object> testZset() {
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        for (int i = 0; i <= 9; i++) {
            // 分数
            double score = i * 0.1;
            // 创建一个 TypedTuple对象，存入值和分数
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("value" + i, score);
            typedTupleSet.add(typedTuple);
        }
        // 往有序集合插入元素
        stringRedisTemplate.opsForZSet().add("zset:zset1", typedTupleSet);
        // 绑定 zset1 有序集合操作
        BoundZSetOperations<String, String> zsetOps = stringRedisTemplate.boundZSetOps("zset:zset1");
        // 增加一个元素
        zsetOps.add("value10", 0.26);
        Set<String> setRange = zsetOps.range(1, 6);
        // 按分数排序获取有序集合
        Set<String> setScore = zsetOps.rangeByScore(0.2, 0.6);
        // 定义值的范围
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        // 大于 value3
        range.gt("value3");
        // 大于等于 value3
        // range.gte("value3");
        // 小于 value8
        // range.lt("value8");
        // 小于等于 value8
        range.lte("value8");
        // 按值排序，请注意这个排序是按字符串排序
        Set<String> setLex = zsetOps.rangeByLex(range);
        // 删除元素
        zsetOps.remove("value9","value2");
        // 求分数
        Double score = zsetOps.score("value8");
        // 在下标区间下，按分数排序，同时返回value 和score
        Set<ZSetOperations.TypedTuple<String>> rangeSet = zsetOps.rangeWithScores(1,6);
        // 在分数区间下，按分数排序，同时返回value 和score
        Set<ZSetOperations.TypedTuple<String>> scoreSet = zsetOps.rangeByScoreWithScores(0.2,0.6);

        //
        // 按分数排序
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    @ApiOperation(value = "redis 测试Multi")
    @GetMapping("/testMulti")
    @ResponseBody
    public Map<String, Object> testMulti() {

        redisTemplate.opsForValue().set("key1", "value1");
        List list = (List) redisTemplate.executePipelined(new SessionCallback<Object>() {
           @Override
           public  Object execute(RedisOperations operations){
               // 设置要监控 key1
               operations.watch("key1");
               // 开启事务，在exec命令执行前，全部都只进入队列
               operations.multi();
               operations.opsForValue().set("key2", "value2");
               //operations.opsForValue().increment("key1",1);
               // 获取值将为null，因为redis 只是把命令放入队列
               Object value2 = operations.opsForValue().get("key2");
               log.info("命令在队列，所以value为null，{}", value2);
               operations.opsForValue().set("key3", "value3");
               // 获取值将为null，因为redis 只是把命令放入队列
               Object value3 = operations.opsForValue().get("key3");
               log.info("命令在队列，所以value为null，{}", value3);
               return operations.exec();

            }
        });
        String ss =new String("1");
        ss.hashCode();
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
        System.out.println("耗时：" + (end - start) + "毫秒");

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }
}
