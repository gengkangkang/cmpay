package com.cmpay.service.quartz.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpay.service.quartz.service.PaymentService;



/**
 * 微信服务相关调度任务
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月21日 下午2:07:41
 *
 */
@Component
public class WeixinTask {

    private  Logger logger = LoggerFactory.getLogger(WeixinTask.class);


	public WeixinTask() {
		System.out.println("-------------------统一支付平台之微信支付相关Task初始化-----------------------------");
	}

    @Autowired
	private PaymentService paymentService;


	@Scheduled(cron = "#{env.cron_wx_orderQueryTask}")
	public void wxOrderQuery() {
		logger.info("定时任务之微信支付查询任务开始[{}]",new Date());
		paymentService.QueryWXOrder();
		logger.info("定时任务之微信支付查询任务结束[{}]",new Date());
	}
}
