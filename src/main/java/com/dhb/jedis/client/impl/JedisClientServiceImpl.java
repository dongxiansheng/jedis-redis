package com.dhb.jedis.client.impl;


import com.dhb.jedis.client.IJedisClientService;
import com.dhb.jedis.service.IRedisOperation;
import com.dhb.jedis.service.IRedisService;
import com.dhb.jedis.service.impl.JedisPoolConfig;

import redis.clients.jedis.Jedis;


public class JedisClientServiceImpl implements IJedisClientService{
	private  IRedisService<Jedis> jedisService;
	private  JedisPoolConfig poolConfig; 
	
	@Override
	public String getString(String key) {
		final String finalKey = key;
		final IRedisService<Jedis> service = jedisService;
		String object = (String) service.run(new IRedisOperation<Jedis>() {
			@Override
			public Object execute(Jedis jedis) {
				String value = service.getString(jedis, finalKey);
				return value;
			}
		}, poolConfig.getDb());
		return object;
	}

	@Override
	public void saveString(String key, String value) {
		final String finalKey = key;
		final String finalValue = value;
		final IRedisService<Jedis> service = jedisService;
		service.run(new IRedisOperation<Jedis>() {
			@Override
			public Object execute(Jedis jedis) {
				service.set(jedis, finalKey, finalValue);
				return finalValue;
			}
		},  poolConfig.getDb());
	}

	@Override
	public Object getObject(String key) {
		final String finalKey = key;
		final IRedisService<Jedis> service = jedisService;
		Object object = service.run(new IRedisOperation<Jedis>() {
			@Override
			public Object execute(Jedis jedis) {
				Object value = service.getObject(jedis, finalKey);
				return value;
			}
		},  poolConfig.getDb());
		return object;
	}

	@Override
	public void saveObject(String key, Object value) {
		final String finalKey = key;
		final Object finalValue = value;
		final IRedisService<Jedis> service = jedisService;
		service.run(new IRedisOperation<Jedis>() {
			@Override
			public Object execute(Jedis jedis) {
				service.set(jedis, finalKey, finalValue);
				return null;
			}
		},  poolConfig.getDb());
	}

	@Override
	public void saveString(String key, String value, int expire) {
		final String finalKey = key;
		final String finalValue = value;
		final int finalExpire = expire;
		final IRedisService<Jedis> service = jedisService;
		service.run(new IRedisOperation<Jedis>() {
			@Override
			public Object execute(Jedis jedis) {
				service.set(jedis, finalKey, finalValue);
				service.expire(jedis, finalKey, finalExpire);
				return finalValue;
			}
		},  poolConfig.getDb());
	}

	@Override
	public void saveObject(String key, Object value, int expire) {
		final String finalKey = key;
		final Object finalValue = value;
		final int finalExpire = expire;
		final IRedisService<Jedis> service = jedisService;
		service.run(new IRedisOperation<Jedis>() {
			@Override
			public Object execute(Jedis jedis) {
				service.set(jedis, finalKey, finalValue);
				service.expire(jedis, finalKey, finalExpire);
				return null;
			}
		},  poolConfig.getDb());
	}


	@Override
	public void expire(String key, int expire) {
		final String finalKey = key;
		final int finalExpire = expire;
		final IRedisService<Jedis> service = jedisService;
		service.run(new IRedisOperation<Jedis>() {
			@Override
			public Object execute(Jedis jedis) {
				service.expire(jedis, finalKey, finalExpire);
				return null;
			}
		},  poolConfig.getDb());
	}

	public IRedisService<Jedis> getJedisService() {
		return jedisService;
	}

	public void setJedisService(IRedisService<Jedis> jedisService) {
		this.jedisService = jedisService;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	
}
