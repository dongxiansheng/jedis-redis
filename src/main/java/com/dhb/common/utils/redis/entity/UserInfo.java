package com.dhb.common.utils.redis.entity;

import java.io.Serializable;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 5413235005093077112L;
	private String userId;
	private String userName;
	private String sex;
	private int age;
	
	public UserInfo(){
		
	}
	public UserInfo(String userId, String userName, String sex, int age) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.sex = sex;
		this.age = age;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", sex=" + sex + ", age=" + age + "]";
	}
	
}
