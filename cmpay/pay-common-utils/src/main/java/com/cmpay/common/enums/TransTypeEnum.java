package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 上午10:53:42
 *
 */
public enum TransTypeEnum {

	RECHARGE("01"),
	WITHDRAW("02"),
	CONSUME("03"),
	CANCEL("04"),
	NONE("");


   private TransTypeEnum(String value){
	   this.value=value;
   }

	public static TransTypeEnum getByTransCode(String transCode){
		if(StringUtils.isBlank(transCode)){
			return null;
		}
		transCode=transCode.trim();
		for(TransTypeEnum transTypeEnum:TransTypeEnum.values()){
			if(transTypeEnum.getValue().equalsIgnoreCase(transCode)){
				return transTypeEnum;
			}
		}
		return null;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
