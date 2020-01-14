package com.e3mall.springboot.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.sso.service.LoginService;
import com.e3mall.springboot.utils.CookieUtils;
import com.e3mall.springboot.utils.E3Result;

@Controller
public class LoginController {
	
	@Reference
	private LoginService loginService;
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	@RequestMapping("/page/login")
	public String showLogin(String redirect,Model model){
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
	/**
	 * 登录
	 * @param:	@param username
	 * @param:	@param password
	 * @param:	@param request
	 * @param:	@param response
	 * @param:	@return     
	 * @return:	E3Result  
	 * @author:	fangzf
	 * @date:  	2020年1月11日 下午3:04:13  
	 * @throws
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public E3Result login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		E3Result e3Result = loginService.userLogin(username, password);
		//判断是否登录成功
		if(e3Result.getStatus() == 200){
			String token = e3Result.getData().toString();
			//如果登录成功需要把token写入cookie
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		}
		//返回结果
		return e3Result;
	}
	
}
