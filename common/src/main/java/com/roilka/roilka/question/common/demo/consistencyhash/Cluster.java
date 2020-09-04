package com.roilka.roilka.question.common.demo.consistencyhash;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghui1
 * @ClassName Cluster
 * TODO
 * @date 2020/9/4 14:38
 **/
public abstract class Cluster {
    protected List<ServerNode> serverNodes;

    public Cluster() {
        this.serverNodes = new ArrayList<>();
    }

    public abstract void addNode(ServerNode node);

    public abstract void removeNode(ServerNode node);

    public abstract ServerNode get(String key);
}
