package com.cmpay.service.quartz.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpay.service.quartz.model.CmapySettTotal;
import com.cmpay.service.quartz.service.SettService;

/**
 *  商户结算相关任务
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月10日 下午2:38:56
 *
 */
@Component
public class SettTask {

    private  Logger logger = LoggerFactory.getLogger(SettTask.class);

    @Autowired
    SettService settService;

    @Scheduled(cron = "#{env.cron_merSettTask}")
    public void merSettTask(){
		logger.info("【MerSett】---------商户日终结算任务开始-start----------");
		  settService.doDaliyMerSettDetail();
		logger.info("【MerSett】---------商户日终结算任务结束-end----------");

    }


    @Scheduled(cron = "#{env.cron_merSettTotalTask}")
    public void merSettTotalTask(){
		logger.info("【MerSettTotal】---------商户日终结算汇总任务开始-start----------");
		  settService.doDaliyMerSettTotal();
		logger.info("【MerSettTotal】---------商户日终结算汇总任务结束-end----------");

    }


    @Scheduled(cron = "#{env.cron_doMerSettTask}")
    public void doMerSettTask(){
		logger.info("【doMerSettTask】---------商户日终结算处理任务开始-start----------");
		List<CmapySettTotal> list=settService.queryMerSett();
		if(list==null || list.size()<1){
			logger.info("【doMerSettTask】没有需要处理的数据");
		}else{
           for(CmapySettTotal cmapySettTotal:list){
        	   settService.doMerSett(cmapySettTotal);
           }

		}

		logger.info("【doMerSettTask】---------商户日终结算处理任务结束-end----------");

    }



}
