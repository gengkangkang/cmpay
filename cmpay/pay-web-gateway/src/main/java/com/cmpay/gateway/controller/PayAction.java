package com.cmpay.gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.exception.TradeBizException;
import com.cmpay.common.util.Constants;
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


	@RequestMapping(value="/payCut.do",method=RequestMethod.POST)
    public String payCut(@RequestBody String str){
		logger.info("[payCut]接受到的字符串================"+str);
        Map<String,Object> result=new HashMap<String,Object>();
        Map<String,String> data=new HashMap<String,String>();

        try{
    	 String decjson=new String(Base64.decodeBase64(str),"UTF-8");
         logger.info("decjson="+decjson);
         JSONObject json=JSONObject.parseObject(decjson);
         String sign=json.getString("sign");
         String encMsg=json.getString("encMsg");
         String encKey=json.getString("encKey");
         //验证签名
         boolean signFlag=this.verifySign(sign, encMsg);
         if(signFlag){
        	 logger.info("验签通过");
            //解密报文  {}
           String payDataJson=this.decMsg(encMsg, encKey);
           logger.info("payDataJson={}",payDataJson);
           JSONObject jsonObject=JSONObject.parseObject(payDataJson);
           String merchantId=jsonObject.getString("merchantId");
           String inchannel=jsonObject.getString("inchannel");
           String userId=jsonObject.getString("userId");
           String amount=jsonObject.getString("amount");
           String cardNo=jsonObject.getString("cardNo");
           String origiOrderId=jsonObject.getString("origiOrderId");
           String payCode=jsonObject.getString("payCode");
           String transType=jsonObject.getString("transType");
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

            data.put(Constants.PAYCODE_KEY,Constants.TRADE_ERROR_C9999_CODE);
			data.put(Constants.PAYMSG_KEY, Constants.TRADE_ERROR_C9999_MSG);
			data.put(Constants.PAYSTATUS_KEY, PayStatusEnum.FAIL.name());
            result.put(Constants.PAYDATA_KEY, data);
        	e.printStackTrace();
        }


        return this.encMsg(result);

    }

}
