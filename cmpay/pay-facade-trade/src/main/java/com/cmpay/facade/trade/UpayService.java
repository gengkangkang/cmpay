package com.cmpay.facade.trade;

import java.util.Map;

import com.cmpay.facade.entity.PayRefundRq;
import com.cmpay.facade.entity.PayRefundRs;
import com.cmpay.facade.entity.QueryPayCutRq;
import com.cmpay.facade.entity.QueryPayCutRs;

/**
 * 支付相关接口
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月29日 上午10:22:28
 *
 */
public interface UpayService {
    /**
     * 银行卡验证
     * @param merchantId  商户号
     * @param userId
     * @param inchannel  接入渠道
     * @param authChannel  认证渠道（银联（CMPAY0001）,金运通 CMPAY0002）
     * @param cardNo  卡号
     * @param cardType 卡类型
     * @param idNo  证件号
     * @param idType 证件类型
     * @param name   姓名
     * @param bankMobile  银行预留手机号
     * @param bankCode   银行编码
     * @param terminalType 终端类型
     * @return
     */
    public Map<String,String> payAuth(String merchantId,String userId,String inchannel,String authChannel,String cardNo,String cardType,String idNo,String idType,String name,String bankMobile,String bankCode,String terminalType);
    /**
     * 快捷支付
     * @param merchantId
     * @param inchannel
     * @param userId
     * @param amount
     * @param cardNo
     * @param origiOrderId
     * @param payCode
     * @param transType
     * @param orderIp
     * @param idNo
     * @param idType
     * @param name
     * @param bankMobile
     * @param bankCode
     * @param bankName
     * @return
     */
    public Map<String,Object> payCut(String merchantId,String inchannel,String userId,String amount,String cardNo,String origiOrderId,String payCode,String transType,String orderIp,String idNo,String idType,String name,String bankMobile,String bankCode,String bankName);


    public Map<String,Object> payConsume(String merchantId,String inchannel,String userId,String amount,String cardNo,String origiOrderId,String payCode,String transType,String orderIp,String idNo,String idType,String name,String bankMobile,String bankCode,String bankName);

    /**
     * 根据卡号查询卡bin信息
     * @param cardNo
     * @return
     */
    public Map<String,String> getCardInfoByCardNo(String cardNo);

    /**
     * 查询订单状态
     * @param queryPayCutRq
     * @return
     */
    public QueryPayCutRs queryOrder(QueryPayCutRq queryPayCutRq);
     /**
      * 退款
      * @param payRefundRq
      * @return
      */
    public PayRefundRs payRefund(PayRefundRq payRefundRq);

    public Map<String,Object> SinPay(String merchantId,String inchannel,String payCode,String cardNo,
  		  String name,String bankCode,String bankName,String transAmt,String userId,String origOrderNo,
  		  String transType,String notifyUrl,String orderip,String province,String city,String remark);

}
