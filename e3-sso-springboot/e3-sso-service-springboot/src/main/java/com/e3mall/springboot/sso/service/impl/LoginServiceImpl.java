package com.e3mall.springboot.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.e3mall.springboot.mapper.TbUserMapper;
import com.e3mall.springboot.pojo.TbUser;
import com.e3mall.springboot.pojo.TbUserExample;
import com.e3mall.springboot.sso.service.LoginService;
import com.e3mall.springboot.utils.E3Result;
import com.e3mall.springboot.utils.JsonUtils;
import com.e3mall.springboot.utils.RedisUtils;

@Service
@Component
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Resource
	private RedisUtils redisUtils;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result userLogin(String username, String password) {
		// 1、判断用户和密码是否正确
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		//执行查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			//返回登录失败
			return E3Result.build(400, "用户名或密码错误");
		}
		//取用户信息
		TbUser user = list.get(0);
		//判断密码是否正确
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
			// 2、如果不正确，返回登录失败
			return E3Result.build(400, "用户名或密码错误");
		}
		// 3、如果正确生成token。
		String token = UUID.randomUUID().toString();
		// 4、把用户信息写入redis，key：token value：用户信息
		user.setPassword(null);
		redisUtils.set("SESSION:" + token, JsonUtils.objectToJson(user), SESSION_EXPIRE);
		// 5、把token返回
		return E3Result.ok(token);
	}

}
