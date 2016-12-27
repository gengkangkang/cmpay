package com.cmpay.service.quartz.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cmpay.service.quartz.scheduler.inacct.InAcctScheduler;
import com.cmpay.service.quartz.util.AcctConstant;

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
	private Logger logger=LoggerFactory.getLogger(getClass());

	   @PostConstruct
       public void initAcctTask(){
		   logger.info("-------------------初始化账务轮询任务-------------------------");
		   InAcctScheduler.getInstance().start();
       }

       @PreDestroy
	   public void destoryAcctTask(){
    	   logger.info("----------------销毁轮询任务--------------------");
    	   logger.info("线程stop开始。开始销毁入账线程！");
    	   AcctConstant.inacct_start=false;
    	   logger.info("线程stop结束。入账线程标志位：【{}】", AcctConstant.inacct_start);
	   }
}
