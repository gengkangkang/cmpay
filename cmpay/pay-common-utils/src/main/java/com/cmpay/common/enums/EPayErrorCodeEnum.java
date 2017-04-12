package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum EPayErrorCodeEnum {

	CODE_010000("010000","0000","交易成功","代扣"),
	CODE_010001("010001","1006","请联系开户银行","代扣"),
	CODE_016002("016002","1007","银行卡无效","代扣"),
	CODE_016004("016004","1008","银行卡已挂失","代扣"),
	CODE_016003("016003","3014","银行账户不存在","代扣"),
	CODE_016008("016008","3022","银行卡已被锁定","代扣"),
	CODE_016005("016005","1005","银行处理中","代扣"),
	CODE_016007("016007","1017","银行卡异常","代扣"),
	CODE_016010("016010","3034","交易失败，风险受限","代扣"),
	CODE_016013("016013","1018","错误次数过多, 请稍后尝试","代扣"),
	CODE_016018("016018","1019","卡未经授权","代扣"),
	CODE_016020("016020","3018","银行卡未开通银联在线支付，请向银行或95516咨询","代扣"),
	CODE_016023("016023","1020","请到柜台重新签约短信银行","代扣"),
	CODE_016025("016025","9907","此卡已过期","代扣"),
	CODE_016072("016072","3010","银行卡号无效","代扣"),
	CODE_017001("017001","1021","CVV2信息不正确","代扣"),
	CODE_017004("017004","1007","银行卡无效","代扣"),
	CODE_017056("017056","1022","卡片有效期输入错误","代扣"),
	CODE_017058("017058","1023","账户未开通网上支付","代扣"),
	CODE_017059("017059","1006","请联系开户银行","代扣"),
	CODE_017062("017062","1024","操作过于频繁，请稍后再试","代扣"),
	CODE_017072("017072","1025","身份证已经被使用","代扣"),
	CODE_017071("017071","1013","银行卡号与手机不匹配","代扣"),
	CODE_017003("017003","1014","银行卡号与账户不匹配","代扣"),
	CODE_017002("017002","1015","账户和姓名不匹配","代扣"),
	CODE_017039("017039","1026","认证信息不匹配","代扣"),
	CODE_017037("017037","9920","银行卡与证件不符","代扣"),
	CODE_016011("016011","1016","请开通相关业务","代扣"),
	CODE_016022("016022","未在银行预留手机","未在银行预留手机","代扣"),
	CODE_017077("017077","1006","请联系开户银行","代扣"),
	CODE_017060("017060","1028","用户未满18岁","代扣"),
	CODE_017061("017061","9920","银行卡与证件不符","代扣"),
	CODE_016029("016029","1029","解约失败","代扣"),
	CODE_016999("016999","1030","您的账户开通快捷卡数目超过上限","代扣"),
	CODE_016998("016998","1031","该银行卡绑定的非激活账户数超过限制，无法开通","代扣"),
	CODE_016997("016997","1032","该身份信息已在多个帐号实名或开通快捷支付，请更换","代扣"),
	CODE_016996("016996","1033","当前账户状态不允许开通快捷支付","代扣"),
	CODE_014012("014012","2004","重复交易","代扣"),
	CODE_014020("014020","3012","银行账户余额不足","代扣"),
	CODE_015122("015122","3002","交易金额超出单笔限额，拒绝交易","代扣"),
	CODE_015123("015123","3003","交易金额超出单日限额，拒绝交易","代扣"),
	CODE_016006("016006","3016","银行交易超过限额","代扣"),
	CODE_016012("016012","1034","短信验证码错误","代扣"),
	CODE_016016("016016","3004","交易金额超出单月限额，拒绝交易","代扣"),
	CODE_016017("016017","1035","支付金额超过当年限制","代扣"),
	CODE_017050("017050","1036","信用额度超限","代扣"),
	CODE_017051("017051","1037","交易次数超过限制","代扣"),
	CODE_017052("017052","1038","手机验证码失效","代扣"),
	CODE_017053("017053","9911","交易异常,请联系发卡行","代扣"),
	CODE_017055("017055","1039","该业务不支持人民币","代扣"),
	CODE_017057("017057","9916","交易超时,请重试","代扣"),
	CODE_017100("017100","1040","代扣失败，请发起新的一笔代扣","代扣"),
	CODE_017101("017101","1041","对公代扣操作只能针对商户账号","代扣"),
	CODE_016014("016014","1006","请联系开户银行","代扣"),
	CODE_016015("016015","1042","payments系统异常","代扣"),
	CODE_016019("016019","1006","请联系开户银行","代扣"),
	CODE_016021("016021","1043","epay参数错误 ","代扣"),
	CODE_018014("018014","1006","请联系开户银行","代扣"),
	CODE_018015("018015","1044","验证码发送失败","代扣"),
	CODE_016009("016009","C9999","未知错误，请联系支付机构","代扣"),
	CODE_020000("020000","0000","业务成功","代扣"),
	CODE_024005("024005","0000","订单已经支付成功","代扣"),
	CODE_024011("024011","1045","订单已经被关闭","代扣"),
	CODE_024012("024012","1046","订单已经存在","代扣"),
	CODE_9999("","C9999","未知错误，请联系支付机构","所有");


	private String respCode;
	private String coreRespCode;
	private String coreRespMsg;
	private String orderType;

	private EPayErrorCodeEnum(String respCode,String coreRespCode,String coreRespMsg,String orderType){
		this.respCode=respCode;
		this.coreRespCode=coreRespCode;
		this.coreRespMsg=coreRespMsg;
	}

	public static EPayErrorCodeEnum getByRespCode(String respCode){
		if(StringUtils.isBlank(respCode)){
			return EPayErrorCodeEnum.CODE_9999;
		}
		respCode=respCode.trim();
		for(EPayErrorCodeEnum cpErrorCodeEnum:EPayErrorCodeEnum.values()){
			if(cpErrorCodeEnum.getRespCode().equalsIgnoreCase(respCode)){
				return cpErrorCodeEnum;
			}
		}
		return EPayErrorCodeEnum.CODE_9999;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getCoreRespCode() {
		return coreRespCode;
	}

	public void setCoreRespCode(String coreRespCode) {
		this.coreRespCode = coreRespCode;
	}

	public String getCoreRespMsg() {
		return coreRespMsg;
	}

	public void setCoreRespMsg(String coreRespMsg) {
		this.coreRespMsg = coreRespMsg;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
