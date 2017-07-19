package com.cmpay.service.htpay.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 代扣查询接口参数
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年7月11日 下午4:08:08
 *
 */
public class HtCutQueryParaReq {
   private String service;
   private String version;
   private String partner_id;
   private String _input_charset;
   private String sign;
   private String sign_type;
   private String memo;

   private String outer_trade_no;
   private String trade_type;
   private String start_time;
   private String end_time;
   private String inner_trade_no;


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


public String getOuter_trade_no() {
	return outer_trade_no;
}
public void setOuter_trade_no(String outer_trade_no) {
	this.outer_trade_no = outer_trade_no;
}
public String getTrade_type() {
	return trade_type;
}
public void setTrade_type(String trade_type) {
	this.trade_type = trade_type;
}
public String getStart_time() {
	return start_time;
}
public void setStart_time(String start_time) {
	this.start_time = start_time;
}
public String getEnd_time() {
	return end_time;
}
public void setEnd_time(String end_time) {
	this.end_time = end_time;
}
public String getInner_trade_no() {
	return inner_trade_no;
}
public void setInner_trade_no(String inner_trade_no) {
	this.inner_trade_no = inner_trade_no;
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
	map.put("outer_trade_no", outer_trade_no);
	map.put("trade_type", trade_type);
	map.put("start_time", start_time);
	map.put("end_time", end_time);
	map.put("inner_trade_no", inner_trade_no);


	return map;
}
@Override
public String toString() {
	return "HtCutQueryParaReq [service=" + service + ", version=" + version + ", partner_id=" + partner_id
			+ ", _input_charset=" + _input_charset + ", sign=" + sign + ", sign_type=" + sign_type + ", memo=" + memo
			+ ", outer_trade_no=" + outer_trade_no + ", trade_type=" + trade_type + ", start_time=" + start_time
			+ ", end_time=" + end_time + ", inner_trade_no=" + inner_trade_no + "]";
}


}
