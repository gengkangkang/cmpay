package com.cmpay.service.trade.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年2月14日 下午5:19:33
 *
 */

public class DisconfDemoTest {

    public static void main(String[] args) throws Exception {

    	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
	        context.start();

//	        TestDisconfService testDisconfService = (TestDisconfService)context.getBean("testDisconfService"); // 获取远程服务代理
//	        testDisconfService.testPro();

	        System.in.read();

    }
}
