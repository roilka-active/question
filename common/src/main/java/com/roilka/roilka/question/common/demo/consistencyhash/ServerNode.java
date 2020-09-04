package com.roilka.roilka.question.common.demo.consistencyhash;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author zhanghui1
 * @ClassName ServerNode
 * TODO
 * @date 2020/9/4 14:24
 **/
@Data
public class ServerNode {
    private String domain;
    private String ip;
    private Map<String,Object> data;

    public <T> void put(String key,T value){
        data.put(key, value);
    }
    public void remove(String key){
        data.remove(key);
    }
    public <T> T get(String key){
        return (T) data.get(key);
    }

    public ServerNode(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
        this.data = new TreeMap<>();
    }
}
