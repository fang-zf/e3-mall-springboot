package com.e3mall.springboot.order.service;

import com.e3mall.springboot.order.pojo.OrderInfo;
import com.e3mall.springboot.utils.E3Result;

public interface OrderService {
	
	E3Result createOrder(OrderInfo orderInfo);
	
}
