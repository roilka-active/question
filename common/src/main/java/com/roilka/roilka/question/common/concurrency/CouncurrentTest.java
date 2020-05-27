package com.roilka.roilka.question.common.concurrency;/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author zhanghui1
 * @date 2020/5/2714:31
 */

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName CouncurrentTest
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/27 14:31
 **/
public class CouncurrentTest {
    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap<>();
        map.put(1, 1);
    }
}
