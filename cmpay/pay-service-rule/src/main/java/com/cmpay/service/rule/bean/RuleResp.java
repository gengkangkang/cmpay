package com.cmpay.service.rule.bean;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月12日 下午1:27:59
 *
 */
public class RuleResp {

	private String code;
	private String msg;
	private String payWay;
	private String pcBankCode;
	private String pcBankName;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPcBankCode() {
		return pcBankCode;
	}
	public void setPcBankCode(String pcBankCode) {
		this.pcBankCode = pcBankCode;
	}
	@Override
	public String toString() {
		return "RuleResp [code=" + code + ", msg=" + msg + ", payWay=" + payWay + ", pcBankCode=" + pcBankCode
				+ ", pcBankName=" + pcBankName + "]";
	}
	public String getPcBankName() {
		return pcBankName;
	}
	public void setPcBankName(String pcBankName) {
		this.pcBankName = pcBankName;
	}

}
