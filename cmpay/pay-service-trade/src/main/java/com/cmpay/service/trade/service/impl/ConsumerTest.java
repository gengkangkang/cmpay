package com.cmpay.service.trade.service.impl;

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
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-consumer.xml"});
	        context.start();

	        UpayService upayService = (UpayService)context.getBean("upayService1"); // 获取远程服务代理
	                                        // merchantId,  userId, inchannel,authChannel,cardNo, cardType,idNo,idType,        name,  bankMobile, String bankCode,String terminalType
//	        Map<String,String> map=upayService.payAuth("800010000001", "0000", "0000", "CMPAY0001","6217003690002667300", "DEBIT", "350801198910081476", "I", "中国", "18721359153", "0005", "01");
//	        Map<String,Object> map=upayService.payCut("800010000001", "0000", "111", "1.50", "6217003690002667300", "2016001", "CMAPI0001","01", "123");

//	        System.out.println("map======"+map.toString()); // 显示调用结果



	        //测试单笔代付
	        Map<String,Object> map=upayService.SinPay("800010000001", "0000", "CMPAY0001", "6222620140009946277", "测试", "0005", "建行", "0.61", "333", "201704170004", "02", "notifyUrl", "10.17.5.102", "上海", "黄埔区", "remark");
             System.out.println("map===="+map.toString());

	}

}
