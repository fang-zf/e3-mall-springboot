package com.e3mall.springboot.sso.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.e3mall.springboot.pojo.TbUser;
import com.e3mall.springboot.sso.service.TokenService;
import com.e3mall.springboot.utils.E3Result;
import com.e3mall.springboot.utils.JsonUtils;
import com.e3mall.springboot.utils.RedisUtils;

@Service
@Component
public class TokenServiceImpl implements TokenService {

	@Resource
	private RedisUtils redisUtils;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result getUserByToken(String token) {
		//根据token到redis中取用户信息
		String json = (String) redisUtils.get("SESSION:" + token);
		//取不到用户信息，登录已经过期，返回登录过期
		if(StringUtils.isBlank(json)){
			return E3Result.build(201, "用户登录已经过期");
		}
		//取到用户信息更新token的过期时间
		redisUtils.expire("SESSION:" + token, SESSION_EXPIRE);
		//返回结果，E3Result其中包含TbUser对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return E3Result.ok(user);
	}

}
