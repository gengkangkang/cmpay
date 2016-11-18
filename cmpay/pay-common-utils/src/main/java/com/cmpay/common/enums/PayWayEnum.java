package com.cmpay.common.enums;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 上午10:40:11
 *
 */
public enum PayWayEnum {
	CMPAY0004("微信支付"),
	KJ("快捷支付"),
	ZF("支付宝");

   private PayWayEnum(String value){
	   this.value=value;
   }

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
