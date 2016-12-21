package com.cmpay.service.jytpay.service;

import java.util.Map;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.service.jytpay.model.CpJYTRespDef;

/**
 * 金运通相关服务
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月7日 上午10:12:07
 *
 */
public interface JYTPayService {
    /**
     * 卡验证
     * @param merNo
     * @param pubKey
     * @param privKey
     * @param privPwd
     * @param bank_card_no
     * @param bank_code
     * @param id_num
     * @param id_name
     * @param terminal_type
     * @param bank_card_type
     * @param phone_no
     * @param idType
     * @return
     */
	Map<String,String> JYTAuth(String merNo,String pubKey,String privKey,String privPwd,String bank_card_no, String bank_code,String id_num, String id_name, String terminal_type, SignCardTypeEnum  bank_card_type, String phone_no,IdTypeEnum idType);
    /**
     * 代收
     * @param merNo
     * @param pubKey
     * @param privKey
     * @param privPwd
     * @param orderNo
     * @param bank_name
     * @param account_no
     * @param account_name
     * @param tran_amt
     * @param idType
     * @param cert_no
     * @param mobile
     * @param remark
     * @param cust_id
     * @param orgi_order_id
     * @return
     */
	CpJYTRespDef setCollection(String merNo,String pubKey,String privKey,String privPwd,String orderNo,String bank_name,String account_no,String account_name,String tran_amt,IdTypeEnum idType,String cert_no,String mobile,String remark,String cust_id,String orgi_order_id);

	CpJYTRespDef queryCollection(String merNo,String pubKey,String privKey,String privPwd,String ori_tran_flowid);

}
