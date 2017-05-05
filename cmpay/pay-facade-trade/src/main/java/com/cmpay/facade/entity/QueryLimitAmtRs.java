package com.cmpay.facade.entity;

import java.io.Serializable;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年5月2日 下午1:42:16
 *
 */
public class QueryLimitAmtRs implements Serializable {

	private static final long serialVersionUID = 1L;

	private String respCode;
	private String respMsg;
	private String cardNo;
	private String bankCode;
	private String payCode;
	private String singleAmt;
	private String dayAmt;
	private String monthAmt;
	private String availableAmt;
	private String sign;

	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
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
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getSingleAmt() {
		return singleAmt;
	}
	public void setSingleAmt(String singleAmt) {
		this.singleAmt = singleAmt;
	}
	public String getDayAmt() {
		return dayAmt;
	}
	public void setDayAmt(String dayAmt) {
		this.dayAmt = dayAmt;
	}
	public String getMonthAmt() {
		return monthAmt;
	}
	public void setMonthAmt(String monthAmt) {
		this.monthAmt = monthAmt;
	}

	public String getAvailableAmt() {
		return availableAmt;
	}
	public void setAvailableAmt(String availableAmt) {
		this.availableAmt = availableAmt;
	}
	@Override
	public String toString() {
		return "QueryLimitAmtRs [respCode=" + respCode + ", respMsg=" + respMsg + ", cardNo=" + cardNo + ", bankCode="
				+ bankCode + ", payCode=" + payCode + ", singleAmt=" + singleAmt + ", dayAmt=" + dayAmt + ", monthAmt="
				+ monthAmt + ", availableAmt=" + availableAmt + "]";
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}


}
