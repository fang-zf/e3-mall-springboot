package com.e3mall.springboot.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.item.pojo.Item;
import com.e3mall.springboot.pojo.TbItem;
import com.e3mall.springboot.pojo.TbItemDesc;
import com.e3mall.springboot.service.ItemService;

@Controller
public class ItemController {
	
	@Reference
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}.html")
	public String showItemInfo(@PathVariable Long itemId,Model modle){
		//调用服务取商品基本信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		//取商品描述信息
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		//把信息传递给页面
		modle.addAttribute("item", item);
		modle.addAttribute("itemDesc", itemDesc);
		//返回逻辑视图
		return "item";
	}
	
}
