package com.e3mall.springboot.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.e3mall.springboot.mapper.TbOrderItemMapper;
import com.e3mall.springboot.mapper.TbOrderMapper;
import com.e3mall.springboot.mapper.TbOrderShippingMapper;
import com.e3mall.springboot.order.pojo.OrderInfo;
import com.e3mall.springboot.order.service.OrderService;
import com.e3mall.springboot.pojo.TbOrderItem;
import com.e3mall.springboot.pojo.TbOrderShipping;
import com.e3mall.springboot.utils.E3Result;
import com.e3mall.springboot.utils.RedisUtils;

@Service
@Component
public class OrderServiceImpl implements OrderService {

	@Resource
	private RedisUtils redisUtils;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_START}")
	private int ORDER_ID_START;
	@Value("${ORDER_DETAIL_ID_GEN_KEY}")
	private String ORDER_DETAIL_ID_GEN_KEY;
	
	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		//生成订单号。使用redis的incr生成。
		if(!redisUtils.hasKey(ORDER_ID_GEN_KEY)){
			redisUtils.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
		}
		String orderId = redisUtils.incr(ORDER_ID_GEN_KEY, 1L).toString();
		//补全orderInfo的属性
		orderInfo.setOrderId(orderId);
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//插入订单表
		orderMapper.insert(orderInfo);
		//向订单明细表插入数据。
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//生成明细id
			String odId = redisUtils.incr(ORDER_DETAIL_ID_GEN_KEY,1L).toString();
			//补全pojo的属性
			tbOrderItem.setId(odId);
			tbOrderItem.setOrderId(orderId);
			//向明细表插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		//向订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		//返回E3Result，包含订单号
		return E3Result.ok(orderId);
	}

}
