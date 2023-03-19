package com.roilka.roilka.question.common.demo.guava;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
public class Guava_List {
    public static void main(String[] args) {
        Multimap multimap = ImmutableListMultimap.of();
        multimap.put(1, "a");
        multimap.put(2, "a");
        multimap.put(3, "a");
        multimap.put(2, "a");
        multimap.put(3, "a");
        System.out.println(multimap.asMap());

    }
}
