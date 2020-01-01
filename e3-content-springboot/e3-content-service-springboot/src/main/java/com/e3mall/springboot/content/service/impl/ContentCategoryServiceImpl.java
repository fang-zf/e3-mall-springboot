package com.e3mall.springboot.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.e3mall.springboot.content.service.ContentCategoryService;
import com.e3mall.springboot.mapper.TbContentCategoryMapper;
import com.e3mall.springboot.pojo.EasyUITreeNode;
import com.e3mall.springboot.pojo.TbContentCategory;
import com.e3mall.springboot.pojo.TbContentCategoryExample;
import com.e3mall.springboot.utils.E3Result;

@Service //暴露服务
@Component
@Transactional
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parenId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parenId);
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : catList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		//创建一个tb_content_category表对应的pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		//设置pojo的属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//1(正常),2(删除)
		contentCategory.setStatus(1);
		//新添加的节点一定是叶子节点
		contentCategory.setIsParent(false);
		//默认排序就是1
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入数据库
		contentCategoryMapper.insert(contentCategory);
		//判断父节点的isparent属性。如果不是true改为true
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			//更新到数数据库
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回结果，返回E3Result，包含pojo
		return E3Result.ok(contentCategory);
	}

	@Override
	public E3Result updateContentCategory(long id, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return E3Result.ok(contentCategory);
	}

	@Override
	public E3Result deleteContentCategory(long id) {
		//判断该节点是否是叶子节点 
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(id);
		if(contentCategoryMapper.countByExample(example)>0){
			return E3Result.build(500, "请先删除子节点！");
		}else{
			//删除前获得父节点 
			TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
			Long parentId = contentCategory.getParentId();
			//删除叶子节点
			contentCategoryMapper.deleteByPrimaryKey(id);
			//判断父级节点是否还有叶子节点  没有就更新IsParent状态
			TbContentCategoryExample exampleParent = new TbContentCategoryExample();
			example.createCriteria().andParentIdEqualTo(parentId);
			if(contentCategoryMapper.countByExample(exampleParent)==0){
				TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
				//设置是否为父类目状态
				parentContentCategory.setIsParent(false);
				//更新
				contentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
			}
		}
		return E3Result.ok();
	}
	
	

}
