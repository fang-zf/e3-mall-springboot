package com.e3mall.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@SpringBootApplication
public class E3CartWebSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3CartWebSpringbootApplication.class, args);
	}

}
