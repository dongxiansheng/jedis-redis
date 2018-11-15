package com.dhb.jedis.client;


public interface IJedisClientService {
	
	public String getString(String key);
	
	public void saveString(String key,String value);
	
	public void saveString(String key,String value,int expire);
	
	public Object getObject(String key);
	
	public void saveObject(String key,Object value);
	
	public void saveObject(String key,Object value,int expire);
	
	public void expire(String key,int expire);
}
