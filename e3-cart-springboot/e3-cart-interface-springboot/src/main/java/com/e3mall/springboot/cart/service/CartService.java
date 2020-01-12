package com.e3mall.springboot.cart.service;

import java.util.List;

import com.e3mall.springboot.pojo.TbItem;
import com.e3mall.springboot.utils.E3Result;

public interface CartService {

	E3Result addCart(long userId,long itemId,int num);
	E3Result mergeCart(long userId,List<TbItem> itemList);
	List<TbItem> getCartList(long userId);
	E3Result updateCartNum(long userId,long itemId,int num);
	E3Result deleteCartItem(long userId,long itemId);
	E3Result clearCartItem(long userId);
}
