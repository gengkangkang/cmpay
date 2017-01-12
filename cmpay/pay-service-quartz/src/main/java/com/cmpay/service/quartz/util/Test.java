package com.cmpay.service.quartz.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月10日 下午1:26:30
 *
 */
public class Test {
	private  Logger logger=LoggerFactory.getLogger(this.getClass());

	public void hello(){
		try{
		Map map=null;
		 String msg=(String) map.get("msg");
		 System.out.println(msg);
		}catch(Exception e){
			logger.error("出错了：[{}]","信息", e);
		}
	}

	public static void main(String[] args) {
		new Test().hello();

	}

}
