package com.e3mall.springboot.sso.service;

import com.e3mall.springboot.pojo.TbUser;
import com.e3mall.springboot.utils.E3Result;

public interface RegisterService {
	
	E3Result checkData(String param,int type);
	E3Result register(TbUser user);
}
