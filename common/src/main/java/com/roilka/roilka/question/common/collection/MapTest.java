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
