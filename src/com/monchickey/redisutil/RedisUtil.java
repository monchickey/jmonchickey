package com.monchickey.redisutil;

import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * Redis 测试类
 *
 */

public class RedisUtil {
    
    private static Jedis jedis;
    
    /**
     * 无密码的构造方法
     * @param url
     */
    public RedisUtil(String url) {
        jedis = new Jedis(url);
    }
    
    /**
     * 有密码认证的的构造方法
     * @param url
     * @param password
     */
    public RedisUtil(String url, String password) {
        jedis = new Jedis(url);
        //权限认证
        jedis.auth(password);
    }
    
    /**
     * 设置字符串
     * @param key
     * @param value
     */
    public static boolean setString(String key, String value) {
        jedis.set(key, value);
        if(jedis.get(key) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 获取字符串
     * @param key
     * @return String
     */
    public static String getString(String key) {
        return jedis.get(key);
    }
    
    
    /**
     * 添加Hash数据
     * @param hashName
     * @param hashContent
     */
    public static void setHash(String hashName, Map<String, String> hashContent) {
        jedis.hmset(hashName, hashContent);
    }
    
    /**
     * 根据键值获取hashmap
     * @param hashName
     * @return map
     */
    public static Map<String, String> getHash(String hashName) {
        return jedis.hgetAll(hashName);
    }
    
    /**
     * 获取hashmap中的指定key
     * @param hashName
     * @param key
     * @return String
     */
    public static String getHashkey(String hashName, String key) {
        return jedis.hget(hashName, key);
    }
}
