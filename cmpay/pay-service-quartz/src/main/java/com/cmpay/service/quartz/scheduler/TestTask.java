package com.cmpay.service.quartz.scheduler;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月21日 下午2:17:47
 *
 */
@Component
public class TestTask {

//	public TestTask(){
//		System.out.println("自动注解测试任务创建成功");
//	}
//
//	@Scheduled(cron="0/3 * * * * ?")
//	public void testJob(){
//		System.out.println("---------测试自动注解定时任务-------");
//	}

	  public static void main(String[] args) throws Exception {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
	        context.start();
	        System.in.read(); // 按任意键退出
	    }

}
