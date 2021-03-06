package com.cmpay.service.chinapay.service;

import java.math.BigDecimal;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.service.chinapay.model.CpAuthBgRespDef;
import com.cmpay.service.chinapay.model.CpSinCutQueryRespDef;
import com.cmpay.service.chinapay.model.CpSinCutRespDef;
import com.cmpay.service.chinapay.model.CpSinPayQueryRespDef;
import com.cmpay.service.chinapay.model.CpSinPayRespDef;

/**
 * 银联相关服务类
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月25日 下午4:00:31
 *
 */
public interface ChinapayService {

    /**
     * 银联后台认证
     * @param cardNo
     * @param idType
     * @param idNo
     * @param cardType
     * @param custName
     * @param cardPhone
     * @return
     */
	public CpAuthBgRespDef bgAuth(String appSysId,String authPrivateKey,String cardNo, IdTypeEnum idType, String idNo,
			SignCardTypeEnum cardType, String custName, String cardPhone);

	/**
	 * 单笔代扣
	 * @param orderId
	 * @param cardNo
	 * @param custId
	 * @param bankCode
	 * @param transAmt
	 * @param custName
	 * @param idNo
	 * @param idType
	 * @param description
	 * @param merId
	 * @param privkey
	 * @param pubKey
	 * @return
	 */
	public CpSinCutRespDef sinCut(String orderId, String cardNo, String custId, String bankCode, BigDecimal transAmt, String custName,
			String idNo,IdTypeEnum idType, String description,String merId,String privkey,String pubKey);

	public CpSinCutQueryRespDef sinCutQuery(String orderNo,String merId,String privkey,String pubKey);


	/**
	 * chinapay支付捷 - 单笔代付交易 ResponseCode : 0000-成功
	 *
	 * @param transInf
	 * @return
	 */
	public CpSinPayRespDef sinPay(String payOrderNo,String transId, String cardNo, String custId, String bankId,String bankName, BigDecimal transAmt, String purpose,
			String custName, String province, String city, String subBank,String merId);

	/**
	 * chinapay支付捷 - 单笔代付交易查询 merDate 固定格式'yyyyMMdd'
	 *
	 * @return
	 */
	public CpSinPayQueryRespDef sinPayQuery(String orderNo,String merId);

}
