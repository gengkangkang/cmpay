package com.cmpay.service.quartz.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 退款报文类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月26日 下午4:15:00
 *
 */
@XStreamAlias("CMAPI0046Rq")
public class CMPAY0046 {

	private CommonRqHdr CommonRqHdr;
	private String MerchantNo;
	private String ChannelOrderNo;
	private String Amount;
	private String UserId;
	public CommonRqHdr getCommonRqHdr() {
		return CommonRqHdr;
	}
	public void setCommonRqHdr(CommonRqHdr commonRqHdr) {
		CommonRqHdr = commonRqHdr;
	}
	public String getMerchantNo() {
		return MerchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		MerchantNo = merchantNo;
	}
	public String getChannelOrderNo() {
		return ChannelOrderNo;
	}
	public void setChannelOrderNo(String channelOrderNo) {
		ChannelOrderNo = channelOrderNo;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}


}
