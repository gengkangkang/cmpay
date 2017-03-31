package com.cmpay.gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.exception.TradeBizException;
import com.cmpay.common.util.Constants;
import com.cmpay.facade.entity.PayRefundRq;
import com.cmpay.facade.entity.PayRefundRs;
import com.cmpay.facade.entity.QueryPayCutRq;
import com.cmpay.facade.entity.QueryPayCutRs;
import com.cmpay.facade.trade.UpayService;

/**
 * 支付相关接口
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月13日 下午4:21:54
 *
 */
@RestController
@RequestMapping("/payment")
public class PayAction extends BaseAction {
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
    UpayService upayService;


//	@RequestMapping(value="/payCut.do",method=RequestMethod.POST)
//    public String payCut(@RequestBody String str){
//		logger.info("[payCut]接受到的字符串================"+str);
//        Map<String,Object> result=new HashMap<String,Object>();
//        Map<String,String> data=new HashMap<String,String>();
//
//        try{
//    	 String decjson=new String(Base64.decodeBase64(str),"UTF-8");
//         logger.info("decjson="+decjson);
//         JSONObject json=JSONObject.parseObject(decjson);
//         String sign=json.getString("sign");
//         String encMsg=json.getString("encMsg");
//         String encKey=json.getString("encKey");
//         //验证签名
//         boolean signFlag=this.verifySign(sign, encMsg);
//         if(signFlag){
//        	 logger.info("验签通过");
//            //解密报文  {}
//           String payDataJson=this.decMsg(encMsg, encKey);
//           logger.info("payDataJson={}",payDataJson);
//           JSONObject jsonObject=JSONObject.parseObject(payDataJson);
//           String merchantId=jsonObject.getString("merchantId");
//           String inchannel=jsonObject.getString("inchannel");
//           String userId=jsonObject.getString("userId");
//           String amount=jsonObject.getString("amount");
//           String cardNo=jsonObject.getString("cardNo");
//           String origiOrderId=jsonObject.getString("origiOrderId");
//           String payCode=jsonObject.getString("payCode");
//           String transType=jsonObject.getString("transType");
//           String orderIp=jsonObject.getString("orderIp");
//           String idNo=jsonObject.getString("idNo");
//           String idType=jsonObject.getString("idType");
//           String name=jsonObject.getString("name");
//           String bankMobile=jsonObject.getString("bankMobile");
//           String bankCode=jsonObject.getString("bankCode");
//
//
//           Map<String,Object> map=upayService.payCut(merchantId, inchannel, userId, amount, cardNo, origiOrderId, payCode, transType, orderIp, idNo, idType, name, bankMobile, bankCode, "");
//           result=map;
//
//         }else{
//        	 logger.info("签名校验失败");
//        	 throw new TradeBizException(Constants.TRADE_ERROR_8817_CODE,Constants.TRADE_ERROR_8817_MSG);
//         }
//
//        }catch(TradeBizException tbe){
//            logger.info("业务异常,code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
//            result.put(Constants.RESPCODE_KEY, tbe.getCode());
//            result.put(Constants.RESPMSG_KEY, tbe.getMsg());
//  		}catch(Exception e){
//        	logger.info("调用代扣接口发生异常！！！！");
//        	result.put(Constants.RESPCODE_KEY, Constants.SUCCESS_CODE);
//	        result.put(Constants.RESPMSG_KEY, "竟然出现意外了，订单支付失败");
//
//            data.put(Constants.PAYCODE_KEY,Constants.TRADE_ERROR_C9999_CODE);
//			data.put(Constants.PAYMSG_KEY, Constants.TRADE_ERROR_C9999_MSG);
//			data.put(Constants.PAYSTATUS_KEY, PayStatusEnum.FAIL.name());
//            result.put(Constants.PAYDATA_KEY, data);
//        	e.printStackTrace();
//        }
//
//
//        return this.encMsg(result);
//
//    }


