package com.roilka.roilka.question.api.controller.zhihu;

import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;
import com.roilka.roilka.question.domain.service.openapi.FastDataService;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.util.List;


/**
 * @ClassName OuterController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/26 19:42
 **/
@Slf4j
@Controller(value = "知乎接口")
@RequestMapping(value = "/zhihu")
public class ZhiHuController {


    @Autowired
    @Qualifier("followerService")
    private IService followerService;

    @GetMapping(value = "/getUserInfo")
    public BizBaseResponse<JSONPObject> getUserInfo(){
        List<UsersInfo> result= followerService.list();
        log.info("结果为：{}",result.size());
        return new BizBaseResponse<JSONPObject>();
    }




}
