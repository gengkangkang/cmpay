package com.cmpay.service.bank.service;

import java.util.Map;

import com.cmpay.common.enums.AuthChannelEnum;
import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月28日 上午10:39:00
 *
 */
public interface PayAuthService {
      public Map<String,String> doPayAuth(String merchantId,String userId,String inchannel,AuthChannelEnum authChannel,String cardNo,SignCardTypeEnum cardType,String idNo,IdTypeEnum idType,String name,String bankMobile,String bankCode,String terminalType);
}
