package com.roilka.roilka.question.common.utils;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/6/3 16:43
 **/
public class Test {

    public static void main(String[] args) {
//        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
//
//        objectObjectConcurrentHashMap.put(1,"ss" );
//
//        int v;
//        String[] aa = {"A", "B", "C", "D", "E"};
//        for (int i = 0; i < 100; i++) {
//            v = (int)(Math.random() * 5);
////            System.out.println(aa[v]);
//        }
//
//        String sss = "KS";
//        int i = sss.hashCode() % 20;
//        System.out.println(i);
        test01();
    }

    private static void test01(){
        String ss = "update hwo_work_order_%s set biz_no = concat(biz_no,'_cancel') where  biz_no in (%s);";
        String bizNo = "'GD202301121645530000036'";
        String result = null;
        for (int i = 0; i < 64; i++) {
            if (i < 10){
                result = String.format(ss, "000" + i, bizNo, bizNo);
                System.out.println(result);
            }else {
                result = String.format(ss, "00" + i, bizNo, bizNo);
                System.out.println(result);
            }

        }
    }
}
