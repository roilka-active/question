package com.roilka.roilka.question.common.demo.consistencyhash;

import java.util.stream.IntStream;

/**
 * @author zhanghui1
 * @ClassName Hash_MainTest
 * TODO
 * @date 2020/9/4 14:57
 **/
public class Hash_MainTest {
    public static void main(String[] args) {
        int dataCount = 100000;
        String preKey = "hash_";
        Cluster cluster = new ConsistencyHashCluster();
        cluster.addNode(new ServerNode("c1.yywang.info", "192.168.0.1"));
        cluster.addNode(new ServerNode("c2.yywang.info", "192.168.0.2"));
        cluster.addNode(new ServerNode("c3.yywang.info", "192.168.0.3"));
        cluster.addNode(new ServerNode("c4.yywang.info", "192.168.0.4"));
        IntStream.range(0, dataCount).forEach(index -> {
            ServerNode serverNode = cluster.get(preKey + index);
            serverNode.put(preKey + index, "Test Data");
        });
        System.out.println("数据分布情况：");
        cluster.serverNodes.forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量：" + node.getData().size());
        });

        //缓存命中率
        long hitCount = IntStream.range(0, dataCount)
                .filter(index -> cluster.get(preKey + index).get(preKey + index) != null)
                .count();
        System.out.println("缓存命中率："+hitCount * 1f/dataCount);

        //增加一个节点
        cluster.addNode(new ServerNode("c5.yywang.info", "192.168.0.5"));

        System.out.println("增加一个节点后，数据分布情况：");
        cluster.serverNodes.forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量：" + node.getData().size());
        });

        //缓存命中率
        hitCount = IntStream.range(0, dataCount)
                .filter(index -> cluster.get(preKey + index).get(preKey + index) != null)
                .count();
        System.out.println("缓存命中率："+hitCount * 1f/dataCount);

        //删除一个节点
        cluster.removeNode(new ServerNode("c5.yywang.info", "192.168.0.5"));
        cluster.removeNode(new ServerNode("c4.yywang.info", "192.168.0.4"));
        System.out.println("删除一个节点后，数据分布情况：");
        cluster.serverNodes.forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量：" + node.getData().size());
        });

        //缓存命中率
        hitCount = IntStream.range(0, dataCount)
                .filter(index -> cluster.get(preKey + index).get(preKey + index) != null)
                .count();
        System.out.println("缓存命中率："+hitCount * 1f/dataCount);
    }
}
