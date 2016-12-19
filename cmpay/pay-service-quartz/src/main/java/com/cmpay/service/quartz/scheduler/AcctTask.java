package com.cmpay.service.quartz.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * 采用多线程异步入账，提高入账时效性
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月16日 下午3:28:04
 *
 */
@Component
public class AcctTask {

	   @PostConstruct
       public void initAcctTask(){
    	   System.out.println("-------------------初始化账务轮询任务-------------------------");
       }

       @PreDestroy
	   public void destoryAcctTask(){
		   System.out.println("----------------销毁轮询任务--------------------");
	   }
}
