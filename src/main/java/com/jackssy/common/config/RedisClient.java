package com.jackssy.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Zhangyf
 * @param <T>
 *
 */
@Component
public class RedisClient<T> {

    private final static Logger logger = LoggerFactory.getLogger(RedisClient.class);

    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value)  {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e){
            logger.error("redis set赋值异常:{}",e);
        }finally {
            //返还到连接池
            jedis.close();
        }
    }

    public String get(String key)   {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            logger.error("redis取值异常:{}",e);
        }
        finally {
            //返还到连接池
            jedis.close();
        }
        return null;
    }

    public void setobj(String key, T value)  {
        Jedis jedis = null;
        try {
            Set<T> set = new HashSet<T>();
            set.add(value);
            jedis = jedisPool.getResource();
            jedis.sadd(key, String.valueOf(set));
        }catch (Exception e){
            logger.error("redis setobj赋值异常:{}",e);
        }
        finally {
            //返还到连接池
            jedis.close();
        }
    }
}