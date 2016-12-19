package com.cmpay.service.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpay.common.enums.AuthChannelEnum;
import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.InChannelEnum;
import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.enums.PayWayEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.common.enums.TransTypeEnum;
import com.cmpay.common.exception.TradeBizException;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.Constants;
import com.cmpay.facade.trade.UpayService;
import com.cmpay.service.bank.service.PayAuthService;
import com.cmpay.service.bank.service.PayService;
import com.cmpay.service.rule.bean.RuleResp;
import com.cmpay.service.rule.service.RuleService;
import com.cmpay.service.trade.dao.CmpayPayChannelMapper;
import com.cmpay.service.trade.dao.CmpaySuppBankMapMapper;
import com.cmpay.service.trade.dao.CmpayTradeRecordMapper;
import com.cmpay.service.trade.model.CmpayPayChannel;
import com.cmpay.service.trade.model.CmpayPayChannelExample;
import com.cmpay.service.trade.model.CmpaySuppBankMap;
import com.cmpay.service.trade.model.CmpaySuppBankMapExample;
import com.cmpay.service.trade.model.CmpayTradeRecord;
import com.cmpay.service.trade.model.CmpayTradeRecordExample;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月29日 上午10:34:13
 *
 */
@Service
public class UpayServiceImpl implements UpayService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	CmpayPayChannelMapper cmpayPayChannelMapper;
	@Autowired
	CmpaySuppBankMapMapper cmpaySuppBankMapMapper;
	@Autowired
	CmpayTradeRecordMapper cmpayTradeRecordMapper;
	@Autowired
	PayAuthService payAuthService;
	@Autowired
	PayService payService;
	@Autowired
	RuleService ruleService;

	@Override
	public Map<String, String> payAuth(String merchantId, String userId, String inchannel, String authChannel,
			String cardNo, String cardType, String idNo, String idType, String name, String bankMobile, String bankCode,
			String terminalType) {
		//1.校验参数
        String code=Constants.FAILED_CODE;
        String msg=null;
        logger.info("卡认证请求参数：merchantId=[{}],userId=[{}],inchannel=[{}],authChannel=[{}],cardNo=[{}],cardType=[{}],idNo=[{}],idType=[{}],name=[{}],"
        		+ "bankMobile=[{}],bankCode=[{}],terminalType=[{}]",
        		merchantId,userId,inchannel,authChannel,cardNo,cardType,idNo,idType,name,bankMobile,bankCode,terminalType);
        Map<String,String> result=new HashMap<String,String>();
        if(StringUtils.isBlank(merchantId)||StringUtils.isBlank(userId)||StringUtils.isBlank(inchannel)||StringUtils.isBlank(authChannel)
        		||StringUtils.isBlank(cardNo)||StringUtils.isBlank(cardType)||StringUtils.isBlank(idNo)||StringUtils.isBlank(idType)
        		||StringUtils.isBlank(name)||StringUtils.isBlank(bankMobile)||StringUtils.isBlank(bankCode)){
        	logger.info("请检查请求参数！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_CODE);
        	result.put(Constants.MSG_KEY, "请检查请求参数");
        	return result;

        }

        if(StringUtils.equals(authChannel, AuthChannelEnum.CMPAY0002.name())){
        	if(StringUtils.isBlank(terminalType)){
        		logger.info("缺少认证渠道必要参数！！！");
            	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_CODE);
            	result.put(Constants.MSG_KEY, "缺少认证渠道必要参数");
            	return result;
        	}
        }

        if(!InChannelEnum.contains(inchannel)){
        	logger.info("非法的渠道！！！");
        	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8805_CODE);
        	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8805_MSG);
        	return result;
        }

        if(!AuthChannelEnum.contains(authChannel)){
        	logger.info("不支持的认证渠道！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_8902_CODE);
        	result.put(Constants.MSG_KEY, Constants.PARA_ERROR_8902_MSG);
        	return result;
        }

        if(!IdTypeEnum.contains(idType)){
        	logger.info("不支持的证件类型！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_8903_CODE);
        	result.put(Constants.MSG_KEY, Constants.PARA_ERROR_8903_MSG);
        	return result;
        }

        if(!SignCardTypeEnum.contains(cardType)){
        	logger.info("不支持的卡类型！！！");
        	result.put(Constants.CODE_KEY, Constants.PARA_ERROR_8904_CODE);
        	result.put(Constants.MSG_KEY, Constants.PARA_ERROR_8904_MSG);
        	return result;
        }

        //2.判断该商户、渠道是否开通实名认证功能，后续配置信息存于redis中
        CmpayPayChannelExample cmpayPayChannelExample=new CmpayPayChannelExample();
        cmpayPayChannelExample.createCriteria().andMerchNoEqualTo(merchantId).andCodeEqualTo(authChannel);
        List<CmpayPayChannel> cpclist=cmpayPayChannelMapper.selectByExample(cmpayPayChannelExample);
        if(cpclist.size()<=0){
        	logger.info("商户[{}]不支持该渠道{}",merchantId,authChannel);
        	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8801_CODE);
        	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8801_MSG);
        	return result;
        }

        CmpayPayChannel cmpayPayChannel=cpclist.get(0);
        if(!StringUtils.equals(Constants.ON, cmpayPayChannel.getCardAuthOpenStatus())){
        	logger.info("商户[{}]该认证渠道[{}]已关闭",merchantId,authChannel);
        	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8802_CODE);
        	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8802_MSG);
        	return result;
        }

        //3.根据核心bankcode和支付渠道编码，查出支付渠道的bankcode(可选)
