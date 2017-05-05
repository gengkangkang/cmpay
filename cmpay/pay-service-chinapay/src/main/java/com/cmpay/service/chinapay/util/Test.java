package com.cmpay.service.chinapay.util;

import chinapay.Base64;
import chinapay.SecureLink;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年4月17日 下午2:26:51
 *
 */
public class Test {

	public String getCpSinPayCheckValue(){
		String Data = "hello world";
		String plainData = "";
		try {
			plainData = new String(Base64.encode(Data.getBytes("GBK")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("转换成Base64后数据：" + plainData);
		String chkValue = null;
		// 初始化key文件：
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		boolean flag = false;
		try {
			System.out.println("key path:{}");
			flag = key.buildKey("808080211389035", 0, "D:\\config\\chinapay\\808080211389035_MerPrK.key");
		} catch (Exception e) {
			System.out.println("build key error!{}");
			e.printStackTrace();
			return null;
		}
		if(flag == false){
			System.out.println("build key error!");
			return null;
		}else{
			System.out.println("build key success!");
		}
		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(plainData);
		System.out.println("签名内容:"+ chkValue);
		return chkValue;
	}

	public static void main(String[] args) {
		Test t=new Test();
		System.out.println(t.getCpSinPayCheckValue());

	}

}
