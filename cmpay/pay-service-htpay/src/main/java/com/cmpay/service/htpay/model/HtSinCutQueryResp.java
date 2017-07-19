package com.cmpay.service.htpay.model;

import java.io.Serializable;

/**
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年7月6日 下午4:51:21
 *
 */
public class HtSinCutQueryResp implements Serializable {


	private static final long serialVersionUID = 1L;
	private String transStat;
	private String partnerId;
	private String inputCharset;
	private String respCode;
	private String respMsg;
	private String cutOrderNo;
	private String thirdPartyOrderNo;
	private String memo;

	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getInputCharset() {
		return inputCharset;
	}
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
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
	public String getCutOrderNo() {
		return cutOrderNo;
	}
	public void setCutOrderNo(String cutOrderNo) {
		this.cutOrderNo = cutOrderNo;
	}
	public String getThirdPartyOrderNo() {
		return thirdPartyOrderNo;
	}
	public void setThirdPartyOrderNo(String thirdPartyOrderNo) {
		this.thirdPartyOrderNo = thirdPartyOrderNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "HtSinCutResp [transStat=" + transStat + ", partnerId=" + partnerId + ", inputCharset=" + inputCharset
				+ ", respCode=" + respCode + ", respMsg=" + respMsg + ", cutOrderNo=" + cutOrderNo
				+ ", thirdPartyOrderNo=" + thirdPartyOrderNo + ", memo=" + memo + "]";
	}

}
