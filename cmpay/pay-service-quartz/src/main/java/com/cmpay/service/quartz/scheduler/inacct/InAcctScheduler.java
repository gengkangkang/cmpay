package com.cmpay.service.quartz.scheduler.inacct;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 入账任务启动类
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月22日 下午5:32:21
 *
 */
public class InAcctScheduler {

	private Logger logger=LoggerFactory.getLogger(getClass());
	private static InAcctScheduler inAcctScheduler;
    public static InAcctScheduler getInstance(){
    	if(inAcctScheduler==null){
    		inAcctScheduler=new InAcctScheduler();
    	}
    	return inAcctScheduler;
    }

    public void start(){
    	logger.info("=======================inAcct入账任务线程启动-start=========================");
          //启动入账线程
    	ExecutorService executorService=Executors.newSingleThreadExecutor();
    	executorService.execute(new InAcctMainTask());
    	logger.info("=======================inAcct入账任务线程启动结束-end===========================");

    }

}
