package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 上午10:40:11
 *
 */
public enum PayWayEnum {
	CMPAY0001("银联"),
	CMPAY0002("金运通"),
	CMPAY0003("连连支付"),
	CMPAY0004("微信支付"),
	CMPAY0005("航天支付");

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

    public static boolean contains(String type){
        for(PayWayEnum payWayEnum : PayWayEnum.values()){
            if(payWayEnum.name().equals(type)){
                return true;
            }
        }
        return false;
    }

	public static PayWayEnum getByCode(String code){
		if(StringUtils.isBlank(code)){
			return null;
		}
		code=code.trim();
		for(PayWayEnum payWayEnum:PayWayEnum.values()){
			if(payWayEnum.name().equalsIgnoreCase(code)){
				return payWayEnum;
			}
		}
		return null;
	}


}
