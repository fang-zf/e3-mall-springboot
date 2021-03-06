package com.e3mall.springboot.controller;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.pojo.EasyUIDataGridResult;
import com.e3mall.springboot.pojo.TbItem;
import com.e3mall.springboot.service.ItemService;
import com.e3mall.springboot.utils.E3Result;

@Controller
public class ItemController {
	
	//超时时间30s 重试3次
	@Reference(timeout=1000*30,retries=3)
	private ItemService itemService;
	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable int itemId){
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//调用服务查询商品列表
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	/**
	 * 商品添加功能
	 * @author: fangzf
	 * @param:  @param item
	 * @param:  @param desc
	 * @param:  @return
	 * @date:   2019年12月29日
	 */
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item, String desc) {
		E3Result result = itemService.addItem(item, desc);
		//发送商品添加消息  
		//在 com.e3mall.springboot.search.message中消费消息 ，添加到sorlr库中
		//在 com.e3mall.springboot.item.listener中消费消息，生成静态页面
		if(result.getStatus() == 200){
			TbItem data = (TbItem) result.getData();
			ActiveMQTopic destination = new ActiveMQTopic("addItem");
			jmsMessagingTemplate.convertAndSend(destination, data.getId());
		}
		return result;
	}
	
}
