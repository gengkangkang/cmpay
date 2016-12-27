package com.cmpay.service.quartz.scheduler.inacct;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmpay.common.util.CmpayUtils;
import com.cmpay.service.quartz.dao.CmapyCutOrderMapper;
import com.cmpay.service.quartz.model.CmapyCutOrder;
import com.cmpay.service.quartz.scheduler.SpringContextUtil;
import com.cmpay.service.quartz.util.AcctConstant;

/**
 *  入账主线程
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月22日 下午5:42:00
 *
 */
public class InAcctMainTask implements Runnable {

	private Logger logger=LoggerFactory.getLogger(getClass());
    private ThreadPoolExecutor taskThreadPool;
    CmapyCutOrderMapper cmapyCutOrderMapper;

	public InAcctMainTask(){
    	taskThreadPool=(ThreadPoolExecutor) Executors.newFixedThreadPool(AcctConstant.ACCTPOOLSIZE);
//    	cmapyCutOrderMapper=(CmapyCutOrderMapper) SpringContextUtil.getBean("cmapyCutOrderMapper");
    }


	@Override
	public void run() {
		logger.info("[InAcct]入账轮询线程启动start》》》》》》》》》》》");
        while(AcctConstant.inacct_start){
         CmpayUtils.threadSleep(AcctConstant.SLEEPTIME);
         isMainTaskStart();
         if(taskThreadPool.getQueue().size()>0){
        	 logger.info("[InAcct]入账轮询线程队列中还有数据continue");
        	 continue;
         }

         //实现入账主逻辑
           handTask();
        }

		logger.info("[InAcct]入账轮询线程结束end》》》》》》》》》》》");


	}

	private void isMainTaskStart(){
		if(taskThreadPool.isShutdown()){
			AcctConstant.inacct_start=false;
			logger.info("入账轮询主线程ThreadPoolExecutor已关闭,关闭InAcctMainTask循环===========");
		}
	}


	private void handTask() {
		try {
//			CmapyCutOrderExample cmapyCutOrderExample=new CmapyCutOrderExample();
//			cmapyCutOrderExample.or().andPayStatusEqualTo(PayStatusEnum.SUCC.name()).andIsAcctEqualTo("1").andInAcctEqualTo("0");
			cmapyCutOrderMapper=(CmapyCutOrderMapper) SpringContextUtil.getBean("cmapyCutOrderMapper");
            List<CmapyCutOrder> taskList=cmapyCutOrderMapper.selectInAcctList();
			if (taskList == null || taskList.size() == 0) {
				logger.info("【InAcct】入账轮询线程没有需要处理的数据");
				return;
			}
			for (CmapyCutOrder cmapyCutOrder : taskList) {
				taskThreadPool.execute(new InAcctDoTask(cmapyCutOrder));
			}
		} catch (Exception e) {
			logger.error("入账任务出现异常！！！！！！");
			e.printStackTrace();
		}

	}

}
