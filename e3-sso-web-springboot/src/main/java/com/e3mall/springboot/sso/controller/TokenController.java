package com.e3mall.springboot.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.sso.service.TokenService;
import com.e3mall.springboot.utils.E3Result;
import com.e3mall.springboot.utils.JsonUtils;

@Controller
public class TokenController {
	
	@Reference
	private TokenService tokenService;
	
	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable("token")String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		//响应结果之前，判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			//把结果封装成一个js语句响应
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}
	
	/*@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable("token")String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		//响应结果之前，判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			//把结果封装成一个js语句响应 spring4.1以后支持
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			//mappingJacksonValue.setJsonpFunction(callback);
			mappingJacksonValue.setValue(callback);
			return mappingJacksonValue;
		}
		return result;
	}*/
}
