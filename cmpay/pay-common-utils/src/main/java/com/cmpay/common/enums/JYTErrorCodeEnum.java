package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum JYTErrorCodeEnum {

	CODE_S0000000("S0000000","0000","受理成功","代扣"),
	CODE_E0000000("E0000000","1005","银行处理中","代扣"),
	CODE_E0000009("E0000009","1000","支持渠道内部错误","代扣"),
	CODE_E0000010("E0000010","1000","支持渠道内部错误","代扣"),
	CODE_E0000011("E0000011","1000","支持渠道内部错误","代扣"),
	CODE_E0000012("E0000012","1000","支持渠道内部错误","代扣"),
	CODE_E0000001("E0000001","1000","支持渠道内部错误","代扣"),
	CODE_E0000004("E0000004","1000","支持渠道内部错误","代扣"),
	CODE_E0000021("E0000021","1000","支持渠道内部错误","代扣"),
	CODE_E0000039("E0000039","1000","支持渠道内部错误","代扣"),
	CODE_ER000009("E0000009","1000","支持渠道内部错误","代扣"),
	CODE_ER000010("E0000010","1000","支持渠道内部错误","代扣"),
	CODE_ER000011("ER000011","1007","银行卡无效","代扣"),
	CODE_ER000012("ER000012","1007","银行卡无效","代扣"),
	CODE_ER000013("ER000013","1015","账户和姓名不匹配","代扣"),
	CODE_ER000014("ER000014","1015","账户和姓名不匹配","代扣"),
	CODE_ER000015("ER000015","1000","支持渠道内部错误","代扣"),
	CODE_ER000016("ER000016","1000","支持渠道内部错误","代扣"),
	CODE_ER000017("ER000017","1000","支持渠道内部错误","代扣"),
	CODE_ER000018("ER000018","1000","支持渠道内部错误","代扣"),
	CODE_ER000019("ER000019","1000","支持渠道内部错误","代扣"),
	CODE_ER000020("ER000020","1000","支持渠道内部错误","代扣"),
	CODE_ER000021("ER000021","1000","支持渠道内部错误","代扣"),
	CODE_ER000022("ER000022","1000","支持渠道内部错误","代扣"),
	CODE_ER000023("ER000023","1007","银行卡无效","代扣"),
	CODE_ER000024("ER000024","1006","请联系开户银行","代扣"),
	CODE_ER000025("ER000025","1015","账户和姓名不匹配","代扣"),
	CODE_ER001000("ER001000","1015","账户和姓名不匹配","代扣"),
	CODE_ER001001("ER001001","1007","银行卡无效","代扣"),
	CODE_ER001002("ER001002","1007","银行卡无效","代扣"),
	CODE_ER001003("ER001003","1014","银行卡号与账户不匹配","代扣"),
	CODE_ER001004("ER001004","1006","请联系开户银行","代扣"),
	CODE_ER001005("ER001005","1007","银行卡无效","代扣"),
	CODE_ER009901("ER009901","1000","支持渠道内部错误","代扣"),
	CODE_ER009902("ER009902","1000","支持渠道内部错误","代扣"),
	CODE_ER009903("ER009903","1000","支持渠道内部错误","代扣"),
	CODE_ER009904("ER009904","1000","支持渠道内部错误","代扣"),
	CODE_ER009921("ER009921","1000","支持渠道内部错误","代扣"),
	CODE_ER009922("ER009922","1014","银行卡号与账户不匹配","代扣"),
	CODE_ER009923("ER009923","1014","银行卡号与账户不匹配","代扣"),
	CODE_ER009924("ER009924","1006","请联系开户银行","代扣"),
	CODE_ER009925("ER009925","1013","银行卡号与手机不匹配","代扣"),
	CODE_ER009930("ER009930","1004","客户余额不足","代扣"),
	CODE_E7000003("E7000003","3003","交易金额超出单日限额，拒绝交易","代扣"),
	CODE_9999("","C9999","未知错误，请联系支付机构","所有");

	private String respCode;
	private String coreRespCode;
	private String coreRespMsg;
	private String orderType;

	private JYTErrorCodeEnum(String respCode,String coreRespCode,String coreRespMsg,String orderType){
		this.respCode=respCode;
		this.coreRespCode=coreRespCode;
		this.coreRespMsg=coreRespMsg;
	}

	public static JYTErrorCodeEnum getByRespCode(String respCode){
		if(StringUtils.isBlank(respCode)){
			return JYTErrorCodeEnum.CODE_9999;
		}
		respCode=respCode.trim();
		for(JYTErrorCodeEnum cpErrorCodeEnum:JYTErrorCodeEnum.values()){
			if(cpErrorCodeEnum.getRespCode().equalsIgnoreCase(respCode)){
				return cpErrorCodeEnum;
			}
		}
		return JYTErrorCodeEnum.CODE_9999;
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
