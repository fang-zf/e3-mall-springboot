package com.e3mall.springboot.search.config;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SolrServer
 * @author:	fangzf
 * @date:	2020年1月2日 下午11:01:18
 */
@Configuration
public class SolrConfig {

	@Bean
	public HttpSolrServer httpSolrServer(){
		HttpSolrServer solrServer = new HttpSolrServer("http://101.37.26.93:8080/solr/collection1");
		return solrServer;
	}

}
