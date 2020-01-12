package com.e3mall.springboot.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.e3mall.springboot.cart.service.CartService;
import com.e3mall.springboot.pojo.TbItem;
import com.e3mall.springboot.pojo.TbUser;
import com.e3mall.springboot.service.ItemService;
import com.e3mall.springboot.sso.service.TokenService;
import com.e3mall.springboot.utils.CookieUtils;
import com.e3mall.springboot.utils.E3Result;
import com.e3mall.springboot.utils.JsonUtils;

/**
 *  购物车处理Controller
 * @author fangzf
 * @date:  2019年6月29日
 */
@Controller
public class CartController {

	@Reference
	private ItemService itemService;
	
	@Reference
	private CartService cartService;
	
	@Reference
	private TokenService tokenService;
	
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;
	
	@RequestMapping("/cart/add/{itemId}.html")
	public String addCart(@PathVariable Long itemId,@RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request,HttpServletResponse response){
		//判断用户是否登录
		//TbUser user = (TbUser) request.getAttribute("user");
		TbUser user = getUser(request);
		//如果是登录状态，把购物车写入redis
		if(user!=null){
			//保存到服务端
			cartService.addCart(user.getId(), itemId, num);
			//返回逻辑视图
			return "cartSuccess";
		}
		//如果未登录使用cookie
		//从cookie中取购物车列表
		boolean flag = false;
		List<TbItem> cartList = getCartListFromCookie(request);
		for (TbItem tbItem : cartList) {
			//如果存在数量相加
			if(tbItem.getId() == itemId.longValue()){
				flag = true;
				//找到商品，数量相加
				tbItem.setNum(tbItem.getNum() + num);
				//跳出循环
				break;
			}
		}
		//如果不存在
		if(!flag){
			//根据商品id查询商品信息。得到一个TbItem对象
			TbItem tbItem = itemService.getItemById(itemId);
			//设置商品数量
			tbItem.setNum(num);
			//取一张图片
			String image = tbItem.getImage();
			if(StringUtils.isNotBlank(image)){
				tbItem.setImage(image.split(",")[0]);
			}
			//把商品添加到商品列表
			cartList.add(tbItem);
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		return "cartSuccess";
	}
	
	/**
	 * @Title:  从cookie中取购物车列表的处理
	 * @author: fangzf
	 * @param:  @return
	 * @date:   2019年6月27日
	 */
	public List<TbItem> getCartListFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, "cart", true);
		//判断json是否为空
		if(StringUtils.isBlank(json)){
			return new ArrayList<>();
		}
		//把json转换成商品列表
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
	/**
	 * @Title:  展示购物车列表
	 * @author: fangzf
	 * @param:  @param request
	 * @param:  @return
	 * @date:   2019年6月29日
	 */
	@RequestMapping("/cart/cart.html")
	public String showCatList(HttpServletRequest request,HttpServletResponse response){
		
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		
		//判断用户是否为登录状态
		TbUser user = getUser(request);
		//如果是登录状态
		if(user != null){
			//从cookie中取购物车列表
			//如果不为空，把cookie中的购物车商品和服务端的购物车商品合并。
			cartService.mergeCart(user.getId(),cartList);
			//把cookie中的购物车删除
			CookieUtils.deleteCookie(request, response, "cart");
			//从服务端取购物车列表
			cartList = cartService.getCartList(user.getId());
		}
		//把列表传递给页面
		request.setAttribute("cartList", cartList);
		return "cart";
	}
	
	/**
	 * @Title:  更新购物车商品数量
	 * @author: fangzf
	 * @param:  @param itemId
	 * @param:  @param num
	 * @param:  @param request
	 * @param:  @param response
	 * @param:  @return
	 * @date:   2019年6月27日
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
			HttpServletRequest request,HttpServletResponse response){
		//判断用户是否为登录状态
		TbUser user= getUser(request);
		if(user != null){
			return cartService.updateCartNum(user.getId(), itemId, num);
		}
		List<TbItem> cartList = getCartListFromCookie(request);
		for (TbItem tbItem : cartList) {
			if(tbItem.getId().longValue() == itemId){
				//更新数量
				tbItem.setNum(num);
				break;
			}
		}
		//把购物车列表写回cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		return E3Result.ok();
	}
	
	/**
	 * @Title:  删除购物车商品
	 * @author: fangzf
	 * @param:  @param itemId
	 * @param:  @param request
	 * @param:  @param response
	 * @param:  @return
	 * @date:   2019年6月27日
	 */
	@RequestMapping("/cart/delete/{itemId}.html")
	public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
		//判断用户是否为登录状态
		TbUser user= getUser(request);
		if(user != null){
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		List<TbItem> cartList = getCartListFromCookie(request);
		for (TbItem tbItem : cartList) {
			if(tbItem.getId().longValue() == itemId){
				//删除商品
				cartList.remove(tbItem);
				break;
			}
		}
		//把购物车列表写回cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		return "redirect:/cart/cart.html";
	}
	
	/**
	 * 获得用户信息
	 * @param:	@param request
	 * @param:	@return     
	 * @return:	TbUser  
	 * @author:	fangzf
	 * @date:  	2020年1月12日 下午2:02:27  
	 * @throws
	 */
	public TbUser getUser(HttpServletRequest request){
		//1.从cookie中取token
		String token = CookieUtils.getCookieValue(request, "token");
		//2.如果没有token，未登录状态，直接放行
		if(StringUtils.isBlank(token)){
			return null;
		}
		//3.取到token，需要调用sso系统的服务，根据token取用户信息
		E3Result e3Result = tokenService.getUserByToken(token);
		//4.没有取到用户信息。登录过期，直接放行。
		if(e3Result.getStatus() != 200){
			return null;
		}
		//5.取到用户信息。登录状态。
		TbUser user =  (TbUser) e3Result.getData();
		return user;
	}
}
