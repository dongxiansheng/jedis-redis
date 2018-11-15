package com.dhb.jedis.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.dhb.common.utils.SerializeUtil;
import com.dhb.jedis.service.IRedisOperation;
import com.dhb.jedis.service.IRedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

public class JedisService  implements IRedisService<Jedis>,InitializingBean  {

	private Pool<Jedis> jedisPool;

	private JedisPoolConfig poolConfig;

	String type;
	
	public void init() {
		
		this.type = poolConfig.getType(); 
		if ("sentinel".equalsIgnoreCase(this.type)) {
			this.jedisPool = new JedisSentinelPool(poolConfig.getMasterName(),
					poolConfig.getSentinelSet(), poolConfig,
					poolConfig.getTimeout(), poolConfig.getPassword());
		} else {
			String password = poolConfig.getPassword(); 
			if (password == null)
				this.jedisPool = new JedisPool(poolConfig, poolConfig.getHost(), 
						poolConfig.getPort(), poolConfig.getTimeout());
			else {
				this.jedisPool = new JedisPool(poolConfig, poolConfig.getHost(), 
						poolConfig.getPort(), poolConfig.getTimeout(),password);
			}
		}
	}

	/**
	 * 通过jedis连接池获取jedis
	 * 
	 * @return
	 */
	private Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}

	/**
	 * 释放jedis连接
	 * 
	 * @param jedis
	 * @param error
	 */
	private void releaseJedis(Jedis jedis, boolean error) {
		if (error) {
			jedisPool.returnBrokenResource(jedis);
		} else {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 销毁jedis连接池
	 */
	public void destory() {
		if (jedisPool != null) {
			jedisPool.destroy();
		}
	}

	@Override
	public void expire(Jedis jedis, String key, int seconds) {
		if (seconds <= 0 || StringUtils.isBlank(key)) {
			return;
		}
		jedis.expire(key, seconds);
	}

	@Override
	public void del(Jedis jedis, String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		jedis.del(key);
	}

	@Override
	public void set(Jedis jedis, String key, String value) {
		if (StringUtils.isBlank(key))
			return;
		jedis.set(key, value);
	}

	@Override
	public void set(Jedis jedis, String key, int value) {
		this.set(jedis, key, String.valueOf(value));
	}

	@Override
	public void set(Jedis jedis, String key, double value) {
		this.set(jedis, key, String.valueOf(value));
	}

	@Override
	public void set(Jedis jedis, String key, float value) {
		this.set(jedis, key, String.valueOf(value));
	}

	@Override
	public void set(Jedis jedis, String key, long value) {
		this.set(jedis, key, String.valueOf(value));
	}

	@Override
	public void set(Jedis jedis, String key, Object value) {
		if (StringUtils.isBlank(key) ||value==null || StringUtils.isBlank(value.toString()))
			return;
		byte[] keyBytes = key.getBytes();
		jedis.set(keyBytes, SerializeUtil.serialize(value));
	}

	@Override
	public String getString(Jedis jedis, String key) {
		if (StringUtils.isBlank(key))
			return null;
		return jedis.get(key);
	}

	@Override
	public Integer getInteger(Jedis jedis, String key) {
		if (StringUtils.isBlank(key))
			return null;
		return Integer.valueOf(jedis.get(key));
	}

	@Override
	public Long getLong(Jedis jedis, String key) {
		if (StringUtils.isBlank(key))
			return null;
		return Long.valueOf(jedis.get(key));
	}

	@Override
	public Float getFloat(Jedis jedis, String key) {
		if (StringUtils.isBlank(key))
			return null;
		return Float.valueOf(jedis.get(key));
	}

	@Override
	public Double getDouble(Jedis jedis, String key) {
		if (StringUtils.isBlank(key))
			return null;
		return Double.valueOf(jedis.get(key));
	}

	@Override
	public Object getObject(Jedis jedis, String key) {
		if (StringUtils.isBlank(key))
			return null;
		byte[] bytes = jedis.get(key.getBytes());
		if (bytes != null) {
			return SerializeUtil.deserialize(bytes);
		}
		return null;
	}

	@Override
	public Object run(IRedisOperation<Jedis> redisOperation, int db) {
		Object obj = null;
		boolean error = true;
		Jedis jedis = getJedis();
		jedis.select(db);
		try {
			obj = redisOperation.execute(jedis);
			error = false;
		} finally {
			this.releaseJedis(jedis, error);
		}
		return obj;
	}

	@Override
	public boolean exists(Jedis jedis, String key) {
		return jedis.exists(key);
	}

	@Override
	public boolean exists(Jedis jedis, byte[] keyBytes) {
		return jedis.exists(keyBytes);
	}

	public Pool<Jedis> getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
		
	}

	/*public static void main(String[] args){
		JedisPoolConfig jpf = new JedisPoolConfig();
		try {
			jpf.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pool<Jedis>  jedisPool = new JedisPool(jpf, jpf.getHost(), 
				jpf.getPort(), jpf.getTimeout());
		Jedis j =jedisPool.getResource();
	}*/

	
	
	public static Jedis getRedis(){
		Jedis j = new Jedis("192.168.203.111");
		j.auth("redis");
		return j;
	}
	public static void main(String[] args){
		
		Jedis j = getRedis();
		String out ;
		j.set("hello", "world");
		out = j.get("hello");
		System.out.print(out);
	}
	 
}
