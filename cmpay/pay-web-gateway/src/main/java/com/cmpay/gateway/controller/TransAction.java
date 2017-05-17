package com.cmpay.gateway.controller;

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
import com.cmpay.common.exception.TradeBizException;
import com.cmpay.common.util.Constants;
import com.cmpay.facade.entity.QueryLimitAmtRq;
import com.cmpay.facade.entity.QueryLimitAmtRs;
import com.cmpay.facade.trade.QueryService;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年5月3日 下午3:29:07
 *
 */
@RestController
@RequestMapping("/trans")
public class TransAction extends BaseAction {
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
    QueryService queryService;

	@RequestMapping(value="/queryLimtAmt",method=RequestMethod.POST)
    public String queryLimtAmt(@RequestBody String str){
		logger.info("[queryLimtAmt]接受到的字符串================"+str);
		String merchantId=null;
		JSONObject jsonObject=null;
		QueryLimitAmtRq req=new QueryLimitAmtRq();
		QueryLimitAmtRs res = new QueryLimitAmtRs();
		try{
			 jsonObject=JSONObject.parseObject(str);
        	String sign=jsonObject.getString("sign");
            merchantId=jsonObject.getString("merId");
            if(StringUtils.isBlank(merchantId)){
            	 logger.info("商户号不能为空");
            	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,"商户号不能为空");
            }
         //验证签名
         boolean signFlag=this.verifyMD5Sign(sign, jsonObject, merchantId);
         if(signFlag){

             String inchannel=jsonObject.getString("inchannel");
             String cardNo=jsonObject.getString("cardNo");
             String bankCode=jsonObject.getString("bankCode");

             if(StringUtils.isBlank(inchannel)||StringUtils.isBlank(cardNo)){
            	 throw new TradeBizException(Constants.PARA_ERROR_8905_CODE,Constants.PARA_ERROR_8905_MSG);
             }
             req.setMerId(merchantId);
             req.setInchannel(inchannel);
             req.setBankCode(bankCode);
             req.setCardNo(cardNo);

    		 res=queryService.queryLimtAmt(req);

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
            res.setRespMsg("查询交易限额异常");

		}
		 //加入签名
        String resSign=this.genMD5Sign((JSONObject)JSON.toJSON(res), merchantId);
        res.setSign(resSign);

		return JSON.toJSONString(res);
	}

}
