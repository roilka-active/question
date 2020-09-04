package com.roilka.roilka.question.common.demo.consistencyhash;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * @author zhanghui1
 * @ClassName ConsistencyHashCluster
 * TODO
 * @date 2020/9/4 15:31
 **/
public class ConsistencyHashCluster extends Cluster {

    private SortedMap<Integer, ServerNode> virNodes = new TreeMap();
    private static final int VIR_NODE_COUNT = 512;
    private static final String SPLIT = "#";
    public ConsistencyHashCluster() {
        super();
    }

    @Override
    public void addNode(ServerNode node) {
        this.serverNodes.add(node);
        IntStream.range(0,VIR_NODE_COUNT)
                .forEach(index -> {
                    int hash = Objects.hashCode(node.getIp()+ SPLIT+ index);
                    int i = hash % VIR_NODE_COUNT;
                    virNodes.put(i, node);
                });
    }

    @Override
    public void removeNode(ServerNode node) {
        serverNodes.removeIf(o -> o.getIp().equals(node.getIp()));
        IntStream.range(0,VIR_NODE_COUNT)
                .forEach(index -> {
                    int hash = Objects.hashCode(node.getIp()+ SPLIT+ index);
                    int i = hash % VIR_NODE_COUNT;
                    virNodes.remove(i);
                });

    }

    @Override
    public ServerNode get(String key) {
        int hashCode = Math.abs(Objects.hashCode(key));
        int i = hashCode % VIR_NODE_COUNT;
        SortedMap<Integer, ServerNode> sortedMap = i >= virNodes.lastKey() ? virNodes.tailMap(0) : virNodes.tailMap(i);
        if (sortedMap.isEmpty()) {
            return null;
        }
        return sortedMap.get(sortedMap.firstKey());
    }
}
