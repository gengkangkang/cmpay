package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum HtPayErrorCodeEnum {

	CODE_T("T","0000","交易成功","代扣"),
	CODE_SYSTEM_ERROR("SYSTEM_ERROR","1000","支持渠道内部错误","代扣"),
	CODE_SESSION_TIMEOUT("SESSION_TIMEOUT","1000","支持渠道内部错误","代扣"),
	CODE_ILLEGAL_ACCESS_SWITCH_SYSTEM("ILLEGAL_ACCESS_SWITCH_SYSTEM","1000","支持渠道内部错误","代扣"),
	CODE_EXTERFACE_INVOKE_CONTEXT_EXPIRED("EXTERFACE_INVOKE_CONTEXT_EXPIRED","1000","支持渠道内部错误","代扣"),
	CODE_PARTNER_ID_NOT_EXIST("PARTNER_ID_NOT_EXIST","1045","商户配置错误","代扣"),
	CODE_ILLEGAL_SERVICE("ILLEGAL_SERVICE","1045","商户配置错误","代扣"),
	CODE_IDENTITY_EXIST_ERROR("IDENTITY_EXIST_ERROR","1045","商户配置错误","代扣"),
	CODE_MEMBER_NOT_EXIST("MEMBER_NOT_EXIST","1045","商户配置错误","代扣"),
	CODE_ACCOUNT_NOT_EXIST("ACCOUNT_NOT_EXIST","1045","商户配置错误","代扣"),
	CODE_MOBILE_EXIST_ERROR("MOBILE_EXIST_ERROR","1045","商户配置错误","代扣"),
	CODE_MEMBER_TYPE_ERROR("MEMBER_TYPE_ERROR","1045","商户配置错误","代扣"),
	CODE_ACCOUNT_MEMBER_TYPE_NOT_MATCH("ACCOUNT_MEMBER_TYPE_NOT_MATCH","1045","商户配置错误","代扣"),
	CODE_ACCOUNT_TYPE_NOT_EXIST("ACCOUNT_TYPE_NOT_EXIST","1045","商户配置错误","代扣"),
	CODE_ACCOUNT_INFO_ERROR("ACCOUNT_INFO_ERROR","1045","商户配置错误","代扣"),
	CODE_TRADE_DATA_MATCH_ERROR("TRADE_DATA_MATCH_ERROR","1046","交易报文错误","代扣"),
	CODE_ILLEGAL_SIGN_TYPE("ILLEGAL_SIGN_TYPE","1046","交易报文错误","代扣"),
	CODE_ILLEGAL_SIGN("ILLEGAL_SIGN","1046","交易报文错误","代扣"),
	CODE_ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT","1046","交易报文错误","代扣"),
	CODE_REQUIRED_FIELD_NOT_EXIST("REQUIRED_FIELD_NOT_EXIST","1046","交易报文错误","代扣"),
	CODE_FIELD_LENGTH_EXCEEDS_LIMIT("FIELD_LENGTH_EXCEEDS_LIMIT","1046","交易报文错误","代扣"),
	CODE_FIELD_TYPE_ERROR("FIELD_TYPE_ERROR","1046","交易报文错误","代扣"),
	CODE_TRADE_AMOUNT_MATCH_ERROR("TRADE_AMOUNT_MATCH_ERROR","1046","交易报文错误","代扣"),
	CODE_PREPAY_DATA_MATCH_ERROR("PREPAY_DATA_MATCH_ERROR","1046","交易报文错误","代扣"),
	CODE_TRADE_PAY_MATCH_ERROR("TRADE_PAY_MATCH_ERROR","1046","交易报文错误","代扣"),
	CODE_TRADE_NO_MATCH_ERROR("TRADE_NO_MATCH_ERROR","1046","交易报文错误","代扣"),
	CODE_TOTAL_FEE_LESSEQUAL_ZERO("TOTAL_FEE_LESSEQUAL_ZERO","1046","交易报文错误","代扣"),
	CODE_ILLEGAL_SUBSCRIPTION_ORDER_NO("ILLEGAL_SUBSCRIPTION_ORDER_NO","1046","交易报文错误","代扣"),
	CODE_ILLEGAL_SUBSCRIPTION("ILLEGAL_SUBSCRIPTION","1046","交易报文错误","代扣"),
	CODE_ILLEGAL_REFUND_AMOUNT("ILLEGAL_REFUND_AMOUNT","1046","交易报文错误","代扣"),
	CODE_ILLEGAL_REQUEST("ILLEGAL_REQUEST","1047","风控未通过","代扣"),
	CODE_PAY_METHOD_ERROR("PAY_METHOD_ERROR","1048","支付方式错误","代扣"),
	CODE_ILLEGAL_PAY_METHOD("ILLEGAL_PAY_METHOD","1048","支付方式错误","代扣"),
	CODE_DUPLICATE_REQUEST_NO("DUPLICATE_REQUEST_NO","1049","重复的请求号","代扣"),
	CODE_ILLEGAL_OUTER_TRADE_NO("ILLEGAL_OUTER_TRADE_NO","1050","交易订单号不存在","代扣"),
	CODE_ILLEGAL_DATE_FORMAT("ILLEGAL_DATE_FORMAT","1051","日期格式错误","代扣"),
	CODE_ILLEGAL_AMOUNT_FORMAT("ILLEGAL_AMOUNT_FORMAT","1052","金额格式错误","代扣"),

	CODE_9999("","C9999","未知错误，请联系支付机构","所有");


	private String respCode;
	private String coreRespCode;
	private String coreRespMsg;
	private String orderType;

	private HtPayErrorCodeEnum(String respCode,String coreRespCode,String coreRespMsg,String orderType){
		this.respCode=respCode;
		this.coreRespCode=coreRespCode;
		this.coreRespMsg=coreRespMsg;
	}

	public static HtPayErrorCodeEnum getByRespCode(String respCode){
		if(StringUtils.isBlank(respCode)){
			return HtPayErrorCodeEnum.CODE_9999;
		}
		respCode=respCode.trim();
		for(HtPayErrorCodeEnum cpErrorCodeEnum:HtPayErrorCodeEnum.values()){
			if(cpErrorCodeEnum.getRespCode().equalsIgnoreCase(respCode)){
				return cpErrorCodeEnum;
			}
		}
		return HtPayErrorCodeEnum.CODE_9999;
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
