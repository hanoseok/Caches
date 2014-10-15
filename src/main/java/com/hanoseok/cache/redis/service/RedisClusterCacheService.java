package com.hanoseok.cache.redis.service;

import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hanoseok on 2014. 4. 4..
 */
public class RedisClusterCacheService<T> implements RedisCacheService <T>, InitializingBean {

	private JedisCluster jedis;

	@Override
	public T get(String key) {
		try{
			return (T)jedis.get(key);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public void set(String key, T t) {
		try{
			jedis.set(key, (String) t);
		}catch(Exception e){
		}

	}

	@Override
	public void del(String key) {
		jedis.del(key);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("192.168.239.133", 6379));
		jedisClusterNodes.add(new HostAndPort("192.168.239.134", 6379));
		jedisClusterNodes.add(new HostAndPort("192.168.239.135", 6379));
		jedisClusterNodes.add(new HostAndPort("192.168.239.136", 6379));
		jedis = new JedisCluster(jedisClusterNodes);
	}
}
