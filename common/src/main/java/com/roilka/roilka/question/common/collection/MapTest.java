package com.roilka.roilka.question.common.collection;/**
 * Package: com.roilka.roilka.question.common.collection
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/2/26 17:41
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import com.roilka.roilka.question.common.base.BizRestException;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhanghui
 * @description map性能测试
 * @date 2020/2/26
 */
public class MapTest {

    public static void main(String[] args) {
        Map<Integer,String> sMap = testTreeMap();
        for (Map.Entry<Integer,String> map :sMap.entrySet()){
            System.out.println(map.getKey()+";"+map.getValue());
        }
        char a = '是';
        System.out.println(a);
        Map<BizRestException,String> map = new HashMap<>();
        int q = 2;
        int w =4;
        System.out.println(q ^ w);
        LRU<String,Integer> lru = new LRU<>(5);
        lru.put("key-1", 1);
        lru.put("key-2", 2);
        lru.put("key-3", 3);
        lru.put("key-4", 4);
        lru.put("key-5", 5);
        lru.print();
        lru.get("key-1");
        lru.put("key-6", 6);
        lru.print();
    }
    public static Map<Integer, String> testTreeMap(){
        SortedMap<Integer,String> sortedMap = new TreeMap<>();
        sortedMap.put(1,"张一");
        sortedMap.put(7,"张七");
        sortedMap.put(3,"张三");
        sortedMap.put(4,"张四");
        sortedMap.put(2,"张二");
        sortedMap.put(5,"张五");

        return sortedMap;
    }
}
