package com.roilka.roilka.question.common.javabase.concurrency.lock.zookeeper;

/**
 * @ClassName ZkLock
 * @Description TODO
 * @Author 75six
 * @Date 2020/4/24 9:53
 **/
public interface ZkLock {
    Boolean lock() throws Exception;

    Boolean unlock();
}
