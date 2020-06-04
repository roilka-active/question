package com.roilka.roilka.question.common.concurrent.atomic;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Major
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/6/4 10:18
 **/
@Data
public class Major implements Serializable {
    private String Name;
    private int Type;
}
