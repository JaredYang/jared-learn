package com.jared.core.redis;

import com.jared.core.cache.redis.JedisClient;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-3-8
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class RedisTest {

    @Test
    public void test(){
        JedisClient jedisClient = new JedisClient();
        jedisClient.set("test1","myvalue1",1000);

        String res = jedisClient.get("test1");
        System.out.print(res);
    }
}
