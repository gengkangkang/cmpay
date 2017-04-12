package com.cmpay.facade.entity;

import java.io.Serializable;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月30日 上午10:51:52
 *
 */
public class QueryPayCutRs implements Serializable {

	private static final long serialVersionUID = 1L;

	private String respCode;

	private String merId;
	private String origOrderId;
	private String transAmt;
	private String transType;
	private String payChannel;
	private String payStatus;
	private String payOrderId;
	private String respMsg;
	private String sign;

	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getOrigOrderId() {
		return origOrderId;
	}
	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	@Override
	public String toString() {
		return "QueryPayCutRs [respCode=" + respCode + ", merId=" + merId + ", origOrderId=" + origOrderId
				+ ", transAmt=" + transAmt + ", transType=" + transType + ", payChannel=" + payChannel + ", payStatus="
				+ payStatus + ", payOrderId=" + payOrderId + ", respMsg=" + respMsg + ", sign=" + sign + "]";
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
