package com.e3mall.springboot.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.e3mall.springboot.utils.RedisUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	@Resource
	private RedisUtils redisUtils;
 
	/**
	 * 插入缓存数据
	 */
	@Test
	public void set() {
		redisUtils.set("redis_key00000", "redis_vale00000");
	}
	
	/**
	 * 读取缓存数据
	 */
	@Test
	public void get() {
		String value = (String) redisUtils.get("redis_key00000");
		System.out.println(value);
	}
 
}
