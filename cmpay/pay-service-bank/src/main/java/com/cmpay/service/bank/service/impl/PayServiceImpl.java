package com.cmpay.service.bank.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpay.common.enums.AuthChannelEnum;
import com.cmpay.common.enums.CpErrorCodeEnum;
import com.cmpay.common.enums.CpPayRespCodeEnum;
import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.JYTErrorCodeEnum;
import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.enums.PayWayEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.Constants;
import com.cmpay.service.bank.dao.CmpayAuthRecordMapper;
import com.cmpay.service.bank.dao.CmpayChannelConfigMapper;
import com.cmpay.service.bank.dao.CmpayCutOrderMapper;
import com.cmpay.service.bank.dao.CmpayPayOrderMapper;
import com.cmpay.service.bank.model.CmpayAuthRecord;
import com.cmpay.service.bank.model.CmpayChannelConfig;
import com.cmpay.service.bank.model.CmpayChannelConfigExample;
import com.cmpay.service.bank.model.CmpayCutOrder;
import com.cmpay.service.bank.model.CmpayPayOrder;
import com.cmpay.service.bank.model.SinPayResp;
import com.cmpay.service.bank.service.PayService;
import com.cmpay.service.chinapay.model.CpAuthBgRespDef;
import com.cmpay.service.chinapay.model.CpSinCutRespDef;
import com.cmpay.service.chinapay.model.CpSinPayRespDef;
import com.cmpay.service.chinapay.service.ChinapayService;
import com.cmpay.service.jytpay.model.CpJYTRespDef;
import com.cmpay.service.jytpay.service.JYTAuthService;
import com.cmpay.service.jytpay.service.JYTPayService;

/**
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月6日 下午5:50:48
 *
 */
