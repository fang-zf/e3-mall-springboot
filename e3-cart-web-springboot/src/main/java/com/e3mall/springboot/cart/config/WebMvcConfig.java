package com.e3mall.springboot.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.e3mall.springboot.cart.interceptor.LoginInterceptor;

/**
 * 拦截器配置
 * 
 * @author: fangzf
 * @date: 2020年1月12日 上午11:45:41
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 登录状态拦截器
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/cart/**");
	}

	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
}
