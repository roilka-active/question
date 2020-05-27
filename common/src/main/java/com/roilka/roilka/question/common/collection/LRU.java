package com.roilka.roilka.question.common.collection;
/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author zhanghui1
 * @date 2020/5/2715:25
 */

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName LRU
 * @Description 最近最少使用算法简单实现
 * @Author 75six
 * @Date 2020/5/27 15:25
 **/
public class LRU<K, V> {
    private LinkedHashMap<K,V> map;
    private int cacheSize;

    public LRU(int cacheSize){
        this.cacheSize = cacheSize;
        map = new LinkedHashMap<K,V>(16,0.75f,true){

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest){
                if (cacheSize + 1 == map.size()){
                    return true;
                }else {
                    return false;
                }
            }
        };
    }

    public synchronized V get(K key){
        return map.get(key);
    }
    public synchronized void put(K key,V value){
        map.put(key, value);
    }
    public synchronized void clear(){
        map.clear();
    }
    public synchronized int usedSize(){
        return map.size();
    }
    public synchronized int freeSize(){
        return cacheSize - map.size();
    }
    public void print(){
        map.forEach((k,v) -> {
            System.out.print(v+"- -");
        });
        System.out.println();
    }
}
