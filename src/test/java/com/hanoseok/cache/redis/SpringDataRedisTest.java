package com.hanoseok.cache.redis;

import com.hanoseok.cache.test.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.MalformedURLException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by hanoseok on 2014. 4. 6..
 */
public class SpringDataRedisTest extends TestBase {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private RedisCacheManager cacheManager;

	@Test
	public void springGet() throws MalformedURLException {
		redisTemplate.opsForValue().set("springs", "1414");
		assertEquals(redisTemplate.opsForValue().get("springs"), "1414");
	}
}
