package com.cmpay.service.chinapay.service;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.service.chinapay.model.CpAuthBgRespDef;

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

}
