package com.cmpay.service.quartz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpay.common.enums.CpErrorCodeEnum;
import com.cmpay.common.enums.JYTErrorCodeEnum;
import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.enums.PayWayEnum;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.Constants;
import com.cmpay.common.util.WXConstants;
import com.cmpay.service.chinapay.model.CpSinCutQueryRespDef;
import com.cmpay.service.chinapay.service.ChinapayService;
import com.cmpay.service.jytpay.model.CpJYTRespDef;
import com.cmpay.service.jytpay.service.JYTPayService;
import com.cmpay.service.jytpay.utils.XmlMsgConstant;
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
	@Value("#{env['UPAY_ORDER_VALID']}")
	private String upayOrderValid;

	@Autowired
	private CmapyRecordMapper cmpayRecordMapper;
	@Autowired
	private CmapyRecordDetailMapper cmpayRecordDetailMapper;
	@Autowired
	private CmapyChannelConfigMapper cmpayChannelConfigMapper;
	@Autowired
	private CmapyCutOrderMapper cmapyCutOrderMapper;
	@Autowired
	private ChinapayService chinapayService;
	@Autowired
	private JYTPayService jYTPayService;

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



	@Override
	@Transactional
	public void doCutOrderTask(CmapyCutOrder cmapyCutOrder) {
	       logger.info("开始更新代扣状态orderNO=[{}],origOrderNo=[{}],payChannel=[{}]",cmapyCutOrder.getOrderId(),cmapyCutOrder.getOrigOrderNo(),cmapyCutOrder.getPayChannel());

			//订单时效性检查
			boolean isOverDue = false;
			Date orderDt = cmapyCutOrder.getCreateTime();
			Date nowDt = new Date();
			long differencesTime = nowDt.getTime() - orderDt.getTime();
			long diffMinute = differencesTime/(60*1000);//差异分钟
			if(diffMinute > Long.parseLong(upayOrderValid)){
				//超过时效
				isOverDue = true;
			}

			PayWayEnum payWayEnum=PayWayEnum.getByCode(cmapyCutOrder.getPayChannel());

			CmapyRecord cmpayRecord=cmpayRecordMapper.selectByOrderId(cmapyCutOrder.getOrderId());
			if(cmpayRecord==null){
				logger.info("cmpayRecord 无此订单！！！！");
				return;
			}

			logger.info("cmpayRecord=[{}]",cmpayRecord.toString());
			if(StringUtils.equals(cmapyCutOrder.getPayStatus(), PayStatusEnum.DEALING.name())){
               if(isOverDue){
                     //超过系统超时时间
            	   cmpayRecord.setPayStatus(PayStatusEnum.FAIL.name());
            	   cmpayRecord.setRemark(Constants.TRADE_ERROR_8818_MSG);

            	   cmapyCutOrder.setPayStatus(PayStatusEnum.FAIL.name());
            	   cmapyCutOrder.setRemark(Constants.TRADE_ERROR_8818_MSG);

					logger.info("----代扣订单超过时效,已做失败处理------------");

               }else{
            	   //分渠道查询订单状态

            		CmapyChannelConfigExample cccCondition=new CmapyChannelConfigExample();
    				cccCondition.createCriteria().andMerNoEqualTo(cmapyCutOrder.getMerNo()).andPaychannelNoEqualTo(payWayEnum.name());
    				List<CmapyChannelConfig> cmpayChannelConfiglist=cmpayChannelConfigMapper.selectByExample(cccCondition);
    				if(cmpayChannelConfiglist.size()<=0){
    					logger.info("-----------------该商户缺少配置信息！！！-----------------");
    					return;

    				}

    				CmapyChannelConfig cmpayChannelConfig=cmpayChannelConfiglist.get(0);
    				logger.debug("paychannel config-->[{}]",cmpayChannelConfig.toString());

            	   switch(payWayEnum){

            	    case CMPAY0001:
            	    	CpSinCutQueryRespDef cpSinCutQueryRespDef=chinapayService.sinCutQuery(cmapyCutOrder.getOrderId(), cmpayChannelConfig.getThirdMerid(), cmpayChannelConfig.getRsaprikey(), cmpayChannelConfig.getRsapubkey());
            	    	 logger.info("cpSinCutQueryRespDef={}",cpSinCutQueryRespDef.toString());
            	    if(cpSinCutQueryRespDef!=null){
            	    	if (cpSinCutQueryRespDef.getResponseCode().equals("00")) {
            	    		cmpayRecord.setPayStatus(PayStatusEnum.SUCC.name());
            	    		cmapyCutOrder.setPayStatus(PayStatusEnum.SUCC.name());
						} else if (cpSinCutQueryRespDef.getResponseCode().equals("45") || cpSinCutQueryRespDef.getResponseCode().equals("") || cpSinCutQueryRespDef.getResponseCode().equals("09")) {
            	    		cmpayRecord.setPayStatus(PayStatusEnum.DEALING.name());
            	    		cmapyCutOrder.setPayStatus(PayStatusEnum.DEALING.name());
						} else {
            	    		cmpayRecord.setPayStatus(PayStatusEnum.FAIL.name());
            	    		cmapyCutOrder.setPayStatus(PayStatusEnum.FAIL.name());
						}
						CpErrorCodeEnum cpErrorCodeEnum=CpErrorCodeEnum.getByRespCode(cpSinCutQueryRespDef.getResponseCode());

						cmpayRecord.setRespCode(cpErrorCodeEnum.getCoreRespCode());
						cmpayRecord.setRespMsg(cpErrorCodeEnum.getCoreRespMsg());
						cmpayRecord.setModifyTime(new Date());
						cmapyCutOrder.setThirdRespCode(cpSinCutQueryRespDef.getResponseCode());
						cmapyCutOrder.setThirdRespMsg(cpSinCutQueryRespDef.getMessage());
						cmapyCutOrder.setRespCode(cpErrorCodeEnum.getCoreRespCode());
						cmapyCutOrder.setRespMsg(cpErrorCodeEnum.getCoreRespMsg());
						cmapyCutOrder.setModifyTime(new Date());
            	    	 }else{
            	    		 logger.info("系统找不到订单记录！！！");
            	    	 }
            	    	break;
            	    case CMPAY0002:

						CpJYTRespDef cpJYTRespDef = jYTPayService.queryCollection(cmpayChannelConfig.getThirdMerid(), cmpayChannelConfig.getRsapubkey(), cmpayChannelConfig.getRsaprikey(), cmpayChannelConfig.getAppsectet(), cmapyCutOrder.getOrderId());
						if(cpJYTRespDef!=null){
						logger.info("响应码:{},交易响应码:{}",cpJYTRespDef.getResp_code(),cpJYTRespDef.getTran_resp_code());
						if (XmlMsgConstant.MSG_RES_CODE_SUCCESS.equals(cpJYTRespDef.getResp_code())) {
							if (cpJYTRespDef.getTran_state().equals("01")) {
								cmpayRecord.setPayStatus(PayStatusEnum.SUCC.name());
	            	    		cmapyCutOrder.setPayStatus(PayStatusEnum.SUCC.name());
							} else if (cpJYTRespDef.getTran_state().equals("03")) {
								cmpayRecord.setPayStatus(PayStatusEnum.FAIL.name());
	            	    		cmapyCutOrder.setPayStatus(PayStatusEnum.FAIL.name());
							}
							JYTErrorCodeEnum jYTErrorCodeEnum=JYTErrorCodeEnum.getByRespCode(cpJYTRespDef.getTran_resp_code());

							cmpayRecord.setRespCode(jYTErrorCodeEnum.getCoreRespCode());
							cmpayRecord.setRespMsg(jYTErrorCodeEnum.getCoreRespMsg());
							cmpayRecord.setModifyTime(new Date());
							cmapyCutOrder.setThirdRespCode(cpJYTRespDef.getTran_resp_code());
							cmapyCutOrder.setThirdRespMsg(cpJYTRespDef.getDescription());
							cmapyCutOrder.setRespCode(jYTErrorCodeEnum.getCoreRespCode());
							cmapyCutOrder.setRespMsg(jYTErrorCodeEnum.getCoreRespMsg());
							cmapyCutOrder.setModifyTime(new Date());

						} else if (XmlMsgConstant.MSG_RES_CODE_PROCESSING.equals(cpJYTRespDef.getResp_code())) {
							logger.error("金运通查询代收订单处理中.");
						} else {
							logger.error("金运通查询代收订单出错.");
						}
						}else{
							logger.info("系统找不到订单记录！！！");
						}

            	    	break;
            	    default:
            	    	logger.info("==================无此支付渠道==============");
        				break;
            	   }



               }

               //执行更新
               cmpayRecordMapper.updateByPrimaryKeySelective(cmpayRecord);
               cmapyCutOrderMapper.updateByPrimaryKeySelective(cmapyCutOrder);

			}else{

				logger.info("状态不是在途交易，无需更新,orderNo:{},cutStatus:{}", cmapyCutOrder.getOrderId(), cmapyCutOrder.getPayStatus());
			}

	}

}
