package org.pay.service.jytpay.service;

import java.util.Map;

import org.pay.service.jytpay.model.CpJYTRespDef;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;

/**
 * 金运通相关服务
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月7日 上午10:12:07
 *
 */
public interface JYTPayService {

	Map<String,String> JYTAuth(String merNo,String pubKey,String privKey,String privPwd,String bank_card_no, String bank_code,String id_num, String id_name, String terminal_type, SignCardTypeEnum  bank_card_type, String phone_no,IdTypeEnum idType);
	CpJYTRespDef setCollection(String merNo,String pubKey,String privKey,String privPwd,String orderNo,String bank_name,String account_no,String account_name,String tran_amt,IdTypeEnum idType,String cert_no,String mobile,String remark,String cust_id,String orgi_order_id);
}
