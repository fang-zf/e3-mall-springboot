package com.e3mall.springboot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.content.service.ContentCategoryService;
import com.e3mall.springboot.pojo.EasyUITreeNode;
import com.e3mall.springboot.utils.E3Result;

@Controller
public class ContentCatController {

	@Reference
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(
			@RequestParam(name="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	
	/**
	 * 添加分类节点
	 */
	@RequestMapping(value="/content/category/create", method=RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCategory(Long parentId, String name) {
		//调用服务添加节点
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
	}
	
	/**
	 * @Title:  更新内容分类
	 * @author: fangzf
	 * @param:  @param id
	 * @param:  @param name
	 * @param:  @return
	 * @date:   2019年6月6日
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCatList(long id,String name){
		E3Result result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
	
	/**
	 * @Title:  删除内容分类
	 * @author: fangzf
	 * @param:  @param id
	 * @param:  @return
	 * @date:   2019年6月6日
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public E3Result deleteContentCatList(long id){
		E3Result result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
}
