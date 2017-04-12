package com.cmpay.service.bank.service;

import java.math.BigDecimal;
import java.util.Map;

import com.cmpay.common.enums.AuthChannelEnum;
import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.PayWayEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.service.bank.model.SinPayResp;

/**
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月6日 下午5:51:01
 *
 */
public interface PayService {
      /**
       * 认证
       * @param merchantId
       * @param userId
       * @param inchannel
       * @param authChannel
       * @param cardNo
       * @param cardType
       * @param idNo
       * @param idType
       * @param name
       * @param bankMobile
       * @param bankCode
       * @param terminalType
       * @return
       */
      public Map<String,String> doPayAuth(String merchantId,String userId,String inchannel,AuthChannelEnum authChannel,String cardNo,SignCardTypeEnum cardType,String idNo,IdTypeEnum idType,String name,String bankMobile,String bankCode,String terminalType);
      /**
       * 快捷代扣
       * @param merchantId
       * @param orderId
       * @param inchannel
       * @param payWayEnum
       * @param cardNo
       * @param idNo
       * @param idType
       * @param name
       * @param bankMobile
       * @param bankCode
       * @param bankName
       * @param transAmt
       * @param userId
       * @param origOrderNo
       * @param remark
       * @return
       */
      public Map<String,Object> doPayCut(String merchantId,String orderId,String inchannel,PayWayEnum payWayEnum,String cardNo,String idNo,IdTypeEnum idType,
    		  String name,String bankMobile,String bankCode,String bankName,BigDecimal transAmt,String userId,String origOrderNo,
    		  String transType,String isAcct,String notifyUrl,String toAcctNo,String orderip,String remark);

      public SinPayResp doSinPay(String merchantId,String orderId,String inchannel,PayWayEnum payWayEnum,String cardNo,
    		  String name,String bankCode,String bankName,BigDecimal transAmt,String userId,String origOrderNo,
    		  String transType,String notifyUrl,String orderip,String province,String city,String remark);

}
