package com.cmpay.service.trade.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年9月1日 上午10:54:28
 *
 */
public class ProviderTest {

	public static void main(String[] args) throws Exception {
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
	        context.start();

	        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
          System.out.println("-------------------------开始模拟调用---------------------------------------");
//	        UpayService upayService = (UpayService)context.getBean("upayService1"); // 获取远程服务代理
            // merchantId,  userId, inchannel,authChannel,cardNo, cardType,idNo,idType,        name,  bankMobile, String bankCode,String terminalType
           //Map<String,String> map=upayService.payAuth("800010000001", "0000", "0000", "CMPAY0001","6217003690002667300", "DEBIT", "350801198910081476", "I", "中国", "18721359153", "0005", "01");
	       // ,String idNo,String idType,String name,String bankMobile,String bankCode,String bankName

//	        Map<String,Object> map=upayService.payCut("800010000001", "0000", "111", "1.50", "6222620140009946277", "20160014", "CMPAY0002","01", "192.168.0.1","410221","I","测试","18721359153","0005","建行");

//	        Map<String,Object> map=upayService.payCut("800010000001", "0000", "111", "1.50", "6222620140009946277", "2016001422", "","01", "192.168.0.1","410221","I","测试","18721359153","0005","建行");
//
//          System.out.println("map======"+map.toString()); // 显示调用结果


	}

}
