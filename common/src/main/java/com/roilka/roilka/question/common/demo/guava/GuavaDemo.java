package com.roilka.roilka.question.common.demo.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName GuavaDemo
 * @Description TODO
 * @Author 75six
 * @Date 2020/5/18 10:11
 **/
public class GuavaDemo {
    public static void main(String[] args) {
//        testObject();
//        useAndIgnoreNull("aa");
        compareTo();
    }

    /**
     * 使用和避免null
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Object useAndIgnoreNull(String obj) {
        List<String> list = new ArrayList<>();
        list.add(obj);

        String a = null;
        String b = "d";
        // 当a为null时，则返回b
        String ss = Optional.ofNullable(a).orElse(b);
        System.out.println(ss);

        Optional<List> optional = Optional.ofNullable(null);
        List t2 = optional.orElse(Lists.newArrayList("bb"));

        System.out.println(t2);
        return ss;
    }

    /**
     * 前置条件判断
     */
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

    /**
     *  比较链
     * @return
     */
    public static int compareTo(){
        String a = "a";
        String b = null;
        String c = "a";
        String d = "d";
        ComparisonChain.start()
                .compare(a, d).
                compare(d, a).result();
        AA aa = new AA();
        aa.setAa("11");
        aa.setBb("22");
        AA bb = new AA();
        bb.setAa("11");
        bb.setBb("223");
        ArrayList<AA> aas = Lists.newArrayList(aa, bb);
        Map<String, String> collect = aas.stream().collect(Collectors.toMap(AA::getAa, AA::getBb, (r1, r2) -> r2));
        System.out.println(collect);
        return 0;
    }
    @Data
    static class AA{
        String aa;
        String bb;
    }
}
