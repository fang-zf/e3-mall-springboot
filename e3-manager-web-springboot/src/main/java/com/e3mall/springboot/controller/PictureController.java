package com.e3mall.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.e3mall.springboot.config.FileUploadProperteis;
import com.e3mall.springboot.utils.FastDFSClient;
import com.e3mall.springboot.utils.JsonUtils;


/**
 * 图片上传处理Controller
 * @author fangzf
 * @date:  2019年12月29日
 */
@Controller
public class PictureController {
	
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private FileUploadProperteis fileUploadProperteis;

	@RequestMapping(value="/pic/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try {
            //返回绝对路径
            String url = fileUploadProperteis.getUploadUrl()+"/"+fastDFSClient.uploadFile(uploadFile);
			//	log.info("上传图片路径：{},上传大小：{}",fileUploadProperteis.getUploadUrl() + url,uploadFile.getSize());
			System.out.println(fileUploadProperteis.getUploadUrl() + url + ",," + uploadFile.getSize());
            	//封装到map中返回
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
	}
}
