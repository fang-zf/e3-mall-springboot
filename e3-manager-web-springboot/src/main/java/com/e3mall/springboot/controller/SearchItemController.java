package com.e3mall.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.search.service.SearchItemService;
import com.e3mall.springboot.utils.E3Result;

/**
 * 导入商品数据到索引库
 * @author fangzf
 * @date:  2019年6月15日
 */
@Controller
public class SearchItemController {
	
	//超时时间30s 重试3次
	@Reference(timeout=1000*30,retries=3)
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList(){
		E3Result e3Result = searchItemService.importAllItems();
		return e3Result;
	}
	
}
