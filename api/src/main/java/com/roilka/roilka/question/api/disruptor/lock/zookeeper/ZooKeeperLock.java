package com.roilka.roilka.question.api.disruptor.lock.zookeeper;

/**
 * @author zhanghui1
 * @ClassName ZooKeeperLock
 * TODO
 * @date 2020/9/10 11:39
 **/
public interface ZooKeeperLock {
    /**

     * 尝试获取锁

     *

     * @param guidNodeName 用于加锁的唯一节点名

     * @param clientGuid 用于唯一标识当前客户端的ID

     * @return

     */

    boolean lock(String guidNodeName, String clientGuid);



    /**

     * 释放锁

     *

     * @param guidNodeName 用于加锁的唯一节点名

     * @param clientGuid 用于唯一标识当前客户端的ID

     * @return

     */

    boolean release(String guidNodeName, String clientGuid);



    /**

     * 锁是否已经存在

     *

     * @param guidNodeName 用于加锁的唯一节点名

     * @return

     */

    boolean exists(String guidNodeName);
}
