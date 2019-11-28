package com.roilka.roilka.question.api.controller.open;

import com.alibaba.fastjson.JSONPObject;
import com.roilka.roilka.question.domain.service.openapi.FastDataService;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @ClassName OuterController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/26 19:42
 **/
@rest
public class OuterController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FastDataService fastDataService;

    public BizBaseResponse<JSONPObject> getDream(){
        return new BizBaseResponse<>();
    }
}
