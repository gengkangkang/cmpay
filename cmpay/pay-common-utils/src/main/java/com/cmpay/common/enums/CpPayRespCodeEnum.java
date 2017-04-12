package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum CpPayRespCodeEnum {

	RESP_0000("0000","提交成功!"),
	RESP_NULL("9999","交易失败!"),
	RESP_0100("0100","商户提交的字段长度、格式错误!"),
	RESP_0101("0101","商户验签错误!"),
	RESP_0102("0102","手续费计算出错!"),
	RESP_0103("0103","商户备付金帐户金额不足!"),
	RESP_0104("0104","操作拒绝!"),
	RESP_0105("0105","重复交易!"),

	RESP_S("s","交易成功!"),
	RESP_2("2","处理中,交易已接受!"),
	RESP_3("3","处理中,财务已确认!"),
	RESP_4("4","处理中,财务处理中!"),
	RESP_5("5","处理中,已发往银行!"),
	RESP_6("6","失败,银行已退单!"),
	RESP_7("7","处理中,重汇已提交!"),
	RESP_8("8","处理中,重汇已发送!"),
	RESP_9("9","失败,重汇已退单!");

	private String respCode;
	private String coreRespMsg;

	private CpPayRespCodeEnum(String respCode,String coreRespMsg){
		this.respCode=respCode;
		this.coreRespMsg=coreRespMsg;
	}

	public static CpPayRespCodeEnum getByRespCode(String respCode){
		if(StringUtils.isBlank(respCode)){
			return CpPayRespCodeEnum.RESP_NULL;
		}
		for(CpPayRespCodeEnum cpPayErrorCodeEnum:CpPayRespCodeEnum.values()){
			if(cpPayErrorCodeEnum.getRespCode().equalsIgnoreCase(respCode)){
				return cpPayErrorCodeEnum;
			}
		}
		return CpPayRespCodeEnum.RESP_NULL;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getCoreRespMsg() {
		return coreRespMsg;
	}

	public void setCoreRespMsg(String coreRespMsg) {
		this.coreRespMsg = coreRespMsg;
	}

}
