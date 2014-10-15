package com.hanoseok.cache.redis.service;

/**
 * Created by hanoseok on 2014. 4. 4..
 */
public interface RedisCacheService<T> {

	public T get(String key);

	public void set(String key, T t);

	public void del(String key);
}
