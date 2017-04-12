package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 证件类型
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月28日 上午10:00:27
 *
 */
public enum IdTypeEnum {

	/**
	 * 身份证
	 */
	I,
	/**
	 * 临时身份证
	 */
	T,
	/**
	 * 士兵证
	 */
	S,
	/**
	 * 军官证
	 */
	B,
	/**
	 * 护照
	 */
	P,
	/**
	 * 营业执照
	 */
	L,
	/**
	 * 其他有效证件
	 */
	O,
	/**
	 * 户口簿
	 */
	R,
	/**
	 * 港澳居民来往内地通行证
	 */
	H,
	/**
	 * 台湾同胞来往内地通行证
	 */
	W,
	/**
	 * 外国人居留证
	 */
	F,
	/**
	 * 警官证
	 */
	C,

	/**
	 * 香港身份证
	 */
	V,
	/**
	 * 澳门身份证
	 */
	X,
	/**
	 * 台湾身份证
	 */
	Y,
	/**
	 * 其他证件
	 */
	Z;


    public static boolean contains(String type){
        for(IdTypeEnum idType : IdTypeEnum.values()){
            if(idType.name().equals(type)){
                return true;
            }
        }
        return false;
    }

	public static IdTypeEnum getByIdType(String idType){
		if(StringUtils.isBlank(idType)){
			return null;
		}
		idType=idType.trim();
		for(IdTypeEnum idTypeEnum:IdTypeEnum.values()){
			if(idTypeEnum.name().equalsIgnoreCase(idType)){
				return idTypeEnum;
			}
		}
		return null;
	}
}
