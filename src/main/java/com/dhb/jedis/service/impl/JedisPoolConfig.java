package com.dhb.jedis.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Protocol;

public class JedisPoolConfig extends GenericObjectPoolConfig implements InitializingBean {
	
	private Log log = LogFactory.getLog(JedisPoolConfig.class);
	
	private String configPath;
	
	private String masterName;
	private Set<String> sentinelSet; 
	private int timeout = Protocol.DEFAULT_TIMEOUT;
	private String password;
	private String host;
	private int port;
	private int db;
	private String type;
	
	private void init() throws Exception{
		loadProperties();
	}
	
	private void loadProperties() throws Exception{
		Properties props = new Properties();
		InputStream inStream = null;
		try {
			inStream = this.getClass().getResourceAsStream(configPath);
			props.load(inStream);
			this.setConfigValue(props);
		} catch (Exception e) {
			log.error("load " + configPath + " error !" , e);
			throw new Exception("load " + configPath + " error !" , e);
		} finally{
			if(inStream != null){
				try {
					inStream.close();
				} catch (IOException e) {
					log.error("closed stream error !" , e);
				}
			}
		}
	}
	
	private void setConfigValue(Properties props){
		
		
		String type = props.getProperty("redis.type");
		log.info("redis.type==>>"+type);
		if(StringUtils.isNotBlank(type)){
			this.type = type;
		}
		String host = props.getProperty("redis.host");
		log.info("redis.host==>>"+host);
		if(StringUtils.isNotBlank(host)){
			this.host = host;
		}
		String port = props.getProperty("redis.port");
		log.info("redis.port==>>"+port);
		if(StringUtils.isNotBlank(port)){
			this.port = Integer.parseInt(port);
		}
		String timeout = props.getProperty("redis.timeout");
		log.info("redis.timeout==>>"+timeout);
		if(StringUtils.isNotBlank(timeout)){
			this.timeout = Integer.parseInt(timeout);
		}
		String password = props.getProperty("redis.password");
		log.info("redis.password==>>"+password);
		if(StringUtils.isNotBlank(password)){
			this.password = password;
		}
		
		String masterName = props.getProperty("redis.masterName");
		log.info("redis.masterName==>>"+masterName);
		if(StringUtils.isNotBlank(masterName)){
			this.masterName = masterName;
		}
		
		String sentinels = props.getProperty("redis.sentinels");
		log.info("redis.sentinels==>>"+sentinels);
		if(StringUtils.isNotBlank(sentinels)){
			this.sentinelSet = new HashSet();
			this.sentinelSet.addAll(Arrays.asList(sentinels.split(",")));
		}
		
		String maxTotal = props.getProperty("redis.maxTotal");
		log.info("redis.maxTotal==>>"+maxTotal);
		if(StringUtils.isNotBlank(maxTotal)){
			super.setMaxTotal(Integer.parseInt(maxTotal));
		}
		
		String maxIdle = props.getProperty("redis.maxIdle");
		log.info("redis.maxIdle==>>"+maxIdle);
		if(StringUtils.isNotBlank(maxIdle)){
			super.setMaxIdle(Integer.parseInt(maxIdle));
		}
		
		String testOnBorrow = props.getProperty("redis.testOnBorrow");
		log.info("redis.testOnBorrow==>>"+testOnBorrow);
		if(StringUtils.isNotBlank(testOnBorrow)){
			super.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
		}
		
		String testWhileIdle = props.getProperty("redis.testWhileIdle");
		log.info("redis.testWhileIdle==>>"+testWhileIdle);
		if(StringUtils.isNotBlank(testWhileIdle)){
			super.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
		}
		
		String selectDB = props.getProperty("redis.selectDB");
		log.info("redis.selectDB==>>"+selectDB);
		if(StringUtils.isNotBlank(selectDB)){
			this.setDb(Integer.parseInt(selectDB));
		}
	}
	
	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public Set<String> getSentinelSet() {
		return sentinelSet;
	}

	public void setSentinelSet(Set<String> sentinelSet) {
		this.sentinelSet = sentinelSet;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
	
	

	public int getDb() {
		return db;
	}

	public void setDb(int db) {
		this.db = db;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	//初始加载
	public void afterPropertiesSet() throws Exception {
		this.init();		
	}
}
