package com.e3mall.springboot.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

/**
 * 前端展示模块
 * @author:	fangzf
 * @date:	2020年1月4日 下午12:50:30
 */
@EnableDubbo
@SpringBootApplication
public class E3PortalWebSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3PortalWebSpringbootApplication.class, args);
	}

}
