package com.roilka.roilka.question.api.controller.open;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roilka.roilka.question.common.constant.RedisFix;
import com.roilka.roilka.question.common.utils.CollectionUtil;
import com.roilka.roilka.question.common.utils.HttpClientUtils;
import com.roilka.roilka.question.common.utils.JsonConvertUtils;
import com.roilka.roilka.question.common.utils.RedisUtils;
import com.roilka.roilka.question.dal.entity.HistoryToday;
import com.roilka.roilka.question.domain.service.zhihu.impl.HistoryTodayService;
import com.roilka.roilka.question.facade.request.base.BizBaseRequest;
import com.roilka.roilka.question.facade.request.open.GetHistoryToDayPageRequest;
import com.roilka.roilka.question.facade.response.BasePageResponse;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import com.roilka.roilka.question.facade.response.open.HistoryToDayResponse;
import com.roilka.roilka.question.facade.response.zhihu.GetHistoryToDayResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName AcecdoteController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/26 19:27
 **/
@Slf4j
@RestController()
@RequestMapping("/area")
@Api(tags = "省市区域管理接口")
public class AcecdoteController {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    private HistoryTodayService historyTodayService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "刷新区域信息到redis")
    @GetMapping("/refreahAreaToRedis")
    public BizBaseResponse<Boolean> refreahAreaToRedis() {
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
        ArrayList l = new ArrayList<String>();
        l.add("s");
        Vector vector = new Vector();
        vector.add("a");
        return new BizBaseResponse<>(true);
    }


}
