package com.e3mall.springboot.content.service;

import java.util.List;

import com.e3mall.springboot.pojo.EasyUITreeNode;
import com.e3mall.springboot.utils.E3Result;

public interface ContentCategoryService {
	
	/**
	 * @Title:  根据parentId获得内容分类
	 * @author: fangzf
	 * @param:  @param parenId
	 * @param:  @return
	 * @date:   2019年5月29日
	 */
	List<EasyUITreeNode> getContentCatList(long parenId);
	
	/**
	 * @Title:  添加内容分类
	 * @author: fangzf
	 * @param:  @param parentId
	 * @param:  @param name
	 * @param:  @return
	 * @date:   2019年6月5日
	 */
	E3Result addContentCategory(long parentId,String name);
	
	/**
	 * @Title:  更新内容分类
	 * @author: fangzf
	 * @param:  @param id
	 * @param:  @param name
	 * @param:  @return
	 * @date:   2019年6月6日
	 */
	E3Result updateContentCategory(long id,String name);
	
	/**
	 * @Title:  删除内容分类
	 * @author: fangzf
	 * @param:  @param id
	 * @param:  @return
	 * @date:   2019年6月6日
	 */
	E3Result deleteContentCategory(long id);
}
