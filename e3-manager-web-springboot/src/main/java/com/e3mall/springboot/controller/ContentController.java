package com.e3mall.springboot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.content.service.ContentService;
import com.e3mall.springboot.pojo.TbContent;
import com.e3mall.springboot.utils.E3Result;

/**
 * 内容管理Controller
 * @author fangzf
 * @date:  2019年5月29日
 */
@Controller
public class ContentController {

	@Reference
	private ContentService contentService;
	
	/**
	 * @Title:  根据categoryId查询内容列表
	 * @author: fangzf
	 * @param:  @param categoryId
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	@RequestMapping(value="/content/query/list",method=RequestMethod.GET)
	@ResponseBody
	public List<TbContent> getContentList(@RequestParam(value="categoryId",defaultValue="0") Long categoryId){
		List<TbContent> list = contentService.getContentList(categoryId);
		return list;
	}
	
	/**
	 * @Title:  添加内容
	 * @author: fangzf
	 * @param:  @param content
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result createContent(TbContent content){
		return contentService.addContent(content);
	}
	
	/**
	 * @Title:  修改内容
	 * @author: fangzf
	 * @param:  @param content
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	@RequestMapping(value="/rest/content/edit",method=RequestMethod.POST)
	@ResponseBody
	public E3Result editContent(TbContent content){
		return contentService.editContent(content);
	}
	
	/**
	 * @Title:  批量删除内容
	 * @author: fangzf
	 * @param:  @param ids
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	@RequestMapping(value="/content/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteContent(long[] ids){
		return contentService.deleteContent(ids);
	}
	
}
