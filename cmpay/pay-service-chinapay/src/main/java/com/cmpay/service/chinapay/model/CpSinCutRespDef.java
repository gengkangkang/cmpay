package com.cmpay.service.chinapay.model;

import java.io.Serializable;

public class CpSinCutRespDef implements Serializable {

	private static final long serialVersionUID = 7974276331668988638L;

	private String responseCode; // 应答信息
	private String transStat; // 代扣状态
	private String message; // 描述
	private String cutOrderNo;
	private String thirdPartyOrderNo;//第三方支付订单号

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getTransStat() {
		return transStat;
	}

	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	@Override
	public String toString() {
		return "CpSinCutRespDef [responseCode=" + responseCode + ", transStat=" + transStat + ", message=" + message
				+ ", cutOrderNo=" + cutOrderNo + ", thirdPartyOrderNo=" + thirdPartyOrderNo + "]";
	}



}
