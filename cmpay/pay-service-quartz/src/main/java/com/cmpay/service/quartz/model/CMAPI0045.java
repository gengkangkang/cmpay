package com.cmpay.service.quartz.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *  商户结算
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月11日 下午4:21:17
 *
 */
@XStreamAlias("CMAPI0045Rq")
public class CMAPI0045 {

	private CommonRqHdr CommonRqHdr;
	private String MerchantNo;
	private String ChannelOrderNo;
	private String Amount;

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



}
