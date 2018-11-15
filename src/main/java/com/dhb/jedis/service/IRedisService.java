package com.dhb.jedis.service;


public interface IRedisService<T> {

	public void expire(T jedis, String key, int seconds);
	
	public void del(T jedis,String key);
	
	public boolean exists(T jedis,String key);
	
	public boolean exists(T jedis,byte[] keyBytes);
	
	public void set(T jedis, String key ,String value);
	
	public void set(T jedis, String key ,int value);
	
	public void set(T jedis, String key ,double value);
	
	public void set(T jedis, String key ,float value);
	
	public void set(T jedis, String key ,long value);
	
	public void set(T jedis, String key ,Object value);
	
	public String getString(T jedis, String key);
	
	public Integer getInteger(T jedis,String key);
	
	public Long getLong(T jedis,String key);
	
	public Float getFloat(T jedis,String key);
	
	public Double getDouble(T jedis,String key);
	
	public Object getObject(T jedis,String key);
	
	public Object run(IRedisOperation<T> redisOperation,int db);
}
