package com.cmpay.service.htpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年7月4日 下午4:12:50
 *
 */
public class test {

	public static void main(String[] args) {
		String json="{\"_input_charset\":\"UTF-8\",\"is_success\":\"T\"}";
		JSONObject obj=JSON.parseObject(json);
		System.out.println(obj.toJSONString());
		String is_success=obj.getString("is_success");
		System.out.println(is_success);
		String err=obj.getString("error_code");
		System.out.println(err);

	}

}
