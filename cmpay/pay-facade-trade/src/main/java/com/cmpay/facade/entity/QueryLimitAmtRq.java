package com.cmpay.facade.entity;

import java.io.Serializable;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年5月2日 下午1:35:24
 *
 */
public class QueryLimitAmtRq implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merId;
	private String inchannel;
	private String cardNo;
	private String bankCode;
	private String sign;

	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getInchannel() {
		return inchannel;
	}
	public void setInchannel(String inchannel) {
		this.inchannel = inchannel;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Override
	public String toString() {
		return "QueryLimitAmtRq [merId=" + merId + ", inchannel=" + inchannel + ", cardNo=" + cardNo + ", bankCode="
				+ bankCode + "]";
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}


}
