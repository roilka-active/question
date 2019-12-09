package com.roilka.roilka.question.api.controller.open;

import com.alibaba.fastjson.JSONPObject;
import com.roilka.roilka.question.domain.service.openapi.FastDataService;
import com.roilka.roilka.question.facade.response.BizBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;


/**
 * @ClassName OuterController
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/26 19:42
 **/

@Controller(value = "外部接口")
public class OuterController {

    @Autowired
    private FastDataService fastDataService;

    public BizBaseResponse<JSONPObject> getDream(){
        return new BizBaseResponse<JSONPObject>();
    }

     public static void getAnnotation(){
        Field[] fields = (new OuterController()).getClass().getFields();
        for (Field field : fields){
            if (field != null && field.isAnnotationPresent(Controller.class)){
                Controller controller = field.getAnnotation(Controller.class);
                System.out.println(controller.value());
            };
        }
    }


}
