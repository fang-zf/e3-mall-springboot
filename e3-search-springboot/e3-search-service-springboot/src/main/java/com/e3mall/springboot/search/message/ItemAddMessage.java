package com.e3mall.springboot.search.message;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.e3mall.springboot.pojo.SearchItem;
import com.e3mall.springboot.search.mapper.ItemMapper;

@Component
public class ItemAddMessage {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	// 使用JmsListener配置消费者监听的队列，其中name是接收到的消息
    @JmsListener(destination = "addItem")
    // SendTo 会将此方法返回的数据, 写入到 OutQueue 中去.
    //@SendTo("SQueue")
    public void handleMessage(String itemId) {
        System.out.println("成功接受到消息：" + itemId);
        try {
        	 //根据商品id查询商品信息
    		SearchItem searchItem = itemMapper.getItemById(new Long(itemId));
    		//创建一个文档对象
    		SolrInputDocument document = new SolrInputDocument();
    		//向文档对象中添加域
    		document.addField("id", searchItem.getId());
    		document.addField("item_title", searchItem.getTitle());
    		document.addField("item_sell_point", searchItem.getSell_point());
    		document.addField("item_price", searchItem.getPrice());
    		document.addField("item_image", searchItem.getImage());
    		document.addField("item_category_name", searchItem.getCategory_name());
    		//把文档写入索引库
    		solrServer.add(document);
    		//提交
    		solrServer.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

}
