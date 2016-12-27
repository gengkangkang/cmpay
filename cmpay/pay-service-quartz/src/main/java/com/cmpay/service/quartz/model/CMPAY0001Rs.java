package com.cmpay.service.quartz.model;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月26日 下午5:23:20
 *
 */
public class CMPAY0001Rs {

	private CommonRsHdr CommonRsHdr;
	private String orderId;
	private String tranStatus;
	private String tranMsg;

	public CommonRsHdr getCommonRsHdr() {
		return CommonRsHdr;
	}
	public void setCommonRsHdr(CommonRsHdr commonRsHdr) {
		CommonRsHdr = commonRsHdr;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}
	public String getTranMsg() {
		return tranMsg;
	}
	public void setTranMsg(String tranMsg) {
		this.tranMsg = tranMsg;
	}
	@Override
	public String toString() {
		return "CMPAY0001Rs [CommonRsHdr=" + CommonRsHdr + ", orderId=" + orderId + ", tranStatus=" + tranStatus
				+ ", tranMsg=" + tranMsg + "]";
	}


}
