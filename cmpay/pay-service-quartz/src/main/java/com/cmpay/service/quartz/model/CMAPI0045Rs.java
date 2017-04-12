package com.cmpay.service.quartz.model;
/**
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月11日 下午4:23:30
 *
 */
public class CMAPI0045Rs {

	private CommonRsHdr CommonRsHdr;
	private String MerchantNo;
	private String Amount;

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

	@Override
	public String toString() {
		return "CMAPI0045Rs [CommonRsHdr=" + CommonRsHdr + ", MerchantNo=" + MerchantNo + ", Amount=" + Amount
				+"]";
	}

}
