package com.cmpay.facade.entity;

import java.io.Serializable;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月5日 下午5:29:11
 *
 */
public class PayRefundRq implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String merId;
	private String inchannel;
	private String userId;
	private String refundAmt;
	private String origiOrderId;
	private String orderIp;
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
	public String getOrigiOrderId() {
		return origiOrderId;
	}
	public void setOrigiOrderId(String origiOrderId) {
		this.origiOrderId = origiOrderId;
	}
	public String getOrderIp() {
		return orderIp;
	}
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "PayRefundRq [merId=" + merId + ", inchannel=" + inchannel + ", userId=" + userId + ", refundAmt="
				+ refundAmt + ", origiOrderId=" + origiOrderId + ", orderIp=" + orderIp + ", sign=" + sign + "]";
	}



}
