package com.cmpay.service.rule.service;

import com.cmpay.service.rule.bean.RuleResp;

/**
 * 路由规则
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月12日 下午1:34:28
 *
 */
public interface RuleService {

	/**
	 * 查询商户是否支持指定的支付渠道
	 * @param merchantId
	 * @param payCode
	 * @param bankCode
	 * @return
	 */
	public RuleResp queryIsSupByMer(String merchantId,String payCode,String bankCode);

	public RuleResp payRule(String merchantId,String transAmt,String userId,String bankCode,String cardNo);

}
