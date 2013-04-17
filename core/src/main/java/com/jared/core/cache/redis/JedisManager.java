package com.jared.core.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-7
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
public class JedisManager {

    private static final Logger logger = LoggerFactory.getLogger(JedisManager.class);

    private static JedisManager instance = new JedisManager();

    private JedisPool jedisPool;

    private JedisManager() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(300);
        config.setMinIdle(2);
        config.setMaxIdle(50);
        config.setMaxWait(5000);
        jedisPool = new JedisPool(config, "192.168.10.11", 6391);
    }

    public static JedisManager getInstance() {
        return instance;
    }

    public Jedis getResource() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            logger.error("Failed to get jedis resource, reason is " + e.getMessage());
        }
        return jedis;
    }

    public void returnResource(Jedis jedis){
        if(jedis==null){
            return;
        }
        try {
            jedisPool.returnResource(jedis);
        } catch (Exception e) {
            logger.error("Failed to return jedis resource, reason is " + e.getMessage());
        }
    }

}
