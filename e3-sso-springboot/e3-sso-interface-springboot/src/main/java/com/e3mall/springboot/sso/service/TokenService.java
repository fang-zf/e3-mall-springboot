package com.e3mall.springboot.sso.service;

import com.e3mall.springboot.utils.E3Result;

public interface TokenService {

	E3Result getUserByToken(String token);
	
}
