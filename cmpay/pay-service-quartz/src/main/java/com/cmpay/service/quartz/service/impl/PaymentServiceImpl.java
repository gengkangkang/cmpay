package com.cmpay.service.quartz.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.enums.PayWayEnum;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.WXConstants;
import com.cmpay.service.quartz.dao.CmapyChannelConfigMapper;
import com.cmpay.service.quartz.dao.CmapyCutOrderMapper;
import com.cmpay.service.quartz.dao.CmapyRecordDetailMapper;
import com.cmpay.service.quartz.dao.CmapyRecordMapper;
import com.cmpay.service.quartz.model.CmapyChannelConfig;
import com.cmpay.service.quartz.model.CmapyChannelConfigExample;
import com.cmpay.service.quartz.model.CmapyCutOrder;
import com.cmpay.service.quartz.model.CmapyCutOrderExample;
import com.cmpay.service.quartz.model.CmapyRecord;
import com.cmpay.service.quartz.model.CmapyRecordDetail;
import com.cmpay.service.quartz.model.CmapyRecordDetailExample;
import com.cmpay.service.quartz.model.CmapyRecordExample;
import com.cmpay.service.quartz.service.PaymentService;
import com.cmpay.service.quartz.util.WXQueryBean;
import com.cmpay.service.quartz.util.WeiXinPayUtils;

