package com.e3mall.springboot.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.pojo.TbUser;
import com.e3mall.springboot.sso.service.RegisterService;
import com.e3mall.springboot.utils.E3Result;

@Controller
public class RegisterController {

	@Reference
	private RegisterService registerService;
	
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
	
	/**
	 * 校验
	 * @param:	@param param
	 * @param:	@param type
	 * @param:	@return     
	 * @return:	E3Result  
	 * @author:	fangzf
	 * @date:  	2020年1月11日 下午2:24:26  
	 * @throws
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param,@PathVariable Integer type){
		E3Result result = registerService.checkData(param, type);
		return result;
	}
	
	/**
	 * 注册
	 * @param:	@param user
	 * @param:	@return     
	 * @return:	E3Result  
	 * @author:	fangzf
	 * @date:  	2020年1月11日 下午2:24:19  
	 * @throws
	 */
	@RequestMapping("/user/register")
	@ResponseBody
	public E3Result register(TbUser user){
		E3Result result = registerService.register(user);
		return result;
	}
	
}
