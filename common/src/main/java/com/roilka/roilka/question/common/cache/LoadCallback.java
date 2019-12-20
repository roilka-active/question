package com.roilka.roilka.question.common.cache;

/**
 * @ClassName LoadCallback
 * @Description 用来返回加载的数据
 * @Author changyou
 * @Date 2019/12/20 14:50
 **/
public interface LoadCallback<T> {

    T load();
}