/**
 * 支付轮询任务
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月19日 上午11:34:44
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private  Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);



	@Value("#{env['wx_orderQuery']}")
	private String wx_orderQuery;

	@Autowired
	private CmapyRecordMapper cmpayRecordMapper;
	@Autowired
	private CmapyRecordDetailMapper cmpayRecordDetailMapper;
	@Autowired
	private CmapyChannelConfigMapper cmpayChannelConfigMapper;
	@Autowired
	private CmapyCutOrderMapper cmapyCutOrderMapper;


	@Override
	@Transactional
	public void QueryWXOrder() {

		//1.查询处理中的订单
		 CmapyRecordDetailExample cmpayRecordDetailExample=new CmapyRecordDetailExample();
	     cmpayRecordDetailExample.createCriteria().andPayChannelEqualTo(PayWayEnum.CMPAY0004.name()).andPayStatusEqualTo(PayStatusEnum.DEALING.name());
	     List<CmapyRecordDetail> cmpayRecordDetaillist=cmpayRecordDetailMapper.selectByExample(cmpayRecordDetailExample);
	     if(!(cmpayRecordDetaillist.size()>0)){
	    	 logger.info("没有处理中的订单");
	    	 return;
	     }

	     //根据商户号，支付渠道编码查询参数，暂时存放在数据库，后续存在redis中
	     CmapyChannelConfigExample cmpayChannelConfigExample=new CmapyChannelConfigExample();
	     cmpayChannelConfigExample.createCriteria().andMerNoEqualTo(cmpayRecordDetaillist.get(0).getMerNo()).andPaychannelNoEqualTo(cmpayRecordDetaillist.get(0).getPayChannel());
	     List<CmapyChannelConfig> ccclist=cmpayChannelConfigMapper.selectByExample(cmpayChannelConfigExample);
         if(ccclist==null || ccclist.size()==0){
	         logger.info(" 获取支付参数异常！！！");
	         return;
//        	 throw new TradeBizException(TradeBizException.TRADE_PAYCHANNEL_CONFIG_ERROR,"获取支付参数异常");
         }
         String appid = ccclist.get(0).getAppid();
         String mchid = ccclist.get(0).getThirdMerid();
         String partnerKey = ccclist.get(0).getPartnerkey();

	     for(CmapyRecordDetail cmpayRecordDetail:cmpayRecordDetaillist){
	    	 //量少的时候暂时用for循环处理，量大时可以考虑走队列、多线程处理
            logger.info("订单[{}]开始查询",cmpayRecordDetail.getOrderId());
            try{
	    	 CmapyRecordDetail crd=new CmapyRecordDetail();
	    	 WXQueryBean wxQueryBean=new WXQueryBean();
	    	 wxQueryBean.setAppid(appid);
	    	 wxQueryBean.setMch_id(mchid);
	    	 wxQueryBean.setOut_trade_no(cmpayRecordDetail.getOrderId());
	    	 wxQueryBean.setNonce_str(CmpayUtils.getUUID());
	    	 String queryXML=WeiXinPayUtils.getWXQueryOrderXml(wxQueryBean, partnerKey);
	    	 logger.info("微信订单查询xml为[{}]",queryXML);
	    	 Map<String, String> queryResult = WeiXinPayUtils.httpXmlRequestString(wx_orderQuery, "POST", queryXML);
	    	 logger.info("微信订单查询返回结果[{}]",queryResult);

	         if (WXConstants.WXSUCCESS.equals(queryResult.get("return_code"))) {


		         String sign = queryResult.remove("sign");
		         queryResult.remove("attach");

 	            if (WeiXinPayUtils.notifySign(queryResult, sign, partnerKey)){
 	                if (WXConstants.WXSUCCESS.equals(queryResult.get("result_code"))){
 	                	//看交易状态
 	            		CmapyRecordExample cmpayRecordExample=new CmapyRecordExample();
 	            		cmpayRecordExample.createCriteria().andOrderIdEqualTo(cmpayRecordDetail.getOrderId());
 	            		CmapyRecord cmpayRecord=new CmapyRecord();

 	                	if(WXConstants.WXSUCCESS.equals(queryResult.get("trade_state"))){
 	 	                	logger.info("查询订单支付成功");
 	 	                    cmpayRecordDetail.setPayStatus(PayStatusEnum.SUCC.name());
 	 	                    //同时更新预付订单状态
 	 	            		cmpayRecord.setPayStatus(PayStatusEnum.SUCC.name());
 	                	}else if(WXConstants.WXNOTPAY.equals(queryResult.get("trade_state")) ||WXConstants.WXCLOSED.equals(queryResult.get("trade_state"))
 	                			||WXConstants.WXPAYERROR.equals(queryResult.get("trade_state"))||WXConstants.WXREFUND.equals(queryResult.get("trade_state"))||WXConstants.WXREVOKED.equals(queryResult.get("trade_state"))){
 	                		 cmpayRecordDetail.setPayStatus(PayStatusEnum.FAIL.name());
  	 	            		 cmpayRecord.setPayStatus(PayStatusEnum.FAIL.name());
 	                	}else{
 	                		//处理中，继续等待查询
 	                		logger.info("订单在处理中，继续等待===");
 	                		continue;
 	                	}

 	                    cmpayRecordDetail.setPayOrderTime(queryResult.get("time_end"));
 	                    cmpayRecordDetail.setThirdRespCode(queryResult.get("trade_state"));
 	                    cmpayRecordDetail.setThirdMsg(queryResult.get("trade_state_desc"));
 	                    cmpayRecordDetail.setThirdOrderNo(queryResult.get("transaction_id"));
 	                    cmpayRecordDetailMapper.updateByPrimaryKeySelective(cmpayRecordDetail);

 	            		cmpayRecordMapper.updateByExampleSelective(cmpayRecord, cmpayRecordExample);


 	                }else{
 	                	//暂时不做处理
 	                	logger.info("查询订单支付业务结果失败");
                         continue;
 	                }


 	            }else{
 	            	logger.error("查询微信订单验证签名失败！！！");

 	            }


	         }else{
	        	 logger.info("查询订单请求微信异常：return_code="+queryResult.get("return_code")+",return_msg="+queryResult.get("return_msg")+",err_code="+queryResult.get("err_code")+",err_code_des="+queryResult.get("err_code_des"));

	         }

            }catch(Exception e){
                 logger.error("查询订单出现异常");
                 e.printStackTrace();
            }
	     }
	}



	@Override
	public void doExpireOrder() {
      logger.info("-------------------开始处理超时过期订单---------------------");
      int r=cmpayRecordMapper.updateExpireOrder();
      logger.info("超时订单处理结果[{}]条",r);
	}



	@Override
	public List<CmapyCutOrder> queryCutOrderList() {

		CmapyCutOrderExample cmapyCutOrderExample=new CmapyCutOrderExample();
		cmapyCutOrderExample.or().andPayStatusEqualTo(PayStatusEnum.DEALING.name());
		List<CmapyCutOrder> orderList=cmapyCutOrderMapper.selectByExample(cmapyCutOrderExample);
		return orderList;
	}

}
