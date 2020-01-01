package com.e3mall.springboot.service;

import com.e3mall.springboot.pojo.EasyUIDataGridResult;
import com.e3mall.springboot.pojo.TbItem;
import com.e3mall.springboot.pojo.TbItemDesc;
import com.e3mall.springboot.utils.E3Result;

public interface ItemService {
	
	/**
	 * 根据id查询
	 * @author: fangzf
	 * @param:  @param itemId
	 * @param:  @return
	 * @date:   2019年12月29日
	 */
	TbItem getItemById(long itemId);
	
	/**
	 * 分页查询
	 * @author: fangzf
	 * @param:  @param page
	 * @param:  @param rows
	 * @param:  @return
	 * @date:   2019年12月29日
	 */
	EasyUIDataGridResult getItemList(int page,int rows );
	E3Result addItem(TbItem item,String desc);
	//TbItemDesc getItemDescById(long itemId);

	
}
