package com.cmpay.service.htpay.model;

import java.util.HashMap;
import java.util.Map;

/**
 *  航天支付参数组装类
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年6月29日 下午2:18:23
 *
 */
public class HtCutParaReq {
   private String service;
   private String version;
   private String partner_id;
   private String _input_charset;
   private String sign;
   private String sign_type;
   private String memo;

   private String request_no;
   private String amount;
   private String bankcard;
   private String bankname;
   private String accname;
   private String certno;
   private String certtype;
   private String mobile;
   private String remark;
   private String cardtype;
   private String cardattribute;

public String getService() {
	return service;
}
public void setService(String service) {
	this.service = service;
}
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}
public String getPartner_id() {
	return partner_id;
}
public void setPartner_id(String partner_id) {
	this.partner_id = partner_id;
}
public String get_input_charset() {
	return _input_charset;
}
public void set_input_charset(String _input_charset) {
	this._input_charset = _input_charset;
}
public String getSign() {
	return sign;
}
public void setSign(String sign) {
	this.sign = sign;
}
public String getSign_type() {
	return sign_type;
}
public void setSign_type(String sign_type) {
	this.sign_type = sign_type;
}
public String getMemo() {
	return memo;
}
public void setMemo(String memo) {
	this.memo = memo;
}
public String getRequest_no() {
	return request_no;
}
public void setRequest_no(String request_no) {
	this.request_no = request_no;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getBankcard() {
	return bankcard;
}
public void setBankcard(String bankcard) {
	this.bankcard = bankcard;
}
public String getBankname() {
	return bankname;
}
public void setBankname(String bankname) {
	this.bankname = bankname;
}
public String getAccname() {
	return accname;
}
public void setAccname(String accname) {
	this.accname = accname;
}
public String getCertno() {
	return certno;
}
public void setCertno(String certno) {
	this.certno = certno;
}
public String getCerttype() {
	return certtype;
}
public void setCerttype(String certtype) {
	this.certtype = certtype;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getCardtype() {
	return cardtype;
}
public void setCardtype(String cardtype) {
	this.cardtype = cardtype;
}
public String getCardattribute() {
	return cardattribute;
}
public void setCardattribute(String cardattribute) {
	this.cardattribute = cardattribute;
}
@Override
public String toString() {
	return "HtCutParaReq [service=" + service + ", version=" + version + ", partner_id=" + partner_id
			+ ", _input_charset=" + _input_charset + ", sign=" + sign + ", sign_type=" + sign_type + ", memo=" + memo
			+ ", request_no=" + request_no + ", amount=" + amount + ", bankcard=" + bankcard + ", bankname=" + bankname
			+ ", accname=" + accname + ", certno=" + certno + ", certtype=" + certtype + ", mobile=" + mobile
			+ ", remark=" + remark + ", cardtype=" + cardtype + ", cardattribute=" + cardattribute + "]";
}

public Map<String,String> convertToMap(){
	Map<String,String> map=new HashMap<String,String>();
	map.put("service", service);
	map.put("version", version);
	map.put("partner_id", partner_id);
	map.put("_input_charset", _input_charset);
	map.put("sign", sign);
	map.put("sign_type", sign_type);
	map.put("memo", memo);
	map.put("request_no", request_no);
	map.put("amount", amount);
	map.put("bankcard", bankcard);
	map.put("bankname", bankname);
	map.put("accname", accname);
	map.put("certno", certno);
	map.put("certtype", certtype);
	map.put("mobile", mobile);
	map.put("remark", remark);
	map.put("cardtype", cardtype);
	map.put("cardattribute", cardattribute);


	return map;
}


}
