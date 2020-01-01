package com.e3mall.springboot.content.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.e3mall.springboot.content.service.ContentService;
import com.e3mall.springboot.mapper.TbContentMapper;
import com.e3mall.springboot.pojo.TbContent;
import com.e3mall.springboot.pojo.TbContentExample;
import com.e3mall.springboot.utils.E3Result;
import com.e3mall.springboot.utils.JsonUtils;
import com.e3mall.springboot.utils.RedisUtils;

@Service //暴露服务
@Component
@Transactional
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Resource
	private RedisUtils redisUtils;
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Override
	public List<TbContent> getContentList(Long categoryId) {
		//查询缓存
		try{
			//如果缓存中有直接响应结果
			String json = (String) redisUtils.hget(CONTENT_LIST, categoryId+"");
			if(StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//如果没有查询数据库
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
		System.out.println("查询数据库");
		//把结果添加到缓存
		try{
			redisUtils.hset(CONTENT_LIST, categoryId+"",  JsonUtils.objectToJson(contentList));
		}catch(Exception e){
			e.printStackTrace();
		}
		return contentList;
	}
	
	@Override
	public E3Result addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//缓存同步,删除缓存中对应的数据。
		//jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		redisUtils.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return E3Result.ok();
	}

	@Override
	public E3Result editContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(content);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContent(long[] ids) {
		for(long id:ids){
			contentMapper.deleteByPrimaryKey(id);
		}
		return E3Result.ok();
	}

}
