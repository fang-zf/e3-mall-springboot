package com.e3mall.springboot.service;

import java.util.List;

import com.e3mall.springboot.pojo.EasyUITreeNode;

public interface ItemCatService {
	
	/**
	 * @Title:  根据商品类别父级id查询商品列表
	 * @author: fangzf
	 * @param:  @param parentId
	 * @param:  @return
	 * @date:   2019年5月24日
	 */
	List<EasyUITreeNode> getItemCatlist(long parentId);
	
}
