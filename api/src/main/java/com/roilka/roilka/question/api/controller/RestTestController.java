package com.roilka.roilka.question.api.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RestTestController
 * @Description 接口测试控制器
 * @Author zhanghui1
 * @Date 2020/4/15 17:13
 **/
@Slf4j
@RequestMapping("test")
@RestController
public class RestTestController {

    @GetMapping("/getName")
    @ApiOperation("测试获取名字")
    public String getName(){
      return "hello";
    }

}
