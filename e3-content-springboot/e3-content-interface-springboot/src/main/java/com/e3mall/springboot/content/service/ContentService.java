package com.e3mall.springboot.content.service;

import java.util.List;

import com.e3mall.springboot.pojo.TbContent;
import com.e3mall.springboot.utils.E3Result;

public interface ContentService {
	
	/**
	 * @Title:  根据categoryId查询内容列表
	 * @author: fangzf
	 * @param:  @param categoryId
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	List<TbContent> getContentList(Long categoryId);
	
	/**
	 * @Title:  添加内容
	 * @author: fangzf
	 * @param:  @param content
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	E3Result addContent(TbContent content);

	/**
	 * @Title:  修改内容
	 * @author: fangzf
	 * @param:  @param content
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	E3Result editContent(TbContent content);

	/**
	 * @Title:  批量删除内容
	 * @author: fangzf
	 * @param:  @param ids
	 * @param:  @return
	 * @date:   2019年6月9日
	 */
	E3Result deleteContent(long[] ids);
	
}
