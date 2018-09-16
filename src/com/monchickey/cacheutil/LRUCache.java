package com.monchickey.cacheutil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K, V> {
    private int maxLength;
    private LinkedHashMap<K, V> cache;
    private final float LOAD_FACTOR = 0.75f;
    // 读写锁
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCache(int maxLength) {
        this.maxLength = maxLength;
        int capacity = (int) Math.ceil(maxLength / LOAD_FACTOR) + 1;
        cache = new LinkedHashMap(capacity, LOAD_FACTOR, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxLength;
            }
        };
    }

    /**
     * 添加cache元素
     * @param item
     * @param value
     */
    public void put(K item, V value) {
        lock.writeLock().lock();
        cache.put(item, value);
        lock.writeLock().unlock();
    }

    /**
     * 获取cache值
     * @param item
     * @return 值存在则返回值, 否则返回null
     */
    public V get(K item) {
        lock.readLock().lock();
        V value = cache.get(item);
        lock.readLock().unlock();
        return value;
    }

    /**
     * 判断key是否存在
     * @param item
     * @return 存在返回true, 否则返回false
     */
    public boolean containsKey(K item) {
        lock.readLock().lock();
        boolean isContain = cache.containsKey(item);
        lock.readLock().unlock();
        return isContain;
    }

    /**
     * 删除item项
     * @param item
     */
    public void remove(K item) {
        lock.writeLock().lock();
        cache.remove(item);
        lock.writeLock().unlock();
    }

    /**
     * 获取cache的大小
     * @return
     */
    public int size() {
        lock.readLock().lock();
        int size = cache.size();
        lock.readLock().unlock();
        return size;
    }

}