@Service
public class PayServiceImpl implements PayService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	CmpayAuthRecordMapper cmpayAuthRecordMapper;
    @Autowired
	CmpayChannelConfigMapper cmpayChannelConfigMapper;

    @Autowired
    ChinapayService chinapayService;
    @Autowired
    JYTAuthService jYTAuthService;
    @Autowired
    JYTPayService jYTPayService;
    @Autowired
    CmpayCutOrderMapper cmpayCutOrderMapper;

    @Autowired
    CmpayPayOrderMapper cmpayPayOrderMapper;

	@Override
	public Map<String, String> doPayAuth(String merchantId,String userId,String inchannel,AuthChannelEnum authChannel,String cardNo, SignCardTypeEnum cardType, String idNo, IdTypeEnum idType,
			String name, String bankMobile, String bankCode, String terminalType) {
		logger.info("[{}]开始卡认证，inchannel=[{}],authChannel=[{}],cardNO=[{}]",userId,inchannel,authChannel,cardNo);
		String code=Constants.FAILED_CODE;
		String msg="";
		Map<String,String> result=new HashMap<String,String>();
       //插入认证流水
		CmpayAuthRecord cmpayAuthRecord=new CmpayAuthRecord();
		cmpayAuthRecord.setMerchantid(merchantId);
		cmpayAuthRecord.setId(CmpayUtils.getUUID());
		cmpayAuthRecord.setUserid(userId);
		cmpayAuthRecord.setInchannel(inchannel);
		cmpayAuthRecord.setAuthchannel(authChannel.name());
		cmpayAuthRecord.setCardno(cardNo);
		cmpayAuthRecord.setCardtype(cardType.name());
		cmpayAuthRecord.setIdno(idNo);
		cmpayAuthRecord.setIdtype(idType.name());
		cmpayAuthRecord.setName(name);
		cmpayAuthRecord.setBankmobile(bankMobile);
		cmpayAuthRecord.setCreateTime(new Date());
		cmpayAuthRecord.setVersion(0);
		if(StringUtils.equals(AuthChannelEnum.CMPAY0002.name(), authChannel.name())){
			//jyt
			cmpayAuthRecord.setBankcode(bankCode);
			cmpayAuthRecord.setTerminaltype(terminalType);
		}

		cmpayAuthRecord.setStatus(PayStatusEnum.DEALING.name());
		cmpayAuthRecordMapper.insert(cmpayAuthRecord);

		//查配置信息，后续放入redis
		CmpayChannelConfigExample cccCondition=new CmpayChannelConfigExample();
		cccCondition.createCriteria().andMerNoEqualTo(merchantId).andPaychannelNoEqualTo(authChannel.name());
		List<CmpayChannelConfig> cmpayChannelConfiglist=cmpayChannelConfigMapper.selectByExample(cccCondition);
		if(cmpayChannelConfiglist.size()<=0){
			logger.info("该商户缺少配置信息！！！");
			result.put(Constants.CODE_KEY, Constants.FAILED_CODE);
			result.put(Constants.MSG_KEY, "商户缺少配置信息");
             return result;
		}

		CmpayChannelConfig cmpayChannelConfig=cmpayChannelConfiglist.get(0);
		logger.debug("paychannel config-->[{}]",cmpayChannelConfig.toString());

		//2.根据authChannel选择认证渠道
          switch(authChannel){
             case CMPAY0001:
                  //银联验卡  bgAuth(String appSysId,String authPrivateKey,String cardNo, IdType idType, String idNo,SignCardTypeDef cardType, String custName, String cardPhone);
            	 CpAuthBgRespDef resp=chinapayService.bgAuth(cmpayChannelConfig.getAppid(), cmpayChannelConfig.getAppsectet(), cardNo, idType, idNo, cardType, name, bankMobile);
            	 if(resp==null){
            		 logger.info("银联无返回对象!");
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 code=Constants.FAILED_CODE;
            		 msg="银联无返回对象";
            	 }else if(StringUtils.equals("0000", resp.getRespCode())){
            		 logger.info("银联验证成功!");
            		 code=Constants.SUCCESS_CODE;
            		 msg="验证成功";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.SUCC.name());
            	 }else{
            		 logger.info("银联验卡失败，resp=[{}]",resp.toString());
            		 code=Constants.FAILED_CODE;
            		 msg="验证失败";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 cmpayAuthRecord.setRemark(resp.getRespMsg());
            	 }
//            	 cmpayAuthRecordMapper.updateByPrimaryKeySelective(cmpayAuthRecord);


        	  break;
             case CMPAY0002:
                  //jyt      String merNo,String pubKey,String privKey,String privPwd,
            	 Map<String,String> respmap=jYTAuthService.JYTAuth(cmpayChannelConfig.getThirdMerid(),cmpayChannelConfig.getRsapubkey(),cmpayChannelConfig.getRsaprikey(),cmpayChannelConfig.getAppsectet(),cardNo, bankCode, idNo, name, terminalType, cardType, bankMobile, idType);

            	 if(respmap==null){
            		 logger.info("金运通无返回对象!");
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 code=Constants.FAILED_CODE;
            		 msg="金运通无返回对象";
            	 }else if(StringUtils.equals("0000", respmap.get("code"))){
            		 logger.info("金运通验证成功!");
            		 code=Constants.SUCCESS_CODE;
            		 msg="验证成功";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.SUCC.name());
            	 }else{
            		 logger.info("金运通验卡失败，resp=[{}]",respmap.toString());
            		 code=Constants.FAILED_CODE;
            		 msg="验证失败";
            		 cmpayAuthRecord.setStatus(PayStatusEnum.FAIL.name());
            		 cmpayAuthRecord.setRemark(respmap.get("msg"));
            	 }
