package com.roilka.roilka.question.api.controller.zhihu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roilka.roilka.question.common.base.BizRestException;
import com.roilka.roilka.question.common.constant.RedisFix;
import com.roilka.roilka.question.common.enums.BizResponseCodeEnum;
import com.roilka.roilka.question.common.utils.CollectionUtil;
import com.roilka.roilka.question.common.utils.HttpClientUtils;
import com.roilka.roilka.question.common.utils.RedisUtils;
import com.roilka.roilka.question.dal.entity.zhihu.Area;
import com.roilka.roilka.question.dal.entity.zhihu.Follower;
import com.roilka.roilka.question.dal.entity.zhihu.Users;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;
import com.roilka.roilka.question.domain.service.openapi.FastDataService;
import com.roilka.roilka.question.domain.service.zhihu.AreaService;
import com.roilka.roilka.question.domain.service.zhihu.IUsersInfoService;
import com.roilka.roilka.question.domain.service.zhihu.impl.FollowerService;
import com.roilka.roilka.question.domain.service.zhihu.impl.UsersService;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import com.roilka.roilka.question.facade.response.zhihu.GetAreaDataResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;


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


    private static final String APPID = "5e5d2ee08f8885b5";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IUsersInfoService usersInfoService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AreaService areaService;
    @Autowired
    private FollowerService followerService;

    @Autowired
    private UsersService usersService;
    @Autowired
    private HttpClientUtils httpClientUtils;

    @GetMapping(value = "/testMapper/{id}")
    @ApiOperation("测试Mapper")
    public void testMapper(@PathVariable("id") Integer id){
        Users result = usersService.getById(id);
        log.info("结果：{}", result);
    }
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
        if (result.size()>0){
            return null;
        }
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
        String url = "https://api.jisuapi.com/area/province";
        //Object responseEntity = restTemplate.getForObject("https://api.jisuapi.com/area/province", Object.class);
        JSONObject a = httpClientUtils.doGetBase(url + "?appkey=" + APPID, null,"utf-8");
        GetAreaDataResponse response = JSONObject.toJavaObject(a, GetAreaDataResponse.class);
        List<GetAreaDataResponse.ResultBean> resultBeans = response.getResult();

        Map<String, String> map = resultBeans.stream().collect(Collectors.toMap(record -> String.valueOf(record.getId()), record -> record.toString()));
        log.info("result is {}", a);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        /*RestTemplate template = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<Object> response = template.exchange(url, HttpMethod.GET, requestEntity, Object.class);
        JSONObject sttr = (JSONObject) response.getBody();
        log.info("返回结果{}", sttr);*/
        //stringRedisTemplate.opsForHash().putAll(RedisFix.AREA, map);
        redisUtils.redisHashPutAll(RedisFix.AREA, map);
        return new BizBaseResponse<>(true);
    }

    @GetMapping("/flush-area-city")
    @ApiOperation("刷新区域缓存")
    public BizBaseResponse<Boolean> flushAreaCity() throws URISyntaxException {
        String url = "https://api.jisuapi.com/area/city";
        // 获取所有的省id集合
        Set<String> provinceSet = redisUtils.redisHashKeys(RedisFix.AREA);
        if (CollectionUtil.isNullOrEmpty(provinceSet)) {
            return new BizBaseResponse<>(true);
        }
        for (String str : provinceSet) {
            Long size = redisUtils.redisHashSize(RedisFix.AREA + str);
            if (size > 0) {
                continue;
            }
            List<GetAreaDataResponse.ResultBean> resultBeanList = new ArrayList<>();
            //Object responseEntity = restTemplate.getForObject("https://api.jisuapi.com/area/province", Object.class);
            JSONObject a = httpClientUtils.doGetBase(url + "?parentid=" + Integer.parseInt(str) + "&appkey=" + APPID, null,"utf-8");

            GetAreaDataResponse response = JSONObject.toJavaObject(a, GetAreaDataResponse.class);
            if (response.getStatus() == 203) {
                continue;
            }
            if (response.getStatus() != 0) {
                throw new BizRestException(BizResponseCodeEnum.PARAM_ERROR, response.getMsg());
            }
            List<GetAreaDataResponse.ResultBean> resultBeans = response.getResult();

            Map<String, String> map = resultBeans.stream().collect(Collectors.toMap(record -> String.valueOf(record.getId()), record -> record.toString()));
            redisUtils.redisHashPutAll(RedisFix.AREA + str, map);
        }
        return new BizBaseResponse<>(true);
    }

    @GetMapping("/flush-area-town")
    @ApiOperation("刷新区域缓存")
    public BizBaseResponse<Boolean> flushAreaTown() throws URISyntaxException {
        String url = "https://api.jisuapi.com/area/town";
        // 获取所有的省id集合
        Set<String> provinceSet = redisUtils.redisHashKeys(RedisFix.AREA);
        if (CollectionUtil.isNullOrEmpty(provinceSet)) {
            return new BizBaseResponse<>(true);
        }
        for (String str : provinceSet) {
            Long size = redisUtils.redisHashSize(RedisFix.AREA + str);
            if (size == 0) {
                continue;
            }
            Set<String> townSet = redisUtils.redisHashKeys(RedisFix.AREA + str);

            for (String town : townSet) {
                List<GetAreaDataResponse.ResultBean> resultBeanList = new ArrayList<>();
                //Object responseEntity = restTemplate.getForObject("https://api.jisuapi.com/area/province", Object.class);
                JSONObject a = httpClientUtils.doGetBase(url + "?parentid=" + Integer.parseInt(str) + "&appkey=" + APPID, null,"utf-8");

                GetAreaDataResponse response = JSONObject.toJavaObject(a, GetAreaDataResponse.class);
                if (response.getStatus() == 203) {
                    continue;
                }
                if (response.getStatus() != 0) {
                    throw new BizRestException(BizResponseCodeEnum.PARAM_ERROR, response.getMsg());
                }
                List<GetAreaDataResponse.ResultBean> resultBeans = response.getResult();

                Map<String, String> map = resultBeans.stream().collect(Collectors.toMap(record -> String.valueOf(record.getId()), record -> record.toString()));
                redisUtils.redisHashPutAll(RedisFix.AREA + str + ":" + town, map);
            }

        }
        return new BizBaseResponse<>(true);
    }

    @ApiOperation("存储区域信息")
    @GetMapping("store-area")
    public BizBaseResponse<Boolean> storeArea() {
        List<Area> list = new ArrayList<>();
        // 获取省集合
        Set<String> provinceSet = redisUtils.redisHashKeys(RedisFix.AREA);

        Map<String, Area> provinceMap = redisUtils.redisHashGetAllWithInstance(RedisFix.AREA, Area.class);
        list.addAll(provinceMap.values());

        for (String province : provinceSet) {
            Long count = redisUtils.redisHashSize(RedisFix.AREA + province);
            if (count == 0) {
                continue;
            }
            // 存入所有省份
            buildArea(RedisFix.AREA, province, list);
            Set<String> citySet = redisUtils.redisHashKeys(RedisFix.AREA + province);

            for (String city : citySet) {

                count = redisUtils.redisHashSize(RedisFix.AREA + province + ":" + city);
                if (count == 0) {
                    continue;
                }
                // 存入城市
                buildArea(RedisFix.AREA + province, city, list);
                Set<String> townSet = redisUtils.redisHashKeys(RedisFix.AREA + province + ":" + city);
                for (String town : townSet) {
                    // 存入乡镇
                    buildArea(RedisFix.AREA + province + ":" + city, town, list);
                }
            }
        }
        list.stream().forEach(record -> {
            areaService.addArea(record);
        });
        return new BizBaseResponse<>(true);
    }

    private void buildArea(String key, String hashKey, List<Area> list) {
        GetAreaDataResponse.ResultBean bean = redisUtils.redisHashGetWithInstance(key, hashKey, GetAreaDataResponse.ResultBean.class);

        Area area = new Area();
        BeanUtils.copyProperties(bean, area);
        list.add(area);
    }
}
