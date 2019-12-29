package com.roilka.roilka.question.api.controller.open;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.roilka.roilka.question.common.constant.RedisFix;
import com.roilka.roilka.question.common.utils.CollectionUtil;
import com.roilka.roilka.question.common.utils.HttpClientUtils;
import com.roilka.roilka.question.common.utils.JsonConvertUtils;
import com.roilka.roilka.question.common.utils.RedisUtils;
import com.roilka.roilka.question.dal.entity.HistoryToday;
import com.roilka.roilka.question.domain.service.zhihu.impl.HistoryTodayService;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import com.roilka.roilka.question.facade.response.zhihu.GetHistoryToDayResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName AcecdoteController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/26 19:27
 **/
@Slf4j
@RestController("/acecdote")
@Api(tags = "奇闻异事管理接口")
public class AcecdoteController {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    private HistoryTodayService historyTodayService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "刷新历史的今天信息")
    @GetMapping("/refreahHistoryToDay")
    public BizBaseResponse<Boolean> refreahHistoryToDay() {
        GetHistoryToDayResponse.ResultBean resultBean = new GetHistoryToDayResponse.ResultBean();
        Integer defaultRows = 500;
        String url = "http://api.avatardata.cn/HistoryToday/LookUp";
        Map<String, Object> body = new HashMap<>();
        GetHistoryToDayResponse response = null;
        for (int yue = 1; yue <= 12; yue++) {
            for (int ri = 1; ri <= 31; ri++) {
                for (int type = 1; type <= 2; type++) {
                    Integer page = 1;
                    do {
                        body.put("key", "053c1b53539848629cdb3edd7e40bf79");
                        body.put("yue", yue);
                        body.put("ri", ri);
                        body.put("type", type);
                        body.put("page", page);
                        body.put("rows", defaultRows);
                        response = JSONObject.toJavaObject(httpClientUtils.doGetWithParam(url, body), GetHistoryToDayResponse.class);
                        if (CollectionUtil.isNullOrEmpty(response.getResult())) {
                            continue;
                        }
                        redisUtils.redisHashSet(RedisFix.HISTORYTODAY, yue + "-" + ri + "_" + type, JsonConvertUtils.objectToJson(response.getResult()));
                        page++;
                    } while (response.getResult().size() == 500);
                }
            }
        }

        log.info("test:{}", JSONObject.toJSON(resultBean));

        return new BizBaseResponse<>(true);
    }

    @ApiOperation(value = "存储历史的今天信息")
    @GetMapping("/storeHistoryToDay")
    public BizBaseResponse<Boolean> storeHistoryToDay() {
        Set<String> set = redisUtils.redisHashKeys(RedisFix.HISTORYTODAY);
        HistoryToday historyToday;
        Set<HistoryToday> setM = new HashSet<>();
        Map<String, Object> map;
        int count = 0;
        for (String str : set) {
            JSONArray arr = redisUtils.redisHashGetWithInstance(RedisFix.HISTORYTODAY, str, JSONArray.class);
            for (Object obj : arr) {
//                historyToday = JSONObject.toJavaObject((JSONObject)obj,HistoryToday.class);
                map = (HashMap) obj;
                historyToday = new HistoryToday();
                historyToday.setDay((int) map.get("day"));
                historyToday.setMonth((int) map.get("month"));
                historyToday.setType((int) map.get("type"));
                historyToday.setYear((int) map.get("year"));
                historyToday.setTitle((String) map.get("title"));
                setM.add(historyToday);
                count++;
                log.info("数据装配成功，count={},historyToday={}", count, historyToday);
            }
        }
        log.info("start to store,size = {}", setM.size());
        historyTodayService.saveBatch(setM);


        return new BizBaseResponse<>(true);
    }
}
