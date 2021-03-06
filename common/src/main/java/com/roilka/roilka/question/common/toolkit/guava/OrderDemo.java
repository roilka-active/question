package com.roilka.roilka.question.common.toolkit.guava;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName OrderDemo
 * @Description TODO
 * @Author 75six
 * @Date 2020/5/18 16:42
 **/
public class OrderDemo {
    public static void main(String[] args) {

    }

    /**
     *   自定义比较器，根据字符串的长度来比较大小
     */
    public static void orderCustom(){
        // 自定义比较器，根据字符串的长度来比较大小
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            @Override
            public int compare(@Nullable String left, @Nullable String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        Ordering<String> ordering = Ordering.from((left, right) -> {
            return Ints.compare(((String) left).length(), ((String) right).length());
        });
        List<String> list = new ArrayList<>();
        String a = "assd";
        String b = "baa";
        int r = ordering.compare(a, b);
        System.out.println(r);
        Ordering<Iterable<String>> iterableOrdering = ordering.lexicographical();
        Iterator<String> iterator = Sets.newHashSet(list).iterator();
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList<String>(linkedList);
    }

}
