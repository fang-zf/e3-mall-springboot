package com.e3mall.springboot.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

/**
 * 搜索模块web
 * @author:	fangzf
 * @date:	2020年1月4日 下午2:43:03
 */
@EnableDubbo
@SpringBootApplication
public class E3SearchWebSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3SearchWebSpringbootApplication.class, args);
	}

}
