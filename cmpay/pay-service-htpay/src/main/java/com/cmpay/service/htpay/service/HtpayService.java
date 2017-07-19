package com.cmpay.service.htpay.service;

import java.math.BigDecimal;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.service.htpay.model.HtSinCutQueryResp;
import com.cmpay.service.htpay.model.HtSinCutResp;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年6月26日 下午4:31:46
 *
 */
public interface HtpayService {

	HtSinCutResp sinCut(String orderId, String cardNo, String custId, String bankCode,String bankName, BigDecimal transAmt,
			String custName, String idNo,IdTypeEnum idType,String mobile,String cardType,String cardAttribute,
			String description,String merId,String privkey,String pubKey);

	HtSinCutQueryResp sinCutQuery(String orderNo,String merId,String privkey,String pubKey);
}
