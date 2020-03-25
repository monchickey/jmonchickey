package com.monchickey.tests;

import com.monchickey.cache.LRUCache;

public class CacheUtilTest {
    public static void main(String[] args) {
        LRUCache<String, Integer> lruCache = new LRUCache<>(100);
        for(int i = 0; i < 100; i++) {
            lruCache.put(i + "", 1);
        }
        System.out.println(lruCache.size());
        System.out.println(lruCache.get("0"));
        lruCache.put("101", 3);
        System.out.println(lruCache.size());
        System.out.println(lruCache.containsKey("0"));
        System.out.println(lruCache.containsKey("1"));
    }
}
