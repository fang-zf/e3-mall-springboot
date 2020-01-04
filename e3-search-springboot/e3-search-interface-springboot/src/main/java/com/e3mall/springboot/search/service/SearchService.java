package com.e3mall.springboot.search.service;

import com.e3mall.springboot.pojo.SearchResult;

public interface SearchService {
	
	/**
	 * @Title:  
	 * @author: fangzf
	 * @param:  @param ketword
	 * @param:  @param page		页数
	 * @param:  @param rows		每个多条条记录
	 * @param:  @return
	 * @date:   2019年6月15日
	 */
	SearchResult search(String keyword,int page,int rows) throws Exception;
	
}
