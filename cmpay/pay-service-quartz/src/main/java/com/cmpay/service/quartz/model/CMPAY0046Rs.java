package com.cmpay.service.quartz.model;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月26日 下午5:23:20
 *
 */
public class CMPAY0046Rs {

	private CommonRsHdr CommonRsHdr;
	private String MerchantNo;
	private String Amount;
	private String UserId;

	public CommonRsHdr getCommonRsHdr() {
		return CommonRsHdr;
	}
	public void setCommonRsHdr(CommonRsHdr commonRsHdr) {
		CommonRsHdr = commonRsHdr;
	}
	public String getMerchantNo() {
		return MerchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		MerchantNo = merchantNo;
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
	@Override
	public String toString() {
		return "CMPAY0046Rs [CommonRsHdr=" + CommonRsHdr + ", MerchantNo=" + MerchantNo + ", Amount=" + Amount
				+ ", UserId=" + UserId + "]";
	}

}
