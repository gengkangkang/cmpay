package com.cmpay.facade.trade;

import java.util.Map;

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

}
