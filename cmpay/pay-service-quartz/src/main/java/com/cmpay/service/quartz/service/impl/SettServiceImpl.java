package com.cmpay.service.quartz.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.HttpClientUtil;
import com.cmpay.service.quartz.dao.CmapySettDetailMapper;
import com.cmpay.service.quartz.dao.CmapySettTotalMapper;
import com.cmpay.service.quartz.model.CMAPI0045;
import com.cmpay.service.quartz.model.CMAPI0045Rs;
import com.cmpay.service.quartz.model.CmapySettDetail;
import com.cmpay.service.quartz.model.CmapySettDetailExample;
import com.cmpay.service.quartz.model.CmapySettTotal;
import com.cmpay.service.quartz.model.CmapySettTotalExample;
import com.cmpay.service.quartz.model.CommonRqHdr;
import com.cmpay.service.quartz.model.SettTotalBean;
import com.cmpay.service.quartz.service.SettService;
import com.cmpay.service.quartz.util.AcctConstant;
import com.cmpay.service.quartz.util.CMAPI0045Util;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月10日 下午5:42:20
 *
 */
@Service
public class SettServiceImpl implements SettService {
    private  Logger logger = LoggerFactory.getLogger(SettServiceImpl.class);

    @Autowired
    CmapySettDetailMapper cmapySettDetailMapper;
    @Autowired
    CmapySettTotalMapper cmapySettTotalMapper;
	@Value("#{env['coreurl']}")
	private String coreurl;

	@Override
	public void doDaliyMerSettDetail() {
        logger.info("开始处理T-1日交易成功的消费类订单，移入结算表");
         try{
        	 int i=cmapySettDetailMapper.payorderToSettDetail();
             logger.info("需要结算的订单条数【{}】条",i);
         }catch(Exception e){
        	 logger.error("结算任务出现异常",e);
         }
         logger.info("处理T-1日交易成功的消费类订单，移入结算表结束");

	}

    @Override
	@Transactional
	public void doDaliyMerSettTotal() {
        logger.info("开始处理结算批次汇总-start");
         try{
           //查找结算明细表为汇总的结算订单
         List<SettTotalBean> stbList=cmapySettDetailMapper.getDailySettTotal();
         if(stbList==null || stbList.size()<1){
        	 logger.info("没有需要批量汇总的结算数据！");
         }else{
             logger.info("需要结算的商户有【{}】个",stbList.size());
             CmapySettTotal cmapySettTotal=null;
             for(SettTotalBean settTotalBean:stbList){
            	  cmapySettTotal=new CmapySettTotal();
            	  String batch_id=CmpayUtils.getUUID();
            	  cmapySettTotal.setBatchId(batch_id);
            	  cmapySettTotal.setMerNo(settTotalBean.getMerId());
            	  cmapySettTotal.setSettAmt(settTotalBean.getSettToatlAmt());
            	  cmapySettTotal.setSettDate(new Date());
            	  cmapySettTotal.setSettStatus("0");
            	  cmapySettTotal.setCreateTime(new Date());
            	  cmapySettTotal.setVersion(0);
            	  cmapySettTotalMapper.insert(cmapySettTotal);

            	  //更新详情表里的批次号
            	  CmapySettDetailExample cmapySettDetailExample=new CmapySettDetailExample();
            	  cmapySettDetailExample.or().andMerNoEqualTo(settTotalBean.getMerId()).andSettStatusEqualTo("0").andBatchIdIsNull();
            	  CmapySettDetail cmapySettDetail=new CmapySettDetail();
            	  cmapySettDetail.setBatchId(batch_id);
            	  cmapySettDetail.setSettStatus("1");
            	  cmapySettDetail.setModifyTime(new Date());

            	  cmapySettDetailMapper.updateByExampleSelective(cmapySettDetail, cmapySettDetailExample);
             }
         }

         }catch(Exception e){
        	 logger.error("结算汇总任务出现异常",e);
         }
         logger.info("开始处理结算批次汇总-end");

	}

    @Override
  public void doMerSett(CmapySettTotal cmapySettTotal){

	  try{
           if(!StringUtils.equals("0", cmapySettTotal.getSettStatus())){
        	   logger.info("结算状态【{}】不正确，不予结算",cmapySettTotal.getSettStatus());
        	   return;
           }

           //请求核心
	        CommonRqHdr com=new CommonRqHdr();
	        com.setRqUID(CmpayUtils.getUUID());
	        com.setSPName(AcctConstant.spName);
	        com.setNumTranCode(AcctConstant.NumTranCode);
	        com.setClearDate(CmpayUtils.getCurrentTime("yyyyMMdd"));
	        com.setTranDate(CmpayUtils.getCurrentTime("yyyyMMdd"));
	        com.setTranTime(CmpayUtils.getCurrentTime("HHmmss"));
	        com.setChannelId(AcctConstant.channelId);
	        com.setVersion(AcctConstant.version);


	        CMAPI0045 pay=new CMAPI0045();
	        pay.setCommonRqHdr(com);
            pay.setMerchantNo(cmapySettTotal.getMerNo());
            pay.setChannelOrderNo(cmapySettTotal.getBatchId());
            pay.setAmount(cmapySettTotal.getSettAmt().toString());

            String xml=CMAPI0045Util.toXML(pay);
            logger.info("请求xml=[{}]",xml);

			String back = HttpClientUtil.postString(coreurl, xml, AcctConstant.UTF8, AcctConstant.UTF8);
            logger.info("返回报文back=[{}]",back);

            CMAPI0045Rs core=CMAPI0045Util.fromXML(back);

	        if(StringUtils.equals("0000", core.getCommonRsHdr().getStatusCode())){

	        	cmapySettTotal.setSettStatus("1");
	        }else{
	        	logger.info("调用结算接口失败");
	        	cmapySettTotal.setSettStatus("2");
	        	String msg=core.getCommonRsHdr().getServerStatusCode();
	        	if(msg.length()>200){
	        		msg=msg.substring(0, 200);
	        	}
	        	cmapySettTotal.setRemark(msg);
	        }




	  }catch(Exception e){
		  logger.error("发送到核心商户结算发现异常！",e);
		  cmapySettTotal.setSettStatus("3");
	  }
	  try{
	  cmapySettTotal.setModifyTime(new Date());
	  cmapySettTotalMapper.updateByPrimaryKeySelective(cmapySettTotal);
	  }catch(Exception e){
		  logger.error("更新结算批次汇总表异常！",e);
	  }


  }

	@Override
	public List<CmapySettTotal> queryMerSett() {
		try{
			CmapySettTotalExample cmapySettTotalExample=new CmapySettTotalExample();
			cmapySettTotalExample.or().andSettStatusEqualTo("0");
			List<CmapySettTotal> list=cmapySettTotalMapper.selectByExample(cmapySettTotalExample);

			return list;

		}catch(Exception e){
			logger.error("查询商户结算批次汇总信息出现异常！",e);
		}
		return null;
	}

}
