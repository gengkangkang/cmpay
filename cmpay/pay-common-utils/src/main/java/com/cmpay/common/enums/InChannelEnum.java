package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;


/**
 * 接入渠道枚举类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月29日 下午3:58:02
 *
 */
public enum InChannelEnum {

	C0000("0000","中民i投"),
	C0001("0001","核心柜面"),
	C0002("0002","O2O"),
	C0003("0003","云石"),
	C0004("0004","基金"),
	C0005("0005","保险客服"),
	C0006("0006","保险电销"),
	C0007("0007","大数据"),
	C0008("0008","OA"),
	C0009("0009","昱胜"),
	C0010("0010","三方支付"),
	C0012("0012","UMP"),
	C0013("0013","CMPAY"),
	C0014("0014","小贷"),
	C0015("0015","中民居家网"),
	C9999("9999","一账通"),

	CC9999("C9999","未知渠道");


   private InChannelEnum(String code,String value){
	   this.code=code;
	   this.value=value;
   }
    private String code;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

  public static boolean contains(String code){
	  for(InChannelEnum inChannelEnum:InChannelEnum.values()){
              if(StringUtils.equals(code, inChannelEnum.getCode())){
            	  return true;
              }
	  }
	  return false;
  }

	public static String getInchannelByCode(String code){
		if(StringUtils.isBlank(code)){
			return InChannelEnum.CC9999.name();
		}
		code=code.trim();
		for(InChannelEnum inchannelEnum:InChannelEnum.values()){
			if(inchannelEnum.getCode().equalsIgnoreCase(code)){
				return inchannelEnum.getValue();
			}
		}
		return code;
	}

  public static void main(String[] args){
	  System.out.println(contains("0005"));
  }
}
