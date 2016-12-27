package com.cmpay.service.quartz.scheduler.inacct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.HttpClientUtil;
import com.cmpay.service.quartz.dao.CmapyCutOrderMapper;
import com.cmpay.service.quartz.model.CMPAY0001;
import com.cmpay.service.quartz.model.CMPAY0001Rs;
import com.cmpay.service.quartz.model.CmapyCutOrder;
import com.cmpay.service.quartz.model.CommonRqHdr;
import com.cmpay.service.quartz.scheduler.SpringContextUtil;
import com.cmpay.service.quartz.util.AcctConstant;
import com.cmpay.service.quartz.util.CMPAY0001Util;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月26日 上午10:16:09
 *
 */

public class InAcctDoTask implements Runnable {

	private Logger logger=LoggerFactory.getLogger(getClass());
	private CmapyCutOrder cmapyCutOrder;
    CmapyCutOrderMapper cmapyCutOrderMapper;

	private String coreurl;

    public InAcctDoTask(CmapyCutOrder cco){
    	this.cmapyCutOrder=cco;
    }


	@Override
	public void run() {
		long starttime=System.currentTimeMillis();
		if(cmapyCutOrder==null){
			logger.info("cmapyCutOrder为空，直接return");
			return;
		}
		cmapyCutOrderMapper=(CmapyCutOrderMapper) SpringContextUtil.getBean("cmapyCutOrderMapper");
		coreurl=SpringContextUtil.getPropertieByName("coreurl");

		logger.info("【InAcct】 orderId=[{}],userId=[{}],amount=[{}]开始入账",cmapyCutOrder.getOrderId(),cmapyCutOrder.getUserId(),cmapyCutOrder.getTransAmt());
        //入账前先更新入账状态，防止异常导致重复入账
		cmapyCutOrder.setInAcct("2");
		cmapyCutOrderMapper.updateByPrimaryKeySelective(cmapyCutOrder);

		//调用入账
		taskHandle(cmapyCutOrder);

		long endtime=System.currentTimeMillis();
		logger.info("【InAcct】 orderId=[{}]入账结束，耗时【{}】",cmapyCutOrder.getOrderId(),(endtime-starttime));

	}

	private void taskHandle(CmapyCutOrder cmapyCutOrder){
		long starttime=System.currentTimeMillis();
		try{
			doInAcct(cmapyCutOrder);
		}catch(Exception e){
			logger.error("【InAcct】入账订单orderid=[{}]入账失败，原因：入账信息发送失败,请查看日志,>>>>更新入账订单状态为入账失败",cmapyCutOrder.getOrderId());
			cmapyCutOrder.setInAcct("3");
			e.printStackTrace();
		}
    	logger.info("===========================【InAcct】入账处理时间为=="+(System.currentTimeMillis()-starttime));
		//更新订单入账状态
		try{
			cmapyCutOrderMapper.updateByPrimaryKeySelective(cmapyCutOrder);
		}catch(Exception e){
			logger.error("====orderid:" + cmapyCutOrder.getOrderId() + "入账状态:" + cmapyCutOrder.getInAcct()
					+ "，原因：更新入账信息到数据库失败,======");
			e.printStackTrace();
		}
		System.out.println("===========================【InAcct】入账任务处理时间+更新状态时间为=="+(System.currentTimeMillis()-starttime));

	}

	private void doInAcct(CmapyCutOrder cmapyCutOrder){
		try{

	        CommonRqHdr com=new CommonRqHdr();
	        com.setRqUID(CmpayUtils.getUUID());
//	        com.setRqUID(cmapyCutOrder.getOrderId());
	        com.setSPName(AcctConstant.spName);
	        com.setNumTranCode(AcctConstant.NumTranCode);
	        com.setClearDate(CmpayUtils.getCurrentTime("yyyyMMdd"));
	        com.setTranDate(CmpayUtils.getCurrentTime("yyyyMMdd"));
	        com.setTranTime(CmpayUtils.getCurrentTime("HHmmss"));
	        com.setChannelId(AcctConstant.channelId);
	        com.setVersion(AcctConstant.version);


	        CMPAY0001 pay=new CMPAY0001();
	        pay.setCommonRqHdr(com);
            pay.setMerId(cmapyCutOrder.getMerNo());
            pay.setUserId(cmapyCutOrder.getUserId());
            pay.setAmount(cmapyCutOrder.getTransAmt().toString());
            pay.setOrderId(cmapyCutOrder.getOrderId());

            String xml=CMPAY0001Util.toXML(pay);
            logger.info("请求xml=[{}]",xml);

			String back = HttpClientUtil.postString(coreurl, xml, AcctConstant.UTF8, AcctConstant.UTF8);
            logger.info("返回报文back=[{}]",back);

	        CMPAY0001Rs core=CMPAY0001Util.fromXML(back);

	        if(StringUtils.equals("0000", core.getCommonRsHdr().getStatusCode())&& StringUtils.equals("SUCC", core.getTranStatus())){
	        	cmapyCutOrder.setInAcct("1");
	        }else{
	        	logger.info("入账失败");
	        	cmapyCutOrder.setInAcct("3");
	        	String msg=core.getCommonRsHdr().getServerStatusCode();
	        	if(msg.length()>200){
	        		msg=msg.substring(0, 200);
	        	}
	        	cmapyCutOrder.setRemark(msg);
	        }


		}catch(Exception e){
			logger.error("orderId[{}]入账异常！！！",cmapyCutOrder.getOrderId());
        	cmapyCutOrder.setInAcct("3");
        	cmapyCutOrder.setRemark("入账异常");
			e.printStackTrace();
		}

	}

}
