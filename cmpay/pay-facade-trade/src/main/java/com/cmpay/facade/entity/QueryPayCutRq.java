package com.cmpay.facade.entity;

import java.io.Serializable;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月30日 上午10:51:39
 *
 */
public class QueryPayCutRq implements Serializable {

	private static final long serialVersionUID = 1L;
	private String merId;
	private String origOrderNo;

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getOrigOrderNo() {
		return origOrderNo;
	}

	public void setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo;
	}

	@Override
	public String toString() {
		return "QueryPayCutRq [merId=" + merId + ", origOrderNo=" + origOrderNo + "]";
	}
}