	@RequestMapping(value="/payCut",method=RequestMethod.POST)
    public String payCut(@RequestBody String str){
		logger.info("[payCut]接受到的字符串================"+str);
        Map<String,Object> result=new HashMap<String,Object>();
        String merchantId=null;
        try{
        	JSONObject jsonObject=JSONObject.parseObject(str);
        	String sign=jsonObject.getString("sign");
            merchantId=jsonObject.getString("merchantId");
            if(StringUtils.isBlank(merchantId)){
            	 logger.info("商户号不能为空");
            	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,"商户号不能为空");
            }
         //验证签名
         boolean signFlag=this.verifyMD5Sign(sign, jsonObject, merchantId);
         if(signFlag){
        	 logger.info("验签通过");
           String inchannel=jsonObject.getString("inchannel");
           String userId=jsonObject.getString("userId");
           String amount=jsonObject.getString("amount");
           String cardNo=jsonObject.getString("cardNo");
           String origiOrderId=jsonObject.getString("origiOrderId");
           String payCode=jsonObject.getString("payCode");
           String transType="01";
           String orderIp=jsonObject.getString("orderIp");
           String idNo=jsonObject.getString("idNo");
           String idType=jsonObject.getString("idType");
           String name=jsonObject.getString("name");
           String bankMobile=jsonObject.getString("bankMobile");
           String bankCode=jsonObject.getString("bankCode");


           Map<String,Object> map=upayService.payCut(merchantId, inchannel, userId, amount, cardNo, origiOrderId, payCode, transType, orderIp, idNo, idType, name, bankMobile, bankCode, "");
           result=map;

         }else{
        	 logger.info("签名校验失败");
        	 throw new TradeBizException(Constants.TRADE_ERROR_8817_CODE,Constants.TRADE_ERROR_8817_MSG);
         }

        }catch(TradeBizException tbe){
            logger.info("业务异常,code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
            result.put(Constants.RESPCODE_KEY, tbe.getCode());
            result.put(Constants.RESPMSG_KEY, tbe.getMsg());
  		}catch(Exception e){
        	logger.info("调用代扣接口发生异常！！！！");
        	result.put(Constants.RESPCODE_KEY, Constants.SUCCESS_CODE);
	        result.put(Constants.RESPMSG_KEY, "竟然出现意外了，订单支付失败");

	        result.put(Constants.PAYCODE_KEY,Constants.TRADE_ERROR_C9999_CODE);
	        result.put(Constants.PAYMSG_KEY, Constants.TRADE_ERROR_C9999_MSG);
	        result.put(Constants.PAYSTATUS_KEY, PayStatusEnum.FAIL.name());
        	e.printStackTrace();
        }
        //加入签名
        String resSign=this.genMD5Sign((JSONObject)JSON.toJSON(result), merchantId);
        result.put(Constants.SIGN_KEY, resSign);

        return JSON.toJSONString(result);

    }


