package com.e3mall.springboot.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.e3mall.springboot.pojo.SearchItem;
import com.e3mall.springboot.pojo.SearchResult;

@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * @Title:  根据查询条件查询索引库
	 * @author: fangzf
	 * @param:  @param query
	 * @param:  @return
	 * @throws SolrServerException 
	 * @date:   2019年6月15日
	 */
	public SearchResult search(SolrQuery query) throws SolrServerException{
		//根据query查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		//取查询结果。
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//取查询结果总记录数
		long numFound = solrDocumentList.getNumFound();
		SearchResult searchResult = new SearchResult();
		searchResult.setRecordCount(numFound);
		//取商品列表，需要取高亮显示
		List<SearchItem> searchItems = new ArrayList<>();
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list !=null && list.size()>0){
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			//添加到商品列表
			searchItems.add(item);
		}
		searchResult.setItemList(searchItems);
		//返回结果
		return searchResult;
	}
	
}
