package com.roilka.roilka.question.api.controller.zhihu;

import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;
import com.roilka.roilka.question.domain.service.openapi.FastDataService;
import com.roilka.roilka.question.domain.service.zhihu.IUsersInfoService;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @ClassName OuterController
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/26 19:42
 **/
@Slf4j
@RestController(value = "知乎接口")
@RequestMapping(value = "/zhihu")
public class ZhiHuController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IUsersInfoService usersInfoService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/get-user-info")
    @ApiOperation("添加知乎粉丝排名到redis")
    public BizBaseResponse<JSONPObject> getUserInfo() {
        List<UsersInfo> result = usersInfoService.getList();
        log.info("结果为：{}", result.size());
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        /*for (UsersInfo usersInfo:result){

            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(usersInfo.getUserId(), Double.valueOf(usersInfo.getFollowers()));
            typedTupleSet.add(typedTuple);
        }*/
        /*for (int i = 0; i < 100; i++) {
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(result.get(i).getUserId(), Double.valueOf(result.get(i).getFollowers()));
            typedTupleSet.add(typedTuple);
        }*/
        long start = System.currentTimeMillis();
        List list = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) {
                /*for (UsersInfo usersInfo:result){

                    ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(usersInfo.getUserId(), Double.valueOf(usersInfo.getFollowers()));
                    typedTupleSet.add(typedTuple);
                }*/
                for (int i = 0; i < 200; i++) {
                    ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(result.get(i).getUserId(), Double.valueOf(result.get(i).getFollowers()));
                    typedTupleSet.add(typedTuple);
                }
                log.info("开始往有序集合内出入元素，size={}", typedTupleSet.size());
                stringRedisTemplate.opsForZSet().add("zhihu:follower:rankings", typedTupleSet);
                return null;
            }

        });
        log.info("store redis expand time {} ms", System.currentTimeMillis() - start);
        // 往有序集合插入元素
        //stringRedisTemplate.opsForZSet().add("zhihu:follower:rankings", typedTupleSet);
        long a = stringRedisTemplate.opsForZSet().reverseRank("zhihu:follower:rankings", "366107");
        log.info("用户366107的，排名是={} ", a);
        return new BizBaseResponse<JSONPObject>();
    }

    @GetMapping("/flush-area")
    @ApiOperation("刷新区域缓存")
    public BizBaseResponse<Boolean> flushArea() throws URISyntaxException {
        URI uri = new URI("https://api.jisuapi.com/area/province");
        Object responseEntity = restTemplate.getForObject("https://api.jisuapi.com/area/province", Object.class);
        log.info("返回结果{}", responseEntity);
        return  new BizBaseResponse<>(true);
    }


}
