package com.cmpay.weixin.vo;

import java.math.BigDecimal;

/**
 *  扫码支付结果展示实体类
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月15日 下午5:02:02
 *
 */
public class ScanPayResultVo {


    private String payWayCode;
    private BigDecimal orderAmount;
    private String productName;
    private String codeUrl;

	public String getPayWayCode() {
		return payWayCode;
	}
	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
}
