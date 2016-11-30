package com.cmpay.common.enums;

public enum SignCardTypeEnum {
	/** 借记卡 */ DEBIT,
	/** 贷记卡 */ CREDIT,
	/** 预付卡 */ PREPAID;

    public static boolean contains(String type){
        for(SignCardTypeEnum signCardTypeDef : SignCardTypeEnum.values()){
            if(signCardTypeDef.name().equals(type)){
                return true;
            }
        }
        return false;
    }
}
