package com.cmpay.weixin.service;

import org.joda.time.DateTime;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 下午2:00:14
 *
 */
public class Test {

	public static void main(String[] args){
		DateTime d=new DateTime();
		DateTime d1=d.plusMillis(24*3600*1000);
		System.out.println(d);
		System.out.println(d.toString("yyyy-MM-dd HH:mm:ss"));
		System.out.println(d1);
		System.out.println(d1.toString("yyyy-MM-dd HH:mm:ss"));
		System.out.println(d1.toDate());


	}
}
