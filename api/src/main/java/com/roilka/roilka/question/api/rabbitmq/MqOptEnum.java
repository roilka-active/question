package com.roilka.roilka.question.api.rabbitmq;

/**
 *  mq消息操作操作类型
 */
public enum MqOptEnum {
    LOG(0, "日志");

    MqOptEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;

    }

    Integer code;
    String desc;
}
