package com.cmpay.service.bank.model;

/**
 *  单笔代付响应实体类
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年3月24日 下午1:58:29
 *
 */
public class SinPayResp {

	private String respCode;
	private String respMsg;
	private String payCode;
	private String payMsg;
	private String payStatus;

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
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getPayMsg() {
		return payMsg;
	}
	public void setPayMsg(String payMsg) {
		this.payMsg = payMsg;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	@Override
	public String toString() {
		return "SinPayResp [respCode=" + respCode + ", respMsg=" + respMsg + ", payCode=" + payCode + ", payMsg="
				+ payMsg + ", payStatus=" + payStatus + "]";
	}

}
