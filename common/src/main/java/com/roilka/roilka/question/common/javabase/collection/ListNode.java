package com.roilka.roilka.question.common.javabase.collection;

import lombok.Data;

/**
 * @author zhanghui1
 * @ClassName ListNode
 * TODO
 * @date 2020/8/31 13:57
 **/
@Data
public class ListNode<T> {
    public ListNode(T val) {
        this.val = val;
    }

    public ListNode() {
    }

    public ListNode<T> next;
    public T val;

}