//        if(StringUtils.equals(authChannel, AuthChannelEnum.CMPAY0002.name())){
        	CmpaySuppBankMapExample cmpaySuppBankMapExample=new CmpaySuppBankMapExample();
        	cmpaySuppBankMapExample.createCriteria().andPayChannelCodeEqualTo(authChannel).andPayBankCodeEqualTo(bankCode);
            List<CmpaySuppBankMap> csbmlist=cmpaySuppBankMapMapper.selectByExample(cmpaySuppBankMapExample);
            if(csbmlist.size()<=0){
            	logger.info("渠道[{}]不支持该银行[{}]",authChannel,bankCode);
            	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8803_CODE);
            	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8803_MSG);
            	return result;
            }

            CmpaySuppBankMap cmpaySuppBankMap=csbmlist.get(0);
            if(!StringUtils.equals(Constants.ON, cmpaySuppBankMap.getStatus())){
            	logger.info("支付渠道[{}]该银行[{}]已关闭",authChannel,bankCode);
            	result.put(Constants.CODE_KEY, Constants.TRADE_ERROR_8804_CODE);
            	result.put(Constants.MSG_KEY, Constants.TRADE_ERROR_8804_MSG);
            	return result;
            }

//        }
            String pcBankCode=cmpaySuppBankMap.getPcBankCode();

        //4.调用pay-service-bank服务验证接口

         Map<String,String> map=payAuthService.doPayAuth(merchantId, userId, inchannel, AuthChannelEnum.valueOf(authChannel), cardNo, SignCardTypeEnum.valueOf(cardType), idNo, IdTypeEnum.valueOf(idType), name, bankMobile, pcBankCode, terminalType);

         logger.info("返回值为："+map.toString());
		return map;
	}

	@Override
	public Map<String,Object> payCut(String merchantId,String inchannel,String userId,String amount,String cardNo,String origiOrderId,String payCode,String transType,String orderIp
			,String idNo,String idType,String name,String bankMobile,String bankCode,String bankName){

		Map<String,Object> result=new HashMap<String,Object>();
		Map<String,String> data=new HashMap<String,String>();
        CmpayTradeRecord cmpayTradeRecord=new CmpayTradeRecord();
        PayWayEnum payWayEnum=null;

		try{
       //1.校验前置或内部服务传的参数
			logger.info("payCut para:merchantId=[{}],inchannel=[{}],userId=[{}],amount=[{}],cardNo=[{}],origiOrderId=[{}],payCode=[{}],idNo=[{}],idType=[{}],name=[{}],bankMobile=[{}],bankName=[{}]"
					,merchantId,inchannel,userId,amount,cardNo,origiOrderId,payCode,idNo,idType,name,bankMobile,bankCode,bankName);
         if(StringUtils.isBlank(merchantId)||StringUtils.isBlank(inchannel)||StringUtils.isBlank(userId)||StringUtils.isBlank(amount)
        		 ||StringUtils.isBlank(cardNo)||StringUtils.isBlank(origiOrderId)||StringUtils.isBlank(idNo)||StringUtils.isBlank(idType)||StringUtils.isBlank(name)
        		 ||StringUtils.isBlank(bankMobile)||StringUtils.isBlank(bankCode)){
        	 logger.info("缺少必要参数");
        	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,Constants.PARA_ERROR_8905_MSG);

         }

         if(!InChannelEnum.contains(inchannel)){
         	logger.info("非法的渠道！！！");
       	    throw new TradeBizException(Constants.TRADE_ERROR_8805_CODE,Constants.TRADE_ERROR_8805_MSG);
         }

         if(StringUtils.isNotBlank(payCode)){
             if(!PayWayEnum.contains(payCode)){
              	logger.info("不支持的支付渠道！！！");
            	    throw new TradeBizException(Constants.TRADE_ERROR_8801_CODE,Constants.TRADE_ERROR_8801_MSG);
              }
         }

         TransTypeEnum transTypeEnum=TransTypeEnum.getByTransCode(transType);
         if(transTypeEnum==null){
        	 logger.info("不支持的交易类型！！！");
     	    throw new TradeBizException(Constants.TRADE_ERROR_8809_CODE,Constants.TRADE_ERROR_8809_MSG);
         }

         if(!IdTypeEnum.contains(idType)){
        	 throw new TradeBizException(Constants.TRADE_ERROR_8810_CODE,Constants.TRADE_ERROR_8810_MSG);
         }
      //订单查重
         CmpayTradeRecordExample cmpayTradeRecordExample=new CmpayTradeRecordExample();
         cmpayTradeRecordExample.createCriteria().andMerNoEqualTo(merchantId).andOrigOrderNoEqualTo(origiOrderId);
        List<CmpayTradeRecord> tlist=cmpayTradeRecordMapper.selectByExample(cmpayTradeRecordExample);
         if(tlist.size()>0){
        	 throw new TradeBizException(Constants.TRADE_ERROR_8814_CODE,Constants.TRADE_ERROR_8814_MSG);
         }

         //1.1 创建交易流水 CMPAY_RECORD
//         CmpayTradeRecord cmpayTradeRecord=new CmpayTradeRecord();
         cmpayTradeRecord.setId(CmpayUtils.getUUID());
         String orderId=CmpayUtils.createOrderId(Constants.KJPAY, transType);
         logger.info("生产支付订单号：[{}]",orderId);
         cmpayTradeRecord.setOrderId(orderId);
         cmpayTradeRecord.setInchannel(inchannel);
         cmpayTradeRecord.setMerNo(merchantId);
         cmpayTradeRecord.setUserId(userId);
         cmpayTradeRecord.setOrigOrderNo(origiOrderId);
         cmpayTradeRecord.setTransAmt(new BigDecimal(amount));
         cmpayTradeRecord.setTransType(transTypeEnum.name());
         cmpayTradeRecord.setPayChannel(payCode);
         cmpayTradeRecord.setPayStatus(PayStatusEnum.WAIT.name());
         cmpayTradeRecord.setPeriod(Constants.PERIOD);
         DateTime d=new DateTime();
 		 DateTime d1=d.plusMillis((int)Constants.PERIOD);
 		cmpayTradeRecord.setExpireTime(d1.toDate());
 		cmpayTradeRecord.setOrderip(orderIp);
 		cmpayTradeRecord.setCreateTime(new Date());
 		cmpayTradeRecord.setVersion(0);

 		cmpayTradeRecordMapper.insert(cmpayTradeRecord);

         //1.2根据userId查询用户绑卡信息,后续可将信息放入redis中,减少网络请求耗时
//          merchantId,orderId,inchannel,payWayEnum,cardNo,idNo,idType,
//		  name,bankMobile,bankCode,bankName,transAmt,userId,origOrderNo,
//		  transType,isAcct,notifyUrl,toAcctNo,orderip,remark


		//2.根据订单信息走自动路由规则
//        PayWayEnum payWayEnum=null;
         if(StringUtils.isBlank(payCode)){
        	 //走路由
        	 RuleResp resp=ruleService.payRule(merchantId, amount, userId, bankCode);
        	 logger.info("resp=[{}]",resp.toString());
           	 if(StringUtils.equals(Constants.SUCCESS_CODE, resp.getCode())){
        		 payWayEnum=PayWayEnum.getByCode(resp.getPayWay());
        		 bankCode=resp.getPcBankCode();
        		 bankName=resp.getPcBankName();
        	 }else{
        		 throw new TradeBizException(resp.getCode(),resp.getMsg());
        	 }

         }else{
        	 //判断商户是否支持其指定渠道
        	 RuleResp ruleResp= ruleService.queryIsSupByMer(merchantId, payCode,bankCode);
        	 logger.info("ruleResp=[{}]",ruleResp.toString());
        	 if(StringUtils.equals(Constants.SUCCESS_CODE, ruleResp.getCode())){
        		 payWayEnum=PayWayEnum.getByCode(ruleResp.getPayWay());
        		 bankCode=ruleResp.getPcBankCode();
        		 bankName=ruleResp.getPcBankName();
        	 }else{
        		 throw new TradeBizException(ruleResp.getCode(),ruleResp.getMsg());
        	 }
         }


		//3.走相应的渠道信息

         //需要根据userId去核心系统查询的参数 idNo, idType, name, bankMobile, bankCode,bankName,toAcctNo
         //test数据
//         String orderId=origiOrderId;
//         String idNo="110106198412232437";
//         IdTypeEnum idType=IdTypeEnum.I;
//         String name="王五";
//         String bankMobile="13621766042";
//         String bankCode="05";
//         String bankName="建设银行";
//          transType="01";
//         String isAcct="0";
//         String toAcctNo="666";
//         String orderip="192.168.0.1";
//         String remark="test";

         String isAcct="0";
         if(StringUtils.equals(transTypeEnum.name(), TransTypeEnum.RECHARGE.name())){
        	 isAcct="1";
         }


         IdTypeEnum idTypeEnum=IdTypeEnum.getByIdType(idType);

        result=payService.doPayCut(merchantId, orderId, inchannel, payWayEnum, cardNo, idNo,idTypeEnum, name, bankMobile, bankCode,
        		bankName, new BigDecimal(amount), userId, origiOrderId, transType, isAcct, "", "", orderIp, "");
		//4.组织相应信息

        String respCode=(String) result.get(Constants.RESPCODE_KEY);
		if(StringUtils.equals(respCode, Constants.SUCCESS_CODE)){
           Map<String,String> resdata=(Map<String, String>) result.get(Constants.PAYDATA_KEY);
           String respayCode=resdata.get(Constants.PAYCODE_KEY);
           String respayMsg=resdata.get(Constants.PAYMSG_KEY);
           String respayStatus=resdata.get(Constants.PAYSTATUS_KEY);
           cmpayTradeRecord.setRespCode(respayCode);
           cmpayTradeRecord.setRespMsg(respayMsg);
           cmpayTradeRecord.setPayStatus(respayStatus);


		}else{
			cmpayTradeRecord.setPayStatus(PayStatusEnum.FAIL.name());
			cmpayTradeRecord.setRespCode(respCode);
			cmpayTradeRecord.setRespMsg((String) result.get(Constants.RESPMSG_KEY));;

		}

		cmpayTradeRecord.setPayChannel(payWayEnum.name());
		cmpayTradeRecordMapper.updateByPrimaryKeySelective(cmpayTradeRecord);


		}catch(TradeBizException tbe){
          logger.info("业务异常code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
          result.put(Constants.RESPCODE_KEY, tbe.getCode());
          result.put(Constants.RESPMSG_KEY, tbe.getMsg());

            cmpayTradeRecord.setPayStatus(PayStatusEnum.FAIL.name());
			cmpayTradeRecord.setRespCode((String) result.get(Constants.RESPCODE_KEY));
			cmpayTradeRecord.setRespMsg((String) result.get(Constants.RESPMSG_KEY));
			cmpayTradeRecordMapper.updateByPrimaryKeySelective(cmpayTradeRecord);

		}catch(Exception e){
			logger.info("【payCut】-----------出现意料外的异常了-------------");
			result.put(Constants.RESPCODE_KEY, Constants.SUCCESS_CODE);
	        result.put(Constants.RESPMSG_KEY, "出现意外，请稍后查询订单状态");

	        data.put("payOrderId", origiOrderId);
            data.put("origiOrderId", origiOrderId);
            data.put(Constants.PAYCODE_KEY,Constants.TRADE_ERROR_C9999_CODE);
			data.put(Constants.PAYMSG_KEY, Constants.TRADE_ERROR_C9999_MSG);
			data.put(Constants.PAYSTATUS_KEY, PayStatusEnum.DEALING.name());
            result.put(Constants.PAYDATA_KEY, data);
            e.printStackTrace();

            cmpayTradeRecord.setPayStatus(PayStatusEnum.DEALING.name());
			cmpayTradeRecord.setRespCode(Constants.TRADE_ERROR_C9999_CODE);
			cmpayTradeRecord.setRespMsg(Constants.TRADE_ERROR_C9999_MSG);
			cmpayTradeRecordMapper.updateByPrimaryKeySelective(cmpayTradeRecord);

		}


		logger.info("[payCut]相应报文：{}",result.toString());
		return result;

	}

}