//            	 cmpayAuthRecordMapper.updateByPrimaryKeySelective(cmpayAuthRecord);

           	  break;

             default:
            	 logger.info("不支持的渠道");
            	 Constants.CODE_KEY=Constants.FAILED_CODE;
            	 Constants.MSG_KEY="不支持的渠道";
            	 break;
          }

     	 cmpayAuthRecordMapper.updateByPrimaryKeySelective(cmpayAuthRecord);


       result.put(Constants.CODE_KEY, code);
       result.put(Constants.MSG_KEY, msg);

		return result;
	}

	@Override
	public Map<String, Object> doPayCut(String merchantId, String orderId, String inchannel, PayWayEnum payWayEnum,
			String cardNo, String idNo, IdTypeEnum idType, String name, String bankMobile, String bankCode,
			String bankName, BigDecimal transAmt, String userId, String origOrderNo,String transType,
			String isAcct,String notifyUrl,String toAcctNo,String orderip, String remark) {

		logger.info("[{}]开始快捷支付,orderId=[{}],inchannel=[{}],payWayEnum=[{}],cardNO=[{}],origOrderNo=[{}]",userId,orderId,inchannel,payWayEnum.name(),cardNo,origOrderNo);
		Map<String,Object> result=new HashMap<String,Object>();
//		Map<String,String> data=new HashMap<String,String>();

		String respCode=Constants.SUCCESS_CODE;
		String respMsg="ok";
		String payCode=null;
		String payMsg=null;
		String payStatus=null;

		//插入代扣流水
		CmpayCutOrder cmpayCutOrder=new CmpayCutOrder();
		cmpayCutOrder.setId(CmpayUtils.getUUID());
		cmpayCutOrder.setOrderId(orderId);
		cmpayCutOrder.setInchannel(inchannel);
		cmpayCutOrder.setMerNo(merchantId);
		cmpayCutOrder.setUserId(userId);
		cmpayCutOrder.setOrigOrderNo(origOrderNo);
		cmpayCutOrder.setTransAmt(transAmt);
		cmpayCutOrder.setTransType(transType);
		cmpayCutOrder.setPayChannel(payWayEnum.name());
		cmpayCutOrder.setPayStatus(PayStatusEnum.NEW.name());
		cmpayCutOrder.setCustName(name);
		cmpayCutOrder.setIdNo(idNo);
		cmpayCutOrder.setIdType(idType.name());
		cmpayCutOrder.setCardNo(cardNo);
		cmpayCutOrder.setBankCode(bankCode);
		cmpayCutOrder.setInAcct("0");
		cmpayCutOrder.setIsAcct(isAcct);
		cmpayCutOrder.setNotifyUrl(notifyUrl);
		cmpayCutOrder.setNotifyCount(0);
		cmpayCutOrder.setHasnotify("0");
		//商户内部户从配置文件中获取
//		cmpayCutOrder.setPayChanelInteracctno(payChanelInteracctno);
		cmpayCutOrder.setToAcctNo(toAcctNo);
		cmpayCutOrder.setPeriod(Constants.PERIOD);
		DateTime d=new DateTime();
		DateTime d1=d.plusMillis((int)Constants.PERIOD);
		cmpayCutOrder.setExpireTime(d1.toDate());
		cmpayCutOrder.setOrderip(orderip);
		cmpayCutOrder.setOrderDt(CmpayUtils.getCurrentTime("yyyyMMdd"));
		cmpayCutOrder.setCreateTime(new Date());
		cmpayCutOrder.setVersion(0);



		//经过router后，查出对应的支付信息，选择相关的支付通道
		//查配置信息，后续放入redis
				CmpayChannelConfigExample cccCondition=new CmpayChannelConfigExample();
				cccCondition.createCriteria().andMerNoEqualTo(merchantId).andPaychannelNoEqualTo(payWayEnum.name());
				List<CmpayChannelConfig> cmpayChannelConfiglist=cmpayChannelConfigMapper.selectByExample(cccCondition);
				if(cmpayChannelConfiglist.size()<=0){
					logger.info("该商户缺少配置信息！！！");
					cmpayCutOrder.setPayStatus(PayStatusEnum.FAIL.name());
					cmpayCutOrder.setRemark("商户缺少配置信息");
					respCode=Constants.TRADE_ERROR_8806_CODE;
					respMsg=Constants.TRADE_ERROR_8806_MSG;
					cmpayCutOrder.setRespCode(respCode);
					cmpayCutOrder.setRespMsg(respMsg);
					cmpayCutOrderMapper.insert(cmpayCutOrder);

					result.put(Constants.RESPCODE_KEY, respCode);
					result.put(Constants.RESPMSG_KEY, respMsg);

					return result;

//					throw new TradeBizException(Constants.FAILED_CODE,"商户缺少配置信息");
				}

				CmpayChannelConfig cmpayChannelConfig=cmpayChannelConfiglist.get(0);
				logger.debug("paychannel config-->[{}]",cmpayChannelConfig.toString());

				//暂时不配置内部户，入账逻辑让账务系统处理
//				String interacctno="";
//				if(StringUtils.equals("1", isAcct)){
//					 interacctno=cmpayChannelConfig.getField1();
//					if(StringUtils.isBlank(interacctno)){
//						logger.info("商户缺少interacctno配置信息！！！");
//						cmpayCutOrder.setPayStatus(PayStatusEnum.FAIL.name());
//						cmpayCutOrder.setRemark("商户缺少interacctno配置信息");
//						respCode=Constants.TRADE_ERROR_8807_CODE;
//						respMsg=Constants.TRADE_ERROR_8807_MSG;
//						cmpayCutOrder.setRespCode(respCode);
//						cmpayCutOrder.setRespMsg(respMsg);
//						cmpayCutOrderMapper.insert(cmpayCutOrder);
//
//						result.put(Constants.RESPCODE_KEY, respCode);
//						result.put(Constants.RESPMSG_KEY, respMsg);
//
//						return result;
//
//					}
//
//				}
//				cmpayCutOrder.setPayChanelInteracctno(interacctno);

				cmpayCutOrderMapper.insert(cmpayCutOrder);

				//根据渠道信息调用相应渠道
		          switch(payWayEnum){
		             case CMPAY0001:
		            	 CpSinCutRespDef resp=chinapayService.sinCut(orderId, cardNo, userId, bankCode, transAmt, name, idNo, idType, remark, cmpayChannelConfig.getThirdMerid(), cmpayChannelConfig.getRsaprikey(), cmpayChannelConfig.getRsapubkey());
		            	 logger.info("银联代扣返回对象为：[{}]",resp.toString());
		             if(resp!=null){
		            		 cmpayCutOrder.setThirdOrderNo(resp.getThirdPartyOrderNo());
		 					CpErrorCodeEnum cpErrorCodeEnum=CpErrorCodeEnum.getByRespCode(resp.getResponseCode());
		 					payCode=cpErrorCodeEnum.getCoreRespCode();
		 					payMsg=cpErrorCodeEnum.getCoreRespMsg();

		 					cmpayCutOrder.setRespCode(payCode);
		 					cmpayCutOrder.setRespMsg(payMsg);
		 					cmpayCutOrder.setThirdRespCode(resp.getResponseCode());
		 					cmpayCutOrder.setThirdRespMsg(resp.getMessage());
		 					cmpayCutOrder.setThirdOrderNo(resp.getCutOrderNo());
		 				}
		 				if (resp == null || StringUtils.isBlank(resp.getTransStat())) {
		 					logger.info("银联无返回对象!");
		 					payCode=Constants.TRADE_ERROR_8808_CODE;
		 					payMsg=Constants.TRADE_ERROR_8808_MSG;
		 					cmpayCutOrder.setPayStatus(PayStatusEnum.DEALING.name());
		 					cmpayCutOrder.setRemark(Constants.TRADE_ERROR_8808_MSG);
		 					cmpayCutOrder.setRespCode(payCode);
		 					cmpayCutOrder.setRespMsg(payMsg);
		 					payStatus=PayStatusEnum.DEALING.name();
		 				} else if (resp.getTransStat().equals("S")) {
		 					cmpayCutOrder.setPayStatus(PayStatusEnum.SUCC.name());
		 					payStatus = PayStatusEnum.SUCC.name();
		 				} else if (resp.getTransStat().equals("U")) {
		 					cmpayCutOrder.setPayStatus(PayStatusEnum.DEALING.name());
		 					payStatus=PayStatusEnum.DEALING.name();
		 				} else if (resp.getTransStat().equals("F")) {
		 					cmpayCutOrder.setPayStatus(PayStatusEnum.FAIL.name());
		 					payStatus=PayStatusEnum.FAIL.name();

		 				} else {
		 					cmpayCutOrder.setPayStatus(PayStatusEnum.DEALING.name());
		 					payStatus=PayStatusEnum.DEALING.name();

		 				}

		 				break;

		             case CMPAY0002:
		            	 CpJYTRespDef def = jYTPayService.setCollection(cmpayChannelConfig.getThirdMerid(), cmpayChannelConfig.getRsapubkey(), cmpayChannelConfig.getRsaprikey(), cmpayChannelConfig.getAppsectet(),
		            			 orderId, bankName, cardNo, name, transAmt.toString(), idType, idNo, bankMobile, remark, userId, origOrderNo);
		            	  logger.info("金运通代收返回报文：[{}]",def.toString());
		 				if(def!=null){

			 					JYTErrorCodeEnum jYTErrorCodeEnum=JYTErrorCodeEnum.getByRespCode(def.getResp_code());
			 					payCode=jYTErrorCodeEnum.getCoreRespCode();
			 					payMsg=jYTErrorCodeEnum.getCoreRespMsg();

			 					cmpayCutOrder.setRespCode(payCode);
			 					cmpayCutOrder.setRespMsg(payMsg);
			 					cmpayCutOrder.setThirdRespCode(def.getResp_code());
			 					cmpayCutOrder.setThirdRespMsg(def.getDescription());
			 					cmpayCutOrder.setThirdOrderNo(def.getTran_flowid());

		 				}

		 				if(def != null && def.getTran_state().equals("01")){

		 					cmpayCutOrder.setPayStatus(PayStatusEnum.SUCC.name());
		 					payStatus = PayStatusEnum.SUCC.name();
		 					payMsg="交易成功";
		 					cmpayCutOrder.setRespMsg(payMsg);

		 				}else if(def!= null && def.getTran_state().equals("03")){
		 					cmpayCutOrder.setPayStatus(PayStatusEnum.FAIL.name());
		 					payStatus=PayStatusEnum.FAIL.name();

		 				}else{
		 					cmpayCutOrder.setPayStatus(PayStatusEnum.DEALING.name());
		 					payStatus=PayStatusEnum.DEALING.name();
		 				}

		           	  break;

		             default:
		            	 logger.info("不支持的渠道");
		            	 respCode=Constants.FAILED_CODE;
		            	 respMsg="不支持的渠道";
		            	 break;
		          }

		          cmpayCutOrder.setModifyTime(new Date());
		          cmpayCutOrderMapper.updateByPrimaryKeySelective(cmpayCutOrder);

		            result.put(Constants.RESPCODE_KEY, respCode);
					result.put(Constants.RESPMSG_KEY, respMsg);


					result.put("payOrderId", orderId);
					result.put("origiOrderId", origOrderNo);
					result.put(Constants.PAYCODE_KEY, payCode);
					result.put(Constants.PAYMSG_KEY, payMsg);
					result.put(Constants.PAYSTATUS_KEY, payStatus);

		            return result;
	}

	@Override
	public SinPayResp doSinPay(String merchantId, String orderId, String inchannel, PayWayEnum payWayEnum,
			String cardNo, String name, String bankCode, String bankName, BigDecimal transAmt, String userId,
			String origOrderNo, String transType, String notifyUrl, String orderip,String province,String city, String remark) {

		logger.info("[doSinPay]开始单笔代付,orderId=[{}],inchannel=[{}],payWayEnum=[{}],cardNO=[{}],origOrderNo=[{}]",orderId,inchannel,payWayEnum.name(),cardNo,origOrderNo);
		SinPayResp sinPayResp=new SinPayResp();
		String respCode=Constants.SUCCESS_CODE;
		String respMsg="ok";
		String payCode=null;
		String payMsg=null;
		String payStatus=null;

		//创建代付订单
		CmpayPayOrder order = new CmpayPayOrder();
		order.setOrderId(orderId);
		order.setInchannel(inchannel);
		order.setMerNo(merchantId);
		order.setUserId(userId);
		order.setOrigOrderNo(origOrderNo);
		order.setBackUrl(null);
		order.setBankCode(bankCode);
		order.setCardNo(cardNo);


		order.setCreateTime(new Date());
		order.setCustId(userId);
		order.setOrderDesc(remark);
		order.setNotifyUrl(notifyUrl);
		order.setOrigOrderNo(origOrderNo);
		order.setSinpayChannelCode(payWayEnum.name());
		order.setPayType(payWayEnum.name());
		order.setTransAmt(transAmt);
		order.setPayStatus(PayStatusEnum.DEALING.name());
		order.setProv(province);
		order.setCity(city);
		order.setSubBank("");
		order.setCustName(name);
		String orderDt = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		order.setOrderDt(orderDt);
		order.setPayChanelInteracctno("");
		order.setCustAcctno("");
		order.setReAcct((short) 0);
		order.setInAcct((short) 0);
		//检查notifyUrl是否为空
		if(StringUtils.isBlank(notifyUrl)){//notifyUrl为空，说明不用通知
			order.setHasnotify((short) 0);
		}else{//不为空，说明需要通知
			order.setHasnotify((short) 1);
		}
		order.setNotifyCount((long)0);

		order.setVersion(0);


		//查配置信息，后续放入redis
		CmpayChannelConfigExample cccCondition=new CmpayChannelConfigExample();
		cccCondition.createCriteria().andMerNoEqualTo(merchantId).andPaychannelNoEqualTo(payWayEnum.name());
		List<CmpayChannelConfig> cmpayChannelConfiglist=cmpayChannelConfigMapper.selectByExample(cccCondition);
		if(cmpayChannelConfiglist.size()<=0){
			logger.info("该商户缺少配置信息！！！");
			order.setPayStatus(PayStatusEnum.FAIL.name());
			order.setRemark("商户缺少配置信息");
			respCode=Constants.TRADE_ERROR_8806_CODE;
			respMsg=Constants.TRADE_ERROR_8806_MSG;
			order.setRespCode(respCode);
			order.setRespMsg(respMsg);
			cmpayPayOrderMapper.insert(order);

			sinPayResp.setRespCode(respCode);
			sinPayResp.setRespMsg(respMsg);

			return sinPayResp;

		}

		CmpayChannelConfig cmpayChannelConfig=cmpayChannelConfiglist.get(0);
		logger.debug("paychannel config-->[{}]",cmpayChannelConfig.toString());
		cmpayPayOrderMapper.insert(order);

		//开始单笔代付
		//根据渠道信息调用相应渠道
        switch(payWayEnum){
           case CMPAY0001:
        	   CpSinPayRespDef resp=null;
        	   try{
        	     resp=chinapayService.sinPay(orderId, orderId, cardNo, userId, bankCode, bankName, transAmt, "sinpay", name, province, city, "", cmpayChannelConfig.getThirdMerid());
          	    logger.info("银联代付返回对象为：[{}]",resp.toString());
        	   }catch(Exception e){
        		   logger.error("银联代付出现异常！",e);
        		   payStatus=PayStatusEnum.DEALING.name();
        		   order.setPayStatus(payStatus);
				order.setRemark("银联代付出现异常！");
				payCode = Constants.TRADE_ERROR_8900_CODE;
				payMsg = Constants.TRADE_ERROR_8900_MSG;
				order.setRespCode(respCode);
				order.setRespMsg(respMsg);
				cmpayPayOrderMapper.updateByPrimaryKeySelective(order);

				sinPayResp.setRespCode(respCode);
				sinPayResp.setRespMsg(respMsg);
				sinPayResp.setPayCode(payCode);
				sinPayResp.setPayMsg(payMsg);
				sinPayResp.setPayStatus(payStatus);

				return sinPayResp;
        	   }
            if(resp!=null){

					CpPayRespCodeEnum cpPayRespCode = CpPayRespCodeEnum.getByRespCode(resp.getResponseCode());
					CpPayRespCodeEnum cpPayRespStat = CpPayRespCodeEnum.getByRespCode(resp.getStat());
					String statMsg = StringUtils.isNotBlank(resp.getStat())?cpPayRespStat.getCoreRespMsg():"";
					String message = cpPayRespCode.getCoreRespMsg()+statMsg;

					payCode = cpPayRespCode.getRespCode();
					payMsg = cpPayRespCode.getCoreRespMsg();


					order.setRespCode(payCode);
					order.setRespMsg(message);
					order.setThirdRespCode(resp.getResponseCode());
					order.setThirdRespMsg(resp.getRespMsg());
					order.setThirdOrderNo(resp.getThirdPartOrderNo());

				}
				if (resp == null || StringUtils.isBlank(resp.getTransStat())) {
					logger.info("银联无返回对象!");
					payCode=Constants.TRADE_ERROR_8808_CODE;
					payMsg=Constants.TRADE_ERROR_8808_MSG;
					order.setRespCode(payCode);
					order.setRespMsg(payMsg);
					order.setPayStatus(PayStatusEnum.DEALING.name());
					order.setRemark(Constants.TRADE_ERROR_8808_MSG);

					payStatus=PayStatusEnum.DEALING.name();
				} else if (resp.getTransStat().equals("S")) {
					order.setPayStatus(PayStatusEnum.SUCC.name());
					payStatus = PayStatusEnum.SUCC.name();
				} else if (resp.getTransStat().equals("U")) {
					order.setPayStatus(PayStatusEnum.DEALING.name());
					payStatus=PayStatusEnum.DEALING.name();
				} else if (resp.getTransStat().equals("F")) {
					order.setPayStatus(PayStatusEnum.FAIL.name());
					payStatus=PayStatusEnum.FAIL.name();

				} else {
					order.setPayStatus(PayStatusEnum.DEALING.name());
					payStatus=PayStatusEnum.DEALING.name();

				}

				break;


           default:
          	 logger.info("不支持的渠道");
          	 respCode=Constants.FAILED_CODE;
          	 respMsg="不支持的渠道";
          	 break;
        }

        cmpayPayOrderMapper.updateByPrimaryKeySelective(order);

		sinPayResp.setRespCode(respCode);
		sinPayResp.setRespMsg(respMsg);
		sinPayResp.setPayCode(payCode);
		sinPayResp.setPayMsg(payMsg);
		sinPayResp.setPayStatus(payStatus);


		return sinPayResp;
	}



}
