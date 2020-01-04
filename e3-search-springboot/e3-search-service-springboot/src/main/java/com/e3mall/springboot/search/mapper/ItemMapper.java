package com.e3mall.springboot.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.e3mall.springboot.pojo.SearchItem;

@Mapper
public interface ItemMapper {
	
	List<SearchItem> getItemList();
	SearchItem getItemById(Long itemId);
}
