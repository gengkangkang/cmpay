package com.cmpay.service.quartz.model;

import java.math.BigDecimal;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月11日 上午10:49:50
 *
 */
public class SettTotalBean {

	 private String merId;
	 private BigDecimal settToatlAmt;

	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public BigDecimal getSettToatlAmt() {
		return settToatlAmt;
	}
	public void setSettToatlAmt(BigDecimal settToatlAmt) {
		this.settToatlAmt = settToatlAmt;
	}

}
