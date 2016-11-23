package com.cmpay.weixin.vo;

/**
 * @author gengkangkang
 * @param <T>
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月21日 下午3:46:05
 *
 */
public class QueryResult {

	private String code;
	private String msg;
	private String return_url;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	@Override
	public String toString() {
		return "QueryResult [code=" + code + ", msg=" + msg + ", return_url=" + return_url + "]";
	}

}
