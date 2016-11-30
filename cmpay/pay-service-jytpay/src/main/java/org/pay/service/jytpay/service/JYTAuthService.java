package org.pay.service.jytpay.service;

import java.util.Map;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月25日 下午4:44:51
 *
 */
public interface JYTAuthService {

	Map<String,String> JYTAuth(String merNo,String pubKey,String privKey,String privPwd,String bank_card_no, String bank_code,String id_num, String id_name, String terminal_type, SignCardTypeEnum  bank_card_type, String phone_no,IdTypeEnum idType);
}
