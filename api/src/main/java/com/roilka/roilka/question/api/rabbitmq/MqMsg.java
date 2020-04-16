package com.roilka.roilka.question.api.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName MqMsg
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/16 14:38
 **/
@Data
public class MqMsg<T> implements Serializable {
    /**
     * 消息唯一id
     */
    private String            messageId;
    /**
     * 操作类型
     */
    private MqOptEnum         opt;
    /**
     * 附带数据
     */
    private T                 data;
    /**
     * 数据版本号
     */
    private long              version          = 1;
}
