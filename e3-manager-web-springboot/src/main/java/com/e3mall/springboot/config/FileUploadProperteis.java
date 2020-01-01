package com.e3mall.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 文件上传相关属性
 * @author fangzf
 * @date: 2019年3月3日
 */
@Data
@Component
@ConfigurationProperties(prefix="file")
public class FileUploadProperteis {
	
	//图片服务器地址
	private String uploadUrl; 
}
