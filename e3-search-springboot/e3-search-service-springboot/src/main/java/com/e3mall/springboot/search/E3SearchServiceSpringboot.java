package com.e3mall.springboot.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableJms //启动消息队列
@EnableDubbo
@SpringBootApplication
public class E3SearchServiceSpringboot {

	public static void main(String[] args) {
		SpringApplication.run(E3SearchServiceSpringboot.class, args);
	}

}
