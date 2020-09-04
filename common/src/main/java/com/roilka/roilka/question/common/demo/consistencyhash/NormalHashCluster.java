package com.roilka.roilka.question.common.demo.consistencyhash;

import java.util.Objects;

/**
 * @author zhanghui1
 * @ClassName NormalHashCluster
 * TODO
 * @date 2020/9/4 14:41
 **/
public class NormalHashCluster extends Cluster {
    public NormalHashCluster() {
        super();
    }

    @Override
    public void addNode(ServerNode node) {
        this.serverNodes.add(node);
    }

    @Override
    public void removeNode(ServerNode node) {
        this.serverNodes.removeIf(o ->
                o.getDomain().equals(node.getDomain()) ||
                        o.getIp().equals(node.getIp())
        );
    }

    @Override
    public ServerNode get(String key) {
        int hashCode = Objects.hashCode(key);
        int i = Math.abs(hashCode % serverNodes.size());
        return serverNodes.get(i);
    }
}
