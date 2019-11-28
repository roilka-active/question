package com.roilka.roilka.question.facade.request.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BizBaseRequest
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/26 19:49
 **/
@Data
public class BizBaseRequest<T> implements Serializable {

    private String channel;

    private String version;

    private  T postData;

}
