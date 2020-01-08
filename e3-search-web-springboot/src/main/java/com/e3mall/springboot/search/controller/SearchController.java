package com.e3mall.springboot.search.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.pojo.SearchResult;
import com.e3mall.springboot.search.service.SearchService;

@Controller
public class SearchController {

	@Reference(timeout=1000*60,retries=3)
	private SearchService searchService;
	
	@Value("${SEARCH_RESULT_ROWS}")
	private int SEARCH_RESULT_ROWS;
	
	/**
	 * 访问时把.html去掉
	 * @param:	@param keyword
	 * @param:	@param page
	 * @param:	@param model
	 * @param:	@return
	 * @param:	@throws Exception     
	 * @return:	String  
	 * @author:	fangzf
	 * @date:  	2020年1月4日 下午4:36:40  
	 * @throws
	 */
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1") int page,Model model) throws Exception{
		//查询商品列表
		SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
		//把结果传递给页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecordCount());
		model.addAttribute("itemList", searchResult.getItemList());
		//返回逻辑视图
		return "search";
	}
	
}
