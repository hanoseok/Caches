package com.hanoseok.cache.controller;

import com.hanoseok.cache.redis.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hanoseok on 2014. 4. 6..
 */
@Controller
@RequestMapping("/redis")
public class RedisCacheController {

	@Autowired
	RedisCacheService<String> shardedRedisCacheService;


	@RequestMapping("/get")
	@ResponseBody
	public String get(@RequestParam String key){
		return shardedRedisCacheService.get(key);
	}

	@RequestMapping("/set")
	@ResponseBody
	public String set(@RequestParam String key, @RequestParam String value){
		shardedRedisCacheService.set(key, value);
		return shardedRedisCacheService.get(key);
	}
}
