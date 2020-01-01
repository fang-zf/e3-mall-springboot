package com.e3mall.springboot.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.content.service.ContentService;
import com.e3mall.springboot.pojo.TbContent;

/**
 * 页面跳转
 * @author fangzf
 * @date:  2019年5月19日
 */
@Controller
public class IndexController {
	
	//默认轮播图id
	@Value("${content_lunbo_id}")
	private Long content_lunbo_id;
	
	@Reference
	private ContentService contentService;
	
	/**
	 * @Title:  首页
	 * @author: fangzf
	 * @param:  @param model
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	@RequestMapping("/")
	public String showIndex(Model model){
		//查询大广告位轮播图
		List<TbContent> ad1List = contentService.getContentList(content_lunbo_id);
		model.addAttribute("ad1List", ad1List);
		return "index";
	}
	
}
