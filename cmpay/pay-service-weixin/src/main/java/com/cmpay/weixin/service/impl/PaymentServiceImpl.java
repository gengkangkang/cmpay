package com.cmpay.weixin.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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
import com.cmpay.weixin.dao.CmpayChannelConfigMapper;
import com.cmpay.weixin.dao.CmpayRecordDetailMapper;
import com.cmpay.weixin.dao.CmpayRecordMapper;
import com.cmpay.weixin.entity.WXQueryBean;
import com.cmpay.weixin.entity.WeiXinPrePay;
import com.cmpay.weixin.exception.TradeBizException;
import com.cmpay.weixin.model.CmpayChannelConfig;
import com.cmpay.weixin.model.CmpayChannelConfigExample;
import com.cmpay.weixin.model.CmpayRecord;
import com.cmpay.weixin.model.CmpayRecordDetail;
import com.cmpay.weixin.model.CmpayRecordDetailExample;
import com.cmpay.weixin.model.CmpayRecordExample;
import com.cmpay.weixin.service.PaymentService;
import com.cmpay.weixin.utils.WeiXinPayUtils;
import com.cmpay.weixin.vo.PayPageShowVo;
import com.cmpay.weixin.vo.QueryResult;
import com.cmpay.weixin.vo.ScanPayResultVo;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 上午11:06:02
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private  Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);


	@Value("#{env['wx_notify_url']}")
	private String wx_notify_url;
	@Value("#{env['wx_prepay_url']}")
	private String wx_prepay_url;
	@Value("#{env['wx_orderQuery']}")
	private String wx_orderQuery;

	@Autowired
	private CmpayRecordMapper cmpayRecordMapper;
	@Autowired
	private CmpayRecordDetailMapper cmpayRecordDetailMapper;
	@Autowired
	private CmpayChannelConfigMapper cmpayChannelConfigMapper;

	@Override
	public PayPageShowVo createPreOrder(String merchantId, String inchannel, String userId, String transType,
			String orderId, BigDecimal amount, String orderIp, String productName) {
		// 1.根据商户号和商户订单查找订单记录
//		CmpayRecordExample cmpayRecordExample=new CmpayRecordExample();
//		cmpayRecordExample.createCriteria().andMerNoEqualTo(merchantId).andOrigOrderNoEqualTo(orderId);
//		List<CmpayRecord> cr=cmpayRecordMapper.selectByExample(cmpayRecordExample);
		CmpayRecord cmpaypara=new CmpayRecord();
		cmpaypara.setMerNo(merchantId);
		cmpaypara.setOrigOrderNo(orderId);
		CmpayRecord cr=cmpayRecordMapper.selectByOrderId(cmpaypara);
		CmpayRecord cmpayRecord=new CmpayRecord();

		if(cr==null){
			String id=CmpayUtils.getUUID();
			String order_id=CmpayUtils.createOrderId(WXConstants.WXPAY, transType);
			cmpayRecord.setId(id);
			cmpayRecord.setOrderId(order_id);
			cmpayRecord.setInchannel(inchannel);
			cmpayRecord.setMerNo(merchantId);
			cmpayRecord.setUserId(userId);
			cmpayRecord.setOrigOrderNo(orderId);
			cmpayRecord.setTransAmt(amount);
			cmpayRecord.setTransType(transType);
			cmpayRecord.setPayStatus(PayStatusEnum.WAIT.name());
			cmpayRecord.setPeriod(WXConstants.PERIOD);
			DateTime d=new DateTime();
			DateTime d1=d.plusMillis((int)WXConstants.PERIOD);
			cmpayRecord.setExpireTime(d1.toDate());
			cmpayRecord.setOrderip(orderIp);
			cmpayRecord.setCreateTime(new Date());
			cmpayRecord.setVersion(0);
			cmpayRecord.setField1(productName);
			cmpayRecordMapper.insert(cmpayRecord);

		}else{

            if (StringUtils.equals(PayStatusEnum.SUCC.name(), cr.getPayStatus())){
            	logger.info(cmpayRecord.getOrderId()+" 订单已支付成功,无需重复支付");
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
            }else{
            	logger.info(cmpayRecord.getOrderId()+" 订单已存在！！！");
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已存在！！！");
            }
		}

		PayPageShowVo payPageShowVo=new PayPageShowVo();
		payPageShowVo.setMerchantOrderNo(cmpayRecord.getOrderId());
		payPageShowVo.setOrderAmount(cmpayRecord.getTransAmt());
		payPageShowVo.setProductName(productName);
		//商户名称暂时写入常量，后续根据商户号查询
		payPageShowVo.setMerchantName("cmpay");

		return payPageShowVo;
	}

	@Override
	@Transactional
	public ScanPayResultVo unifiedorder(String cmpayOrder, String productName) {
		CmpayRecord cr=cmpayRecordMapper.selectByCmpayOrderId(cmpayOrder);
		 if (cr == null){
         	logger.info(cmpayOrder+" 预付订单不存在");
	            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"预付订单不存在");
	        }
	     if (StringUtils.equals(PayStatusEnum.SUCC.name(), cr.getPayStatus())){
	         	logger.info(cmpayOrder+" 订单已支付成功,无需重复支付");
	            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
	        }
	     ScanPayResultVo scanPayResultVo = new ScanPayResultVo();
	     cr.setPayChannel(PayWayEnum.CMPAY0004.name());
	     cr.setPayStatus(PayStatusEnum.DEALING.name());
	     cmpayRecordMapper.updateByPrimaryKey(cr);

	     //创建订单详情
	     CmpayRecordDetail cmpayRecordDetail=new CmpayRecordDetail();
	     cmpayRecordDetail.setId(CmpayUtils.getUUID());
	     cmpayRecordDetail.setOrderId(cr.getOrderId());
	     cmpayRecordDetail.setInchannel(cr.getInchannel());
	     cmpayRecordDetail.setMerNo(cr.getMerNo());
	     cmpayRecordDetail.setUserId(cr.getUserId());
	     cmpayRecordDetail.setOrigOrderNo(cr.getOrigOrderNo());
	     cmpayRecordDetail.setPayChannel(cr.getPayChannel());
	     cmpayRecordDetail.setTransAmt(cr.getTransAmt());
	     cmpayRecordDetail.setTransType(cr.getTransType());
	     cmpayRecordDetail.setInfoOrder(productName);
	     cmpayRecordDetail.setPeriod(cr.getPeriod());
	     cmpayRecordDetail.setExpireTime(cr.getExpireTime());
	     cmpayRecordDetail.setOrderId(cr.getOrderId());
	     cmpayRecordDetail.setPayStatus(cr.getPayStatus());
	     cmpayRecordDetail.setIsAcct("0");
	     cmpayRecordDetail.setAcctStatus("0");
	     cmpayRecordDetail.setCreateTime(new Date());
	     cmpayRecordDetail.setVersion(0);

	     //根据商户号，支付渠道编码查询参数
	     CmpayChannelConfigExample cmpayChannelConfigExample=new CmpayChannelConfigExample();
	     cmpayChannelConfigExample.createCriteria().andMerNoEqualTo(cr.getMerNo()).andPaychannelNoEqualTo(cr.getPayChannel());
	     List<CmpayChannelConfig> ccclist=cmpayChannelConfigMapper.selectByExample(cmpayChannelConfigExample);
         if(ccclist==null || ccclist.size()==0){
	         	logger.info(cmpayOrder+" 获取支付参数异常");
        	 throw new TradeBizException(TradeBizException.TRADE_PAYCHANNEL_CONFIG_ERROR,"获取支付参数异常");
         }

         cmpayRecordDetail.setReturnUrl(ccclist.get(0).getReturnUrl());
         cmpayRecordDetail.setNotifyUrl(ccclist.get(0).getNotifyUrl());
	     cmpayRecordDetailMapper.insert(cmpayRecordDetail);


         String appid = ccclist.get(0).getAppid();
         String thirdMerId =ccclist.get(0).getThirdMerid();
         String partnerKey = ccclist.get(0).getPartnerkey();
         WeiXinPrePay weiXinPrePay=this.sealWeixinPerPay(appid, thirdMerId, productName, "", cr.getOrderId(), cr.getTransAmt(), cr.getCreateTime(), cr.getExpireTime(), cr.getOrderip(),cr.getId());
         String prePayXml = WeiXinPayUtils.getPrePayXml(weiXinPrePay, partnerKey);
         logger.info("微信扫码支付统一下单参数为："+prePayXml);
         Map<String, Object> prePayRequest = WeiXinPayUtils.httpXmlRequest(wx_prepay_url, "POST", prePayXml);
         System.out.println("prePayRequest:"+prePayRequest.toString());

         if (WXConstants.WXSUCCESS.equals(prePayRequest.get("return_code"))) {
        	 if(WXConstants.WXSUCCESS.equals(prePayRequest.get("result_code"))){
             String weiXinPrePaySign = WeiXinPayUtils.geWeiXintPrePaySign(appid, thirdMerId, weiXinPrePay.getDeviceInfo(), WXConstants.TRADETYPE_NATIVE, prePayRequest, partnerKey);
             String codeUrl = String.valueOf(prePayRequest.get("code_url"));
             logger.info("预支付生成成功:"+codeUrl);
             if (prePayRequest.get("sign").equals(weiXinPrePaySign)) {
            	 //更新支付详情表状态
            	 cmpayRecordDetail.setCodeUrl(codeUrl);
            	 cmpayRecordDetail.setThirdOrderNo((String)prePayRequest.get("prepay_id"));
            	 cmpayRecordDetailMapper.updateByPrimaryKey(cmpayRecordDetail);
                 scanPayResultVo.setCodeUrl(codeUrl);//设置微信跳转地址
                 scanPayResultVo.setPayWayCode(PayWayEnum.CMPAY0004.name());
                 scanPayResultVo.setProductName(productName);
                 scanPayResultVo.setOrderAmount(cr.getTransAmt());
             }else{
            	 logger.error("微信返回结果签名异常");
                 throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"微信返回结果签名异常");
             }
        	 }else{
        		 //失败
            	 logger.info("统一下单失败：return_code="+prePayRequest.get("return_code")+",return_msg="+prePayRequest.get("return_msg")+",err_code="+prePayRequest.get("err_code")+",err_code_des="+prePayRequest.get("err_code_des"));
            	 cmpayRecordDetail.setThirdRespCode((String) prePayRequest.get("err_code"));
            	 cmpayRecordDetail.setThirdMsg((String) prePayRequest.get("err_code_des"));
            	 cmpayRecordDetailMapper.updateByPrimaryKey(cmpayRecordDetail);
            	 throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"微信统一下单失败");
        	 }
         }else{
        	 logger.info("请求微信异常：return_code="+prePayRequest.get("return_code")+",return_msg="+prePayRequest.get("return_msg")+",err_code="+prePayRequest.get("err_code")+",err_code_des="+prePayRequest.get("err_code_des"));
        	 cmpayRecordDetail.setThirdRespCode((String) prePayRequest.get("return_code"));
        	 cmpayRecordDetail.setThirdMsg((String) prePayRequest.get("return_msg"));
        	 cmpayRecordDetailMapper.updateByPrimaryKey(cmpayRecordDetail);
        	 throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"请求微信异常");
         }

		return scanPayResultVo;
	}

	private WeiXinPrePay sealWeixinPerPay(String appId, String mchId, String productName, String remark,
			String orderNo, BigDecimal orderPrice, Date orderTime,Date expireTime, String orderIp,String id) {
		WeiXinPrePay weiXinPrePay = new WeiXinPrePay();

		weiXinPrePay.setAppid(appId);
		weiXinPrePay.setMchId(mchId);
		weiXinPrePay.setBody(productName);// 商品描述
//		weiXinPrePay.setAttach(remark);// 支付备注
		weiXinPrePay.setOutTradeNo(orderNo);// 订单号

		Integer totalFee = orderPrice.multiply(BigDecimal.valueOf(100d)).intValue();
		weiXinPrePay.setTotalFee(totalFee);// 订单金额
		weiXinPrePay.setTimeStart(CmpayUtils.formatDate(orderTime, "yyyyMMddHHmmss"));// 订单开始时间
		weiXinPrePay.setTimeExpire(CmpayUtils.formatDate(expireTime, "yyyyMMddHHmmss"));// 订单到期时间
		weiXinPrePay.setNotifyUrl(wx_notify_url);// 通知地址
		weiXinPrePay.setTradeType(WXConstants.TRADETYPE_NATIVE);
		//暂时id，后续有前端传入
		weiXinPrePay.setProductId(id);
		weiXinPrePay.setSpbillCreateIp(orderIp);// 下单IP

		return weiXinPrePay;
	}

	@Override
	@Transactional
	public String acceptWXNotify(Map<String, String> notifyMap) {
        logger.info("接收到微信支付结果参数[{}]",notifyMap);

        String return_code=notifyMap.get("return_code");
        if(StringUtils.equals(return_code, WXConstants.WXSUCCESS)){
        	 String returnStr = null;
             String orderNo = notifyMap.get("out_trade_no");
             //根据订单号获取微信支付订单信息
    	     CmpayRecordDetailExample cmpayRecordDetailExample=new CmpayRecordDetailExample();
    	     cmpayRecordDetailExample.createCriteria().andOrderIdEqualTo(orderNo);
    	     List<CmpayRecordDetail> cmpayRecordDetaillist=cmpayRecordDetailMapper.selectByExample(cmpayRecordDetailExample);
    	     if(cmpayRecordDetaillist.size()==0){
               logger.info("{}订单不存在！！！",orderNo);
               throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
    	     }
    	     if(cmpayRecordDetaillist.size()>1){
                 logger.info("{}订单记录存在多条，怎么插入进去的！！！",orderNo);
                 throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
      	     }
    	     CmpayRecordDetail cmpayRecordDetail=cmpayRecordDetaillist.get(0);
    	        if (PayStatusEnum.SUCC.name().equals(cmpayRecordDetail.getPayStatus())){
    	            logger.info("{}订单状态为成功状态！",orderNo);
    	            return WXConstants.WX_RETURN_SUCCESS;
    	        }

    	   //校验报文签名信息
    		     //根据商户号，支付渠道编码查询参数，暂时存放在数据库，后续存在redis中
    		     CmpayChannelConfigExample cmpayChannelConfigExample=new CmpayChannelConfigExample();
    		     cmpayChannelConfigExample.createCriteria().andMerNoEqualTo(cmpayRecordDetail.getMerNo()).andPaychannelNoEqualTo(cmpayRecordDetail.getPayChannel());
    		     List<CmpayChannelConfig> ccclist=cmpayChannelConfigMapper.selectByExample(cmpayChannelConfigExample);
    	         if(ccclist==null || ccclist.size()==0){
    		         logger.info(" 获取支付参数异常！！！");
    	        	 throw new TradeBizException(TradeBizException.TRADE_PAYCHANNEL_CONFIG_ERROR,"获取支付参数异常");
    	         }

    	         String partnerKey = ccclist.get(0).getPartnerkey();

    	         String sign = notifyMap.remove("sign");

    	            if (WeiXinPayUtils.notifySign(notifyMap, sign, partnerKey)){
    	                if (WXConstants.WXSUCCESS.equals(notifyMap.get("result_code"))){
    	                    cmpayRecordDetail.setPayStatus(PayStatusEnum.SUCC.name());
    	                    cmpayRecordDetail.setPayOrderTime(notifyMap.get("time_end"));
    	                    cmpayRecordDetail.setThirdRespCode(notifyMap.get("result_code"));
    	                    cmpayRecordDetail.setThirdOrderNo(notifyMap.get("transaction_id"));
    	                    cmpayRecordDetailMapper.updateByPrimaryKeySelective(cmpayRecordDetail);
    	                    //同时更新预付订单状态
    	            		CmpayRecordExample cmpayRecordExample=new CmpayRecordExample();
    	            		cmpayRecordExample.createCriteria().andOrderIdEqualTo(cmpayRecordDetail.getOrderId());
    	            		CmpayRecord cmpayRecord=new CmpayRecord();
    	            		cmpayRecord.setPayStatus(PayStatusEnum.SUCC.name());
    	            		cmpayRecordMapper.updateByExampleSelective(cmpayRecord, cmpayRecordExample);

    	                    return WXConstants.WX_RETURN_SUCCESS;
    	                }else{
    	                	//失败，更新订单
    	                	logger.info("{}订单支付失败",orderNo);
    	                	 cmpayRecordDetail.setPayStatus(PayStatusEnum.FAIL.name());
     	                     cmpayRecordDetail.setPayOrderTime(notifyMap.get("time_end"));
     	                     cmpayRecordDetail.setThirdRespCode(notifyMap.get("result_code"));
     	                     cmpayRecordDetail.setThirdOrderNo(notifyMap.get("transaction_id"));
     	                     cmpayRecordDetailMapper.updateByPrimaryKeySelective(cmpayRecordDetail);
     	                    //同时更新预付订单状态
     	            		CmpayRecordExample cmpayRecordExample=new CmpayRecordExample();
     	            		cmpayRecordExample.createCriteria().andOrderIdEqualTo(cmpayRecordDetail.getOrderId());
     	            		CmpayRecord cmpayRecord=new CmpayRecord();
     	            		cmpayRecord.setPayStatus(PayStatusEnum.FAIL.name());
     	            		cmpayRecordMapper.updateByExampleSelective(cmpayRecord, cmpayRecordExample);
     	                    return WXConstants.WX_RETURN_SUCCESS;
    	                }
    	            }else{
    	            	logger.error("微信通知验证签名失败！！！");
    	                return WXConstants.WX_RETURN_FAIL;
    	            }


        }else{
        	//通讯异常，通知报文，应该不会出现该情况
        	logger.error("这种情况要是发生，那就是微信瓦特了！！！！！！！");
        	return WXConstants.WX_RETURN_FAIL;
        }
	}

	@Override
	@Transactional
	public void QueryWXOrder() {

		//1.查询处理中的订单
		 CmpayRecordDetailExample cmpayRecordDetailExample=new CmpayRecordDetailExample();
	     cmpayRecordDetailExample.createCriteria().andPayChannelEqualTo(PayWayEnum.CMPAY0004.name()).andPayStatusEqualTo(PayStatusEnum.DEALING.name());
	     List<CmpayRecordDetail> cmpayRecordDetaillist=cmpayRecordDetailMapper.selectByExample(cmpayRecordDetailExample);
	     if(!(cmpayRecordDetaillist.size()>0)){
	    	 logger.info("没有处理中的订单");
	    	 return;
	     }

	     //根据商户号，支付渠道编码查询参数，暂时存放在数据库，后续存在redis中
	     CmpayChannelConfigExample cmpayChannelConfigExample=new CmpayChannelConfigExample();
	     cmpayChannelConfigExample.createCriteria().andMerNoEqualTo(cmpayRecordDetaillist.get(0).getMerNo()).andPaychannelNoEqualTo(cmpayRecordDetaillist.get(0).getPayChannel());
	     List<CmpayChannelConfig> ccclist=cmpayChannelConfigMapper.selectByExample(cmpayChannelConfigExample);
         if(ccclist==null || ccclist.size()==0){
	         logger.info(" 获取支付参数异常！！！");
	         return;
//        	 throw new TradeBizException(TradeBizException.TRADE_PAYCHANNEL_CONFIG_ERROR,"获取支付参数异常");
         }
         String appid = ccclist.get(0).getAppid();
         String mchid = ccclist.get(0).getThirdMerid();
         String partnerKey = ccclist.get(0).getPartnerkey();

	     for(CmpayRecordDetail cmpayRecordDetail:cmpayRecordDetaillist){
	    	 //量少的时候暂时用for循环处理，量大时可以考虑走队列、多线程处理
            logger.info("订单[{}]开始查询",cmpayRecordDetail.getOrderId());
            try{
	    	 CmpayRecordDetail crd=new CmpayRecordDetail();
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
 	            		CmpayRecordExample cmpayRecordExample=new CmpayRecordExample();
 	            		cmpayRecordExample.createCriteria().andOrderIdEqualTo(cmpayRecordDetail.getOrderId());
 	            		CmpayRecord cmpayRecord=new CmpayRecord();

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
	public QueryResult OrderQuery(String orderId) {

		String code=null;
		String msg=null;
		String return_url=null;
  		CmpayRecordDetail cmpayRecordDetail=cmpayRecordDetailMapper.selectByCmpayOrderId(orderId);
        if(null!=cmpayRecordDetail){
      	  return_url=cmpayRecordDetail.getReturnUrl();

              if(StringUtils.equals(cmpayRecordDetail.getPayStatus(), PayStatusEnum.SUCC.name())){
            	  code=WXConstants.SUCCESS_CODE;
            	  msg=WXConstants.SUCCESS_MSG;
              }else if(StringUtils.equals(cmpayRecordDetail.getPayStatus(), PayStatusEnum.FAIL.name())){
            	  code=WXConstants.FAILED_CODE;
            	  msg=WXConstants.FAILED_MSG;
              }else{
            	  code=WXConstants.PROCESS_CODE;
            	  msg=WXConstants.PROCESS_MSG;
              }

        }else{
        	logger.info("订单不存在！！！！！");
        	code=WXConstants.FAILED_CODE;
        	msg="订单不存在";
        }

        QueryResult queryResult=new QueryResult();
        queryResult.setCode(code);
        queryResult.setMsg(msg);
        queryResult.setReturn_url(return_url);

		return queryResult;
	}

	@Override
	public void doExpireOrder() {
      logger.info("-------------------开始处理超时过期订单---------------------");
      int r=cmpayRecordMapper.updateExpireOrder();
      logger.info("超时订单处理结果[{}]条",r);
	}

}
