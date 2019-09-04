package com.bingo.cache.redis;

import redis.clients.jedis.Jedis;

/**
 * @author Bingo
 * @title: ReidsStart
 * @projectName java-hub
 * @description: TODO
 * @date 2019/9/4  23:45
 */
public class ReidsStart {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.110");

        String key = "cache/redis";
        jedis.set(key, "redis");

        String value = jedis.get(key);
        System.out.println(String.format("%s--%s", key,value));
    }
}
