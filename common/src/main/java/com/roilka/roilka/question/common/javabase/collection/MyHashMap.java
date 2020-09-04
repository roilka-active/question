package com.roilka.roilka.question.common.javabase.collection;

import java.util.*;

/**
 * @ClassName MyHashMap
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/6/15 19:51
 **/
public class MyHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    Set<Entry<K, V>> entrySet;

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> se;
        return (se = entrySet) == null ? (entrySet = new EntrySet()) : se;
    }

    final class EntrySet extends AbstractSet<Entry<K, V>> {

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }
}

