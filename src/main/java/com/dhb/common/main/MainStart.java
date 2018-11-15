package com.dhb.common.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dhb.common.utils.redis.service.RedisSwitchManager;

public class MainStart {
	public static void main(String[] args) {
		MainStart server = new MainStart();
		System.out.println("开始启动MainStart....");
		server.startXmlServer();
	}

	public void startXmlServer() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:/redis/*.xml" });
		context.refresh();
		RedisSwitchManager bean = (RedisSwitchManager) context.getBean("redisSwitchManager");
		/*String str = bean.method1();
		System.out.println(str);*/
		bean.method2();
//		context.refresh();
	}

}
