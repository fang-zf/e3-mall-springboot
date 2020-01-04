package com.e3mall.springboot.search.solrj;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {
	
	//@Test
	public void addDocument() throws SolrServerException, IOException{
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://101.37.26.93:8080/solr/collection1");
		//创建一个文档对象SolrInputDocument
		SolrInputDocument document = new  SolrInputDocument();
		//向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义。
		document.addField("id", "doc1");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", "1000");
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	//@Test
	public void delDocument() throws SolrServerException, IOException{
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://101.37.26.93:8080/solr/collection1");
		solrServer.deleteById("doc1");
		//提交
		solrServer.commit();
	}
	
	//@Test
	public void queryIndexFuza() throws SolrServerException{
		SolrServer solrServer = new HttpSolrServer("http://101.37.26.93:8080/solr/collection1");
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("手机");
		query.setStart(0);
		query.setRows(20);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//执行查询
		QueryResponse queryResponse = solrServer.query(query);
		//取文档列表。取查询结果的总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询结果总记录数："+solrDocumentList.getNumFound());
		//遍历文档列表，从取域的内容。
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list !=null && list.size()>0){
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			
			System.out.println(title);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println(solrDocument.get("item_image"));
		}
		
	}
	
	
}
