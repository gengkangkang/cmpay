package com.cmpay.service.quartz.scheduler;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
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

	public TestTask(){
		System.out.println("自动注解测试任务创建成功");

	}

	@Scheduled(cron="0/1 * * * * ?")
	public void testJob() throws InterruptedException{
		System.out.println("---------测试自动注解定时任务-------，"+new Date());
		System.out.println("执行个任务，看会不会调度下一个");
		int j=0;
		int m=0;
		for(int i=0;i<1000000000;i++){
			//哈哈
			j++;
			for(int k=0;k<j;k++){
				m++;
			}
		}
		System.out.println("执行完了,"+j+",m="+m);
	}

	  public static void main(String[] args) throws Exception {
	        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
	        context.start();
	        System.in.read(); // 按任意键退出
	    }

}
