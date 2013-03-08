package com.jared.core.cache.redis;

import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-7
 * Time: 下午6:28
 * To change this template use File | Settings | File Templates.
 */
public class JedisClient {

    private JedisManager jedisManager = JedisManager.getInstance();

    public void set(String key, String value, int duration) {
        Jedis jedis = null;
        try {
            jedis = jedisManager.getResource();
            if (jedis != null) {
                jedis.setex(key, duration, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisManager.returnResource(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisManager.getResource();
            if (jedis != null) {
                return jedis.get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisManager.returnResource(jedis);
        }
        return null;
    }


    public void t(){
        Jedis jedis = null;
        try {
            jedis = jedisManager.getResource();
            if (jedis != null) {
               // jedis.set
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisManager.returnResource(jedis);
        }
    }



}
