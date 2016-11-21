package com.cmpay.weixin.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 下午2:00:14
 *
 */
public class Test {

	public static void main(String[] args){
//		DateTime d=new DateTime();
//		DateTime d1=d.plusMillis(24*3600*1000);
//		System.out.println(d);
//		System.out.println(d.toString("yyyy-MM-dd HH:mm:ss"));
//		System.out.println(d1);
//		System.out.println(d1.toString("yyyy-MM-dd HH:mm:ss"));
//		System.out.println(d1.toDate());


		Map<String,String> map=new HashMap<String,String>();
		map.put("hello", "哈哈");
		map.put("hello1", "哈哈1");
		map.put("hello2", "哈哈2");
        System.out.println(map.toString());
       String str= map.remove("hello1");
       System.out.println(str);
       System.out.println(map.toString());

	}
}
