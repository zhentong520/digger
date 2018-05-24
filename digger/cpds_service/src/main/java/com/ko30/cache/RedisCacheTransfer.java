package com.ko30.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * 静态注入中间类
 * @author DengHuaqing
 *
 *2017-04-13
 */
public class RedisCacheTransfer {
	
	 @Autowired
	    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
	        RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
	    }
	 

}
