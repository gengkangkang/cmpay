package com.cmpay.service.quartz.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 入账报文类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月26日 下午4:15:00
 *
 */
@XStreamAlias("CMPAY0001Rq")
public class CMPAY0001 {

	private CommonRqHdr CommonRqHdr;
	private String merId;
	private String userId;
	private String amount;
	private String orderId;
	public CommonRqHdr getCommonRqHdr() {
		return CommonRqHdr;
	}
	public void setCommonRqHdr(CommonRqHdr commonRqHdr) {
		CommonRqHdr = commonRqHdr;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


}
