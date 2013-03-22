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


    public void append(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisManager.getResource();
            if (jedis != null) {
                jedis.append(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisManager.returnResource(jedis);
        }
    }

    public Long decr(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisManager.getResource();
            if (jedis != null) {
                res = jedis.decr(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisManager.returnResource(jedis);
        }
        return res;
    }

    public Long decrBy(String key,long num) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisManager.getResource();
            if (jedis != null) {
                res =  jedis.decrBy(key,num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisManager.returnResource(jedis);
        }
        return res;
    }

    public Long del(String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisManager.getResource();
            if (jedis != null) {
                res = jedis.del(keys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisManager.returnResource(jedis);
        }
        return res;
    }


}
