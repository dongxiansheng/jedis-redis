package com.dhb.common.utils.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhb.common.utils.redis.entity.UserInfo;
import com.dhb.jedis.client.impl.JedisClientServiceImpl;

public class RedisSwitchManager {
	protected static Logger logger = LoggerFactory.getLogger(RedisSwitchManager.class);
	private JedisClientServiceImpl jedisClientService;

	
	
	public String method1(){
		jedisClientService.saveString("first", "diyi");
		String first = jedisClientService.getString("first");
		return first;
	}
	public void method2(){
		UserInfo user = new UserInfo("1001","小品","男",23);
		jedisClientService.saveObject(user.getUserId(), user);
		UserInfo user2 = (UserInfo) jedisClientService.getObject("1001");
		System.out.println(user2.toString());
		
	}
	
	
	public void setJedisClientService(JedisClientServiceImpl jedisClientService) {
		this.jedisClientService = jedisClientService;
	}	
}
