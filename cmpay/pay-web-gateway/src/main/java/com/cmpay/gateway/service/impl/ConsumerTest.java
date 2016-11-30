package com.cmpay.gateway.service.impl;

import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cmpay.facade.trade.UpayService;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年9月1日 上午11:19:14
 *
 */
public class ConsumerTest {

	public static void main(String[] args) {
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"conf/spring-consumer.xml"});
	        context.start();

	        UpayService upayService = (UpayService)context.getBean("upayService"); // 获取远程服务代理
	                                        // merchantId,  userId, inchannel,authChannel,cardNo, cardType,idNo,idType,        name,  bankMobile, String bankCode,String terminalType
	        Map<String,String> map=upayService.payAuth("800010000001", "0000", "0000", "CMPAY0001","6217003690002667300", "DEBIT", "350801198910081476", "I", "中国", "18721359153", "0005", "01");

	        System.out.println("map======"+map.toString()); // 显示调用结果



	}

}
