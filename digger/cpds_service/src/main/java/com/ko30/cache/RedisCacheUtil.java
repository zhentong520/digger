package com.ko30.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * redis工具类
 * 
 * @author LDengHuaqing 2017-04-17
 * @param <T>
 */
@Service("redisCacheUtil")
public class RedisCacheUtil<T> {

	@Autowired
	@Qualifier("redisTemplate")
	public RedisTemplate redisTemplate;

	private final Logger logger=Logger.getLogger(RedisCacheUtil.class);
	
	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = getRedisTemplateBean();
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * 
	 * @param key
	 *            缓存的键值
	 * @param value
	 *            缓存的值
	 * @return 缓存的对象
	 */
	public <T> ValueOperations<String, T> setCacheObject(String key, T value,
			int time) {
		
		try {
			ValueOperations<String, T> operation = redisTemplate.opsForValue();
			redisTemplate.expire(key, time, TimeUnit.HOURS);
			operation.set(key, value);
			return operation;
		} catch (Exception e) {
			logger.info("设置redis数据异常："+e.getMessage());
		}
		return null;
		
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * 
	 * @param key
	 *            缓存的键值
	 * @param value
	 *            缓存的值
	 * @return 缓存的对象
	 */
	public <T> ValueOperations<String, T> setCacheObjectMIN(String key,
			T value, int time) {

		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		redisTemplate.expire(key, time, TimeUnit.MINUTES);
		operation.set(key, value);
		return operation;
	}

	/**
	 * 获得缓存的基本对象。
	 * 
	 * @param key
	 *            缓存键值
	 * @param operation
	 * @return 缓存键值对应的数据
	 */
	public <T> T getCacheObject(String key/* ,ValueOperations<String,T> operation */) {
		
		try {
			ValueOperations<String, T> operation = redisTemplate.opsForValue();
			return operation.get(key);
		} catch (Exception e) {
			logger.info("设置redis数据异常："+e.getMessage());
		}
		return null;
	}

	/**
	 * 缓存List数据
	 * 
	 * @param key
	 *            缓存的键值
	 * @param dataList
	 *            待缓存的List数据
	 * @return 缓存的对象
	 */
	public <T> ListOperations<String, T> setCacheList(String key,
			List<T> dataList, int time) {
		ListOperations listOperation = redisTemplate.opsForList();
		if (null != dataList) {
			int size = dataList.size();
			for (int i = 0; i < size; i++) {
				// System.out.println(SerializeUtil.serialize(dataList.get(i)));
				listOperation.rightPush(key, dataList.get(i));
			}
			redisTemplate.expire(key, time, TimeUnit.HOURS);

		}

		return listOperation;
	}

	/**
	 * 获得缓存的list对象
	 * 
	 * @param key
	 *            缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public <T> List<T> getCacheList(String key) {
		List<T> dataList = new ArrayList<T>();
		if (redisTemplate == null) {
			redisTemplate = getRedisTemplateBean();
		}
		ListOperations<String, T> listOperation = redisTemplate.opsForList();

		Long size = listOperation.size(key);

		for (int i = 0; i < size; i++) {
			dataList.add((T) listOperation.leftPop(key));
		}

		return dataList;
	}

	/**
	 * 缓存Set
	 * 
	 * @param key
	 *            缓存键值
	 * @param dataSet
	 *            缓存的数据
	 * @return 缓存数据的对象
	 */
	public <T> BoundSetOperations<String, T> setCacheSet(String key,
			Set<T> dataSet, int time) {
		BoundSetOperations<String, T> setOperation = redisTemplate
				.boundSetOps(key);
		/*
		 * T[] t = (T[]) dataSet.toArray(); setOperation.add(t);
		 */

		Iterator<T> it = dataSet.iterator();
		while (it.hasNext()) {
			setOperation.add(it.next());
		}
		redisTemplate.expire(key, time, TimeUnit.HOURS);
		return setOperation;
	}

	/**
	 * 获得缓存的set
	 * 
	 * @param key
	 * @param operation
	 * @return
	 */
	public Set<T> getCacheSet(String key/*
										 * ,BoundSetOperations<String,T>
										 * operation
										 */) {
		Set<T> dataSet = new HashSet<T>();
		BoundSetOperations<String, T> operation = redisTemplate
				.boundSetOps(key);

		Long size = operation.size();
		for (int i = 0; i < size; i++) {
			dataSet.add(operation.pop());
		}
		return dataSet;
	}

	/**
	 * 缓存Map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public <T> HashOperations<String, String, T> setCacheMap(String key,
			Map<String, T> dataMap, int time) {

		HashOperations hashOperations = redisTemplate.opsForHash();
		if (null != dataMap && null != hashOperations) {

			for (Map.Entry<String, T> entry : dataMap.entrySet()) {

				/*
				 * System.out.println("Key = " + entry.getKey() + ", Value = " +
				 * entry.getValue());
				 */
				try {

					hashOperations.put(key, entry.getKey(), entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			redisTemplate.expire(key, time, TimeUnit.HOURS);
		}

		return hashOperations;
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 * @param hashOperation
	 * @return
	 */
	public <T> Map<String, T> getCacheMap(String key/*
													 * ,HashOperations<String,String
													 * ,T> hashOperation
													 */) {
		Map<String, T> map = redisTemplate.opsForHash().entries(key);
		/* Map<String, T> map = hashOperation.entries(key); */
		return map;
	}

	/**
	 * 缓存Map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public <T> HashOperations<String, Integer, T> setCacheIntegerMap(
			String key, Map<Integer, T> dataMap, int time) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		if (null != dataMap) {

			for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
				hashOperations.put(key, entry.getKey(), entry.getValue());

			}
			redisTemplate.expire(key, time, TimeUnit.HOURS);

		}

		return hashOperations;
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 * @param hashOperation
	 * @return
	 */
	public <T> Map<Integer, T> getCacheIntegerMap(String key/*
															 * ,HashOperations<
															 * String,String,T>
															 * hashOperation
															 */) {
		Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
		/* Map<String, T> map = hashOperation.entries(key); */
		return map;
	}

	/**
	 * 删除数据
	 * 
	 * @param key
	 */
	public void reomveCache(String key) {

		redisTemplate.delete(key);
	}

	/**
	 * 设置缓存有效时间
	 */
	public void setDateTime(String key, Integer time) {

		redisTemplate.expire(key, time, TimeUnit.HOURS);
	}

	public RedisTemplate getRedisTemplateBean() {
		if (redisTemplate == null) {
			synchronized (RedisTemplate.class) {
				if (redisTemplate == null) {
					redisTemplate = new RedisTemplate();
				}
			}
		}
		return redisTemplate;
	}
	
	
	/**
	 * 获得缓存的Map 的其中一个value
	 * @param key
	 * @return
	 */
	public String getCacheMapValue(String key,String childKey) {
		return (String) redisTemplate.opsForHash().get(key, childKey);
	}
	
	public boolean exists(final String key) {
         return (boolean)redisTemplate.execute(new RedisCallback() {
        	 public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                 return connection.exists(key.getBytes());
             }
		 });
    }

}
