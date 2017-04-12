package com.cmpay.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum CpErrorCodeEnum {

	CODE_1001("00","0000","交易成功","代扣"),
	CODE_2000("2000","1005","银行处理中","代扣"),
	CODE_2045("45","1005","银行处理中","代扣"),
	CODE_2009("09","1005","银行处理中","代扣"),
	CODE_20E2("E2","1000","支持渠道内部错误","代扣"),
	CODE_2001("01","1006","请联系开户银行","代扣"),
	CODE_2003("03","1000","支持渠道内部错误","代扣"),
	CODE_2005("05","1016","请开通相关业务","代扣"),
	CODE_2006("06","1000","支持渠道内部错误","代扣"),
	CODE_2013("13","1000","支持渠道内部错误","代扣"),
	CODE_2014("14","1007","银行卡无效","代扣"),
	CODE_2022("22","1000","支持渠道内部错误","代扣"),
	CODE_2030("30","1000","支持渠道内部错误","代扣"),
	CODE_2031("31","1006","请联系开户银行","代扣"),
	CODE_2041("41","1008","银行卡已挂失","代扣"),
	CODE_2051("51","1004","客户余额不足","代扣"),
	CODE_2061("61","1002","金额超限","代扣"),
	CODE_2094("94","1000","支持渠道内部错误","代扣"),
	CODE_20EC("EC","1000","支持渠道内部错误","代扣"),
	CODE_20F3("F3","1000","支持渠道内部错误","代扣"),
	CODE_20FF("FF","1012","非白名单卡号","代扣"),
	CODE_20P9("P9","1009","银行卡已冻结","代扣"),
	CODE_20PD("PD","1016","请开通相关业务","代扣"),
	CODE_20PS("PS","1014","银行卡号与账户不匹配","代扣"),
	CODE_20PU("PU","1000","支持渠道内部错误","代扣"),
	CODE_20PZ("PZ","1000","支持渠道内部错误","代扣"),
	CODE_20Q3("Q3","1000","支持渠道内部错误","代扣"),
	CODE_20QB("QB","1000","支持渠道内部错误","代扣"),
	CODE_20QS("QS","1011","支持渠道系统繁忙","代扣"),
	CODE_20ST("ST","1000","支持渠道内部错误","代扣"),
	CODE_20T4("T4","1010","银行账户未签约","代扣"),
	CODE_20TY("TY","1000","支持渠道内部错误","代扣"),
	CODE_20EL("EL","1000","支持渠道内部错误","代扣"),
	CODE_1("1","1000","支持渠道内部错误","代扣"),


	CODE_S("s","0000","交易成功","代付"),
	CODE_2("2","1005","银行处理中","代付"),
	CODE_3("3","1005","银行处理中","代付"),
	CODE_4("4","1005","银行处理中","代付"),
	CODE_5("5","1005","银行处理中","代付"),
	CODE_6("6","2001","银行已退单","代付"),
	CODE_7("7","1005","银行处理中","代付"),
	CODE_8("8","1005","银行处理中","代付"),
	CODE_9("9","2002","重汇已退单","代付"),
	CODE_0100("0100","2003","支持渠道内部错误","代付"),
	CODE_0101("0101","2003","支持渠道内部错误","代付"),
	CODE_0102("0102","2003","支持渠道内部错误","代付"),
	CODE_0103("0103","2003","支持渠道内部错误","代付"),
	CODE_0104("0104","2003","支持渠道内部错误","代付"),
	CODE_0105("0105","2004","重复交易","代付"),

	/////////////////////////////卡实名认证/////////////////////////////////////////
	CODE_0000("0000","0000","认证成功","认证"),
	CODE_0001("0001","9200","认证失败，请重新认证","认证"),
	CODE_1111("1111","9900","认证失败,请联系发卡行","认证"),
	CODE_2222("2222","9200","认证失败，请重新认证","认证"),
	CODE_9999R("9999","9900","认证失败,请联系发卡行","认证"),
	CODE_9900("9900","9900","认证失败,请联系发卡行","认证"),
	CODE_9901("9901","9901","无效的发卡行","认证"),
	CODE_9902("9902","9902","无效交易","认证"),
	CODE_9903("9903","9903","无效金额","认证"),
	CODE_9904("9904","9904","无效卡号","认证"),
	CODE_9905("9905","9905","客户取消交易","认证"),
	CODE_9906("9906","9906","无效交易响应","认证"),
	CODE_9907("9907","9907","此卡已过期","认证"),
	CODE_9908("9908","9908","密码错误","认证"),
	CODE_9909("9909","9909","余额不足","认证"),
	CODE_9910("9910","9910","未开通此功能","认证"),
	CODE_9911("9911","9911","交易异常,请联系发卡行","认证"),
	CODE_9912("9912","9912","超出金额限制","认证"),
	CODE_9913("9913","9913","此卡受限制,请联系发卡行","认证"),
	CODE_9914("9914","9914","超出取款次数限制","认证"),
	CODE_9915("9915","9915","超出最大输入密码次数,请联系发卡行","认证"),
	CODE_9916("9916","9916","交易超时,请重试","认证"),
	CODE_9917("9917","9917","交易重复,请稍后查询结果","认证"),
	CODE_9918("9918","9918","密码格式错误","认证"),
	CODE_9919("9919","9919","银行卡与姓名不符","认证"),
	CODE_9920("9920","9920","银行卡与证件不符","认证"),
	CODE_9800("","9800","请求参数有误（会有具体提示）","认证"),
	CODE_9801("","9801","不支持的银行卡，请检查卡号是否正确","认证"),
	CODE_9802("","9802","核心不支持的银行","认证"),
	CODE_9803("","9803","该银行渠道已关闭，请确认支持的绑卡列表","认证"),
	CODE_9804("","9804","只支持借记卡绑卡","认证"),
	CODE_9805("","9805","验卡失败，渠道无返回","认证"),
	CODE_9806("","9806","账号custId已被其他用户绑定","认证"),
	CODE_9807("","9807","证件号已经存在","认证"),
	CODE_9808("","9808","证件号与上次绑定不一致","认证"),
	CODE_9809("","9809","客户信息为空","认证"),
	CODE_9810("","9810","卡片验证失败","认证"),
	//金運通
	CODE_9811("9811","9811","卡片校验失败，请确认卡片信息是否正确","金运通认证"),




	/////////////////////////////卡实名认证/////////////////////////////////////////


	CODE_9999("","C9999","未知错误，请联系支付机构","所有");

	private String respCode;
	private String coreRespCode;
	private String coreRespMsg;
	private String orderType;

	private CpErrorCodeEnum(String respCode,String coreRespCode,String coreRespMsg,String orderType){
		this.respCode=respCode;
		this.coreRespCode=coreRespCode;
		this.coreRespMsg=coreRespMsg;
	}

	public static CpErrorCodeEnum getByRespCode(String respCode){
		if(StringUtils.isBlank(respCode)){
			return CpErrorCodeEnum.CODE_9999;
		}
		respCode=respCode.trim();
		for(CpErrorCodeEnum cpErrorCodeEnum:CpErrorCodeEnum.values()){
			if(cpErrorCodeEnum.getRespCode().equalsIgnoreCase(respCode)){
				return cpErrorCodeEnum;
			}
		}
		return CpErrorCodeEnum.CODE_9999;
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

	public static void main(String[] args){
		System.out.println(CpErrorCodeEnum.CODE_0000.getCoreRespCode());
	}

}
