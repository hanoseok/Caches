package com.hanoseok.cache.redis.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanoseok on 2014. 4. 4..
 */
@Service
public class ShardedRedisCacheService<T> implements RedisCacheService<T>, InitializingBean {

	private ShardedJedisPool pool;

	@Override
	public T get(String key) {
		ShardedJedis jedis = pool.getResource();
		try {
			return (T) jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public void set(String key, T t) {
		ShardedJedis jedis = pool.getResource();
		try {
			jedis.set(key, (String) t);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}

	}

	@Override
	public void del(String key) {
		ShardedJedis jedis = pool.getResource();
		try {
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		list.add(new JedisShardInfo("192.168.239.133"));
		list.add(new JedisShardInfo("192.168.239.134"));
		list.add(new JedisShardInfo("192.168.239.135"));
		list.add(new JedisShardInfo("192.168.239.136"));
		pool = new ShardedJedisPool(new JedisPoolConfig(), list);
	}
}
