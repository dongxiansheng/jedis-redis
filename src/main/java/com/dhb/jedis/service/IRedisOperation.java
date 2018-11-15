package com.dhb.jedis.service;


public interface IRedisOperation<T> {
	public Object execute(T jedis); 
}
