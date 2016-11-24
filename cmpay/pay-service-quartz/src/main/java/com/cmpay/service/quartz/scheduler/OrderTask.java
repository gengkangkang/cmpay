package com.cmpay.service.quartz.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpay.weixin.service.PaymentService;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月23日 上午10:02:16
 *
 */
@Component
public class OrderTask {

    private  Logger logger = LoggerFactory.getLogger(OrderTask.class);


	public OrderTask() {
		System.out.println("-------------------统一支付平台之订单相关Task初始化-----------------------------");
	}

    @Autowired
	private PaymentService paymentService;


	@Scheduled(cron = "#{env.cron_doExpireOrder}")
	public void ExpireOrderJob() {
		logger.info("定时任务之处理过期订单任务开始[{}]",new Date());
		     paymentService.doExpireOrder();
		logger.info("定时任务之处理过期订单任务结束[{}]",new Date());
	}
}
