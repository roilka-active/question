package com.roilka.roilka.question.api.controller.open;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName OuterController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/26 19:42
 **/
public class OuterController {

    @Autowired
    private RestTemplate restTemplate;

    public BizBaseResponse<JSONPObject> getDream(){

        return new BizBaseResponse<>();
    }
}