	@RequestMapping(value="/payConsume",method=RequestMethod.POST)
    public String payConsumer(@RequestBody String str){
		logger.info("[payConsume]接受到的字符串================"+str);
        Map<String,Object> result=new HashMap<String,Object>();
        String merchantId=null;
        try{
        	JSONObject jsonObject=JSONObject.parseObject(str);
        	String sign=jsonObject.getString("sign");
            merchantId=jsonObject.getString("merchantId");
            if(StringUtils.isBlank(merchantId)){
            	 logger.info("商户号不能为空");
            	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,"商户号不能为空");
            }
         //验证签名
         boolean signFlag=this.verifyMD5Sign(sign, jsonObject, merchantId);
         if(signFlag){
        	 logger.info("验签通过");
           String inchannel=jsonObject.getString("inchannel");
           String userId=jsonObject.getString("userId");
           String amount=jsonObject.getString("amount");
           String cardNo=jsonObject.getString("cardNo");
           String origiOrderId=jsonObject.getString("origiOrderId");
           String payCode=jsonObject.getString("payCode");
           String transType="03";
           String orderIp=jsonObject.getString("orderIp");
           String idNo=jsonObject.getString("idNo");
           String idType=jsonObject.getString("idType");
           String name=jsonObject.getString("name");
           String bankMobile=jsonObject.getString("bankMobile");
           String bankCode=jsonObject.getString("bankCode");


           Map<String,Object> map=upayService.payConsume(merchantId, inchannel, userId, amount, cardNo, origiOrderId, payCode, transType, orderIp, idNo, idType, name, bankMobile, bankCode, "");
           result=map;

         }else{
        	 logger.info("签名校验失败");
        	 throw new TradeBizException(Constants.TRADE_ERROR_8817_CODE,Constants.TRADE_ERROR_8817_MSG);
         }

        }catch(TradeBizException tbe){
            logger.info("业务异常,code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
            result.put(Constants.RESPCODE_KEY, tbe.getCode());
            result.put(Constants.RESPMSG_KEY, tbe.getMsg());
  		}catch(Exception e){
        	logger.info("调用代扣接口发生异常！！！！");
        	result.put(Constants.RESPCODE_KEY, Constants.SUCCESS_CODE);
	        result.put(Constants.RESPMSG_KEY, "竟然出现意外了，订单支付失败");

	        result.put(Constants.PAYCODE_KEY,Constants.TRADE_ERROR_C9999_CODE);
	        result.put(Constants.PAYMSG_KEY, Constants.TRADE_ERROR_C9999_MSG);
	        result.put(Constants.PAYSTATUS_KEY, PayStatusEnum.FAIL.name());
        	e.printStackTrace();
        }
        //加入签名
        String resSign=this.genMD5Sign((JSONObject)JSON.toJSON(result), merchantId);
        result.put(Constants.SIGN_KEY, resSign);

        return JSON.toJSONString(result);

    }


	@RequestMapping(value="/queryOrder",method=RequestMethod.POST)
    public String queryOrder(@RequestBody String str){
		logger.info("[queryOrder]接受到的字符串================"+str);
		String merchantId=null;
		QueryPayCutRs res = new QueryPayCutRs();
		try{
			JSONObject jsonObject=JSONObject.parseObject(str);
        	String sign=jsonObject.getString("sign");
            merchantId=jsonObject.getString("merId");
            if(StringUtils.isBlank(merchantId)){
            	 logger.info("商户号不能为空");
            	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,"商户号不能为空");
            }
         //验证签名
         boolean signFlag=this.verifyMD5Sign(sign, jsonObject, merchantId);
         if(signFlag){
        	 QueryPayCutRq queryPayCutRq=JSON.parseObject(str, QueryPayCutRq.class);
    		 res=upayService.queryOrder(queryPayCutRq);

         }else{
        	 logger.info("签名校验失败");
        	 throw new TradeBizException(Constants.TRADE_ERROR_8817_CODE,Constants.TRADE_ERROR_8817_MSG);
         }
		}catch(TradeBizException tbe){
            logger.info("业务异常,code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
            res.setRespCode(tbe.getCode());
            res.setRespMsg(tbe.getMsg());
//            res.setSign(null);
//            return JSON.toJSONString(res);
  		}catch(Exception e){
			e.printStackTrace();
			res.setRespCode(Constants.EXCEPTION_CODE);
            res.setRespMsg("查询异常");
//            res.setSign(null);
//            return JSON.toJSONString(res);
		}
		 //加入签名
        String resSign=this.genMD5Sign((JSONObject)JSON.toJSON(res), merchantId);
        res.setSign(resSign);

		return JSON.toJSONString(res);
	}

	@RequestMapping(value="/queryCardInfoByCardNo",method=RequestMethod.POST)
    public String queryCardInfoByCardNo(@RequestBody String str){
		logger.info("[queryCardInfoByCardNo]接受到的字符串================"+str);
		Map<String,String> res=null;
		JSONObject jsonObject=JSONObject.parseObject(str);
		String cardNo=jsonObject.getString("cardNo");
		if(cardNo.length()<10){
			return JSON.toJSONString("非法的卡号");
		}
		 res=upayService.getCardInfoByCardNo(cardNo);

		return JSON.toJSONString(res);
	}


