package com.e3mall.springboot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.pojo.EasyUITreeNode;
import com.e3mall.springboot.service.ItemCatService;

/**
 * 商品分类管理Controller
 * @author fangzf
 * @date:  2019年12月29日
 */
@Controller
public class ItemCatController {

	//超时时间30s 重试3次
	@Reference(timeout=1000*30,retries=3)
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(
			@RequestParam(name="id", defaultValue="0")Long parentId) {
		//调用服务查询节点列表
		List<EasyUITreeNode> list = itemCatService.getItemCatlist(parentId);
		return list;
	}
}
