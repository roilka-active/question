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
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
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

    @GetMapping(value = "/getUserInfo")
    @ApiOperation("添加知乎粉丝排名到redis")
    public BizBaseResponse<JSONPObject> getUserInfo(){
        List<UsersInfo> result= usersInfoService.getList();
        log.info("结果为：{}",result.size());
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        /*for (UsersInfo usersInfo:result){

            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(usersInfo.getUserId(), Double.valueOf(usersInfo.getFollowers()));
            typedTupleSet.add(typedTuple);
        }*/
        for (int i = 0; i < 100; i++) {
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(result.get(i).getUserId(), Double.valueOf(result.get(i).getFollowers()));
            typedTupleSet.add(typedTuple);
        }
        // 往有序集合插入元素
        stringRedisTemplate.opsForZSet().add("zhihu:follower:rankings", typedTupleSet);
        long a = stringRedisTemplate.opsForZSet().reverseRank("zhihu:follower:rankings", "366107");
        log.info("用户366107的，排名是={} ", a);
        return new BizBaseResponse<JSONPObject>();
    }




}
