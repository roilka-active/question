package com.roilka.roilka.question.common.javabase.collection;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanghui1
 * @ClassName ListNodeTest
 * TODO
 * @date 2020/8/31 14:00
 **/
public class ListNodeTest {
    public static void main(String[] args) {
        ListNode<Integer> nodeL = new ListNode<>();
        nodeL.val = 1;
        nodeL.next = new ListNode<>(2);
        nodeL.next.next = new ListNode<>(3);
        nodeL.next.next.next = new ListNode<>(4);
        nodeL.next.next.next.next = new ListNode<>(5);
       System.out.println("reverse before:"+ nodeL.toString());
       System.out.println("reverse after:"+ reverse(nodeL).toString());
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        int [][] a=new int[][]{{1,2,3},{2,3}};

        System.out.println(a[0].length);
        System.out.println(a.length);
    }

    public static  <T> ListNode<T> reverse(ListNode<T> head){
        ListNode<T> cur = null;
        ListNode<T> pre = head;
        while(pre != null){
            ListNode temp = pre.next;
            pre.next = cur;
            cur = pre;
            pre = temp;
        }
        return cur;
    }
}
