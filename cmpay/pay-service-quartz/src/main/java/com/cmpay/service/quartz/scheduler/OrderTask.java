package com.cmpay.service.quartz.scheduler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpay.service.quartz.model.CmapyCutOrder;
import com.cmpay.service.quartz.model.CmapyOrderRefund;
import com.cmpay.service.quartz.service.PaymentService;




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

	@Scheduled(cron = "#{env.cron_doCutOrderTask}")
	public void CutOrderTask(){
		logger.info("【CutOrder】---------代扣订单轮询任务开始-start----------");

		List<CmapyCutOrder> orderList=paymentService.queryCutOrderList();
		for(CmapyCutOrder cmapyCutOrder:orderList){
			paymentService.doCutOrderTask(cmapyCutOrder);
		}

		logger.info("【CutOrder】---------代扣订单轮询任务结束-end----------");


	}


	@Scheduled(cron = "#{env.cron_doRefundOrderTask}")
	public void RefundOrderTask(){
		logger.info("【Refund】---------退款订单轮询任务开始-start----------");
		List<CmapyOrderRefund> rlist=paymentService.queryRefundOrderList();
         if(rlist==null || rlist.size()<1){
        	 logger.info("退款订单轮询任务没有要处理的订单！！！");
         }else{
            for(CmapyOrderRefund cmapyOrderRefund:rlist){

            	paymentService.doRefundOrderTask(cmapyOrderRefund);
            }


         }

		logger.info("【Refund】---------退款订单轮询任务结束-end----------");


	}


}
