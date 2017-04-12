package com.cmpay.service.trade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cmpay.service.trade.exception.TradeBizException;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月6日 上午10:26:48
 *
 */
public class Test {

	public Map<String,String> sayhello(String name){
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtils.equals("00", name)){
			throw new TradeBizException("1000","哈哈");
		}

		map.put("001", "呵呵");
		return map;

	}

	public static void main(String[] args) {
//		Test t=new Test();
//        try{
//		Map<String,String> r=t.sayhello("00");
//
//		System.out.println(r.toString());
//        }catch(TradeBizException e){
//        	System.out.println("捕捉到了哈哈");
//        	System.out.println(String.valueOf(e.getCode())+","+e.getMsg());
//        }

	//	System.out.println(new BigDecimal(16));

//		Map<String,String> map=new HashMap<String,String>();
//		map.put("code", "000");
//		map.put("msg", "8");
//
//		String json=JSON.toJSONString(map);
//		System.out.println(json);
		String name="disconf.hello";
	    if (name.contains("disconf.")) {
            String newName = name.substring(name.indexOf('.') + 1);
            System.out.println(newName);
        }

	}

}
