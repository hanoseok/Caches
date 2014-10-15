package com.hanoseok.cache.redis;

import com.hanoseok.cache.redis.service.RedisCacheService;
import com.hanoseok.cache.test.TestBase;
import com.hanoseok.utils.reflect.ReflectionUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * Created by hanoseok on 2014. 4. 6..
 */
public class JedisRedisTest extends TestBase {

	@Autowired
	RedisCacheService shardedRedisCacheService;

	@Test
	public void jedisGet() throws MalformedURLException {
		shardedRedisCacheService.set("test1", "1111");
		assertEquals(shardedRedisCacheService.get("test1"), "1111");
	}

}
