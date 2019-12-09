package com.roilka.roilka.question.domain.serviceimpl.openapi;

import com.roilka.roilka.question.domain.service.openapi.FastDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName FastDataServiceImpl
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/27 17:37
 **/

@Service
public class FastDataServiceImpl implements FastDataService {


    @Override
    public Object getAreaInfo() {

        return null;
    }
}
