package com.cmpay.service.quartz.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpay.common.util.RedisConstants;
import com.cmpay.common.util.RedisUtil;

/**
 *  缓存相关任务
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年2月23日 上午11:34:56
 *
 */
@Component
public class RedisTask {

    private  Logger logger = LoggerFactory.getLogger(RedisTask.class);
    @Autowired
    RedisUtil redisUtil;

    @Scheduled(cron = "#{env.cron_cleanDayAmountLimt}")
    public void cleanDayAmountLimt(){
    	logger.info("[cleanDayAmountLimt]定时任务之清理单日限额任务开始[{}]",new Date());

    	try{
    		redisUtil.delByPreStr(RedisConstants.CMPAY_DAYAMOUNT_);
    	}catch(Exception e){
    		logger.info("清理单日限额缓存出现异常！",e);
    	}

	    logger.info("[cleanDayAmountLimt]定时任务之清理单日限额任务结束[{}]",new Date());
    }

    @Scheduled(cron = "#{env.cron_cleanMonthAmountLimt}")
    public void cleanMonthAmountLimt(){
    	logger.info("[cleanMonthAmountLimt]定时任务之清理单月限额任务开始[{}]",new Date());

    	try{
    		redisUtil.delByPreStr(RedisConstants.CMPAY_MONTHAMOUNT_);
    	}catch(Exception e){
    		logger.info("清理单月限额缓存出现异常！",e);
    	}

	    logger.info("[cleanMonthAmountLimt]定时任务之清理单月限额任务结束[{}]",new Date());
    }

}
