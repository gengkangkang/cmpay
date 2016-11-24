package com.cmpay.weixin.service;

import java.math.BigDecimal;
import java.util.Map;

import com.cmpay.weixin.vo.PayPageShowVo;
import com.cmpay.weixin.vo.QueryResult;
import com.cmpay.weixin.vo.ScanPayResultVo;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月14日 下午5:37:34
 *
 *  微信支付接口类
 *
 */
public interface PaymentService {

    /**
     * 预下单接口
     * @param merchantId
     * @param inchannel
     * @param userId
     * @param transType
     * @param orderId
     * @param amount
     * @param orderIp
     * @param productName
     * @return
     */
	public PayPageShowVo createPreOrder(String merchantId,String inchannel,String userId,String transType,String orderId,BigDecimal amount,String orderIp,String productName);
   /**
    * 微信支付统一下单接口
    * @param cmpayOrder
    * @param productName
    * @return
    */
	public ScanPayResultVo unifiedorder(String cmpayOrder,String productName);

	/**
	 * 接受微信异步通知
	 * @param notifyMap
	 * @return
	 */
	public String acceptWXNotify(Map<String, String> notifyMap);

	/**
	 * 查询微信待处理中订单
	 */
	public void QueryWXOrder();

	/**
	 * 根据订单号查询订单状态
	 * @param orderId
	 * @return
	 */
	public QueryResult OrderQuery(String orderId);

	/**
	 * 处理超时过期订单
	 */
	public void doExpireOrder();

}
