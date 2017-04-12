package com.cmpay.facade.entity;

import java.io.Serializable;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月5日 下午5:29:11
 *
 */
public class PayRefundRs implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String respCode;
	private String respMsg;
	private String origiOrderId;
	private String userId;
	private String refundAmt;
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
	public String getOrigiOrderId() {
		return origiOrderId;
	}
	public void setOrigiOrderId(String origiOrderId) {
		this.origiOrderId = origiOrderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "PayRefundRs [respCode=" + respCode + ", respMsg=" + respMsg + ", origiOrderId=" + origiOrderId
				+ ", userId=" + userId + ", refundAmt=" + refundAmt + ", sign=" + sign + "]";
	}




}