	@RequestMapping(value="/payRefund",method=RequestMethod.POST)
    public String payRefund(@RequestBody String str){
		logger.info("[payRefund]接受到的字符串================"+str);
		String merchantId=null;
		PayRefundRs res = new PayRefundRs();
		try{
			JSONObject jsonObject=JSONObject.parseObject(str);
        	String sign=jsonObject.getString("sign");
            merchantId=jsonObject.getString("merId");
            if(StringUtils.isBlank(merchantId)){
            	 logger.info("商户号不能为空");
            	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,"商户号不能为空");
            }
         //验证签名
         boolean signFlag=this.verifyMD5Sign(sign, jsonObject, merchantId);
         if(signFlag){
        	 PayRefundRq payRefundRq=JSON.parseObject(str, PayRefundRq.class);
    		 res=upayService.payRefund(payRefundRq);

         }else{
        	 logger.info("签名校验失败");
        	 throw new TradeBizException(Constants.TRADE_ERROR_8817_CODE,Constants.TRADE_ERROR_8817_MSG);
         }
		}catch(TradeBizException tbe){
            logger.info("业务异常,code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
            res.setRespCode(tbe.getCode());
            res.setRespMsg(tbe.getMsg());

  		}catch(Exception e){
			e.printStackTrace();
			res.setRespCode(Constants.EXCEPTION_CODE);
            res.setRespMsg("退款异常");

		}
		 //加入签名
        String resSign=this.genMD5Sign((JSONObject)JSON.toJSON(res), merchantId);
        res.setSign(resSign);

		return JSON.toJSONString(res);
	}

    /**
     * 卡认证
     * @param str
     * @return
     */
	@RequestMapping(value="/payAuth",method=RequestMethod.POST)
    public String payAuth(@RequestBody String str){
		logger.info("[payAuth]接受到的字符串================"+str);
		String merchantId=null;
		Map<String,String> res=new HashMap<String,String>();
		try{
			JSONObject jsonObject=JSONObject.parseObject(str);
        	String sign=jsonObject.getString("sign").trim();
            merchantId=jsonObject.getString("merchantId").trim();
            if(StringUtils.isBlank(merchantId)){
            	 logger.info("商户号不能为空");
            	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,"商户号不能为空");
            }
         //验证签名
         boolean signFlag=this.verifyMD5Sign(sign, jsonObject, merchantId);
         if(signFlag){
        	 String userId=jsonObject.getString("userId").trim();
        	 String inchannel=jsonObject.getString("inchannel").trim();
        	 String authChannel=jsonObject.getString("authChannel").trim();
        	 String cardNo=jsonObject.getString("cardNo").trim();
        	 String cardType=jsonObject.getString("cardType").trim();
        	 String idNo=jsonObject.getString("idNo").trim();
        	 String idType=jsonObject.getString("idType").trim();
        	 String name=jsonObject.getString("name").trim();
        	 String bankMobile=jsonObject.getString("bankMobile").trim();
        	 String bankCode=jsonObject.getString("bankCode").trim();
        	 String terminalType=jsonObject.getString("terminalType").trim();


    		 res=upayService.payAuth(merchantId, userId, inchannel, authChannel, cardNo, cardType, idNo, idType, name, bankMobile, bankCode, terminalType);


         }else{
        	 logger.info("[payAuth]签名校验失败");
        	 throw new TradeBizException(Constants.TRADE_ERROR_8817_CODE,Constants.TRADE_ERROR_8817_MSG);
         }
		}catch(TradeBizException tbe){
            logger.info("业务异常,code=[{}],msg=[{}]",tbe.getCode(),tbe.getMsg());
            res.put(Constants.CODE_KEY, tbe.getCode());
            res.put(Constants.MSG_KEY, tbe.getMsg());

  		}catch(Exception e){
			logger.info("调用代扣接口发生异常！！！！",e);
        	res.put(Constants.CODE_KEY, Constants.EXCEPTION_CODE);
	        res.put(Constants.MSG_KEY, "竟然出现意外了，卡认证失败");

		}
		 //加入签名
        String resSign=this.genMD5Sign((JSONObject)JSON.toJSON(res), merchantId);
        res.put(Constants.SIGN_KEY, resSign);

		return JSON.toJSONString(res);
	}

}
