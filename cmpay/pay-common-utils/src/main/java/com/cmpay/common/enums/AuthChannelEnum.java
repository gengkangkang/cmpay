package com.cmpay.common.enums;
/**
 * 卡认证渠道
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月28日 下午2:11:13
 *
 */
public enum AuthChannelEnum {

	CMPAY0001("银联"),
	CMPAY0002("金运通");

   private AuthChannelEnum(String value){
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
        for(AuthChannelEnum authChannelEnum : AuthChannelEnum.values()){
            if(authChannelEnum.name().equals(type)){
                return true;
            }
        }
        return false;
    }

}
