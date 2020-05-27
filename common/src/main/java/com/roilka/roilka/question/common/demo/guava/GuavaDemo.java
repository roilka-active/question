package com.roilka.roilka.question.common.demo.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName GuavaDemo
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/18 10:11
 **/
public class GuavaDemo {
    public static void main(String[] args) {
        testObject();
    }

    /**
     * 使用和避免null
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Object useAndIgnoreNull(T obj) {
        List<T> list = new ArrayList<>();
        list.add(obj);
        Optional<List> optional = Optional.ofNullable(list);

        String a = null;
        String b = "d";
        // 当a为null时，则返回b
        String ss = Optional.ofNullable(a).orElse(b);
        System.out.println(ss);
        return ss;
    }

    public static void testPreconditions() {
        String a = "a";
        String b = null;
        Preconditions.checkNotNull(a);
        Boolean flag = false;
        /**
         *  检查boolean是否为true，用来检查传递给方法的参数。
         */
        Preconditions.checkArgument(flag);
        /**
         *  用来检查对象的某些状态。
         */
        Preconditions.checkState(flag);
        Preconditions.checkElementIndex(1, 1);
        Objects.equal(a, b);
    }

    /**
     * 常见Object方法
     */
    public static void testObject() {
        String a = "a";
        String b = null;
        String c = "a";
        String d = "d";

        // equal
        //避免空指针的的判断
        boolean flag = Objects.equal(a, b);
        System.out.println(flag);
        // 计算散列值
        int hc = Objects.hashCode(d);
        System.out.println(hc);

        String result = MoreObjects.toStringHelper(a).add("a", a).add("d", d).toString();
        System.out.println(result);


    }

    public static int compareTo(){
        String a = "a";
        String b = null;
        String c = "a";
        String d = "d";
        ComparisonChain.start().compare(a, d).compare(d, a).result();
        return 0;
    }
}
