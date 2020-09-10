package com.roilka.roilka.question.api.disruptor.lock.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhanghui1
 * @ClassName ZookeeperBase
 * TODO
 * @date 2020/9/10 11:43
 **/
public class ZookeeperBase implements Watcher {

    Logger logger = null;

    // 等待连接建立成功的信号
    private CountDownLatch countDownLatch =  new CountDownLatch(1);
    // zookeeper 客户端
    private ZooKeeper zooKeeper = null;
    // 避免重复节点
    static Integer rootNodeInital = Integer.valueOf(1);

    public ZookeeperBase(String address) {
        logger = LoggerFactory.getLogger(getClass());

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.EventType.None.equals(watchedEvent.getType())) {
            // 连接状态发生变化
            if (Event.KeeperState.SyncConnected.equals(watchedEvent.getState())) {
                //连接建立成功
                countDownLatch.countDown();
            }
        }
    }
}
