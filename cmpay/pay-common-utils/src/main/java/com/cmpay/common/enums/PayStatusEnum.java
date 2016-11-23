package com.cmpay.common.enums;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 下午1:47:35
 *
 */
public enum PayStatusEnum {

	WAIT("未支付"),
	SUCC("成功"),
	DEALING("处理中"),
	FAIL("失败");

   private PayStatusEnum(String value){
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
