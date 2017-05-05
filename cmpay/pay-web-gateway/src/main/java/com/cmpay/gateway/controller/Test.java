package com.cmpay.gateway.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmpay.common.security.util.SignUtil;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月14日 上午9:54:59
 *
 */
public class Test extends BaseAction {

//	@Override
//	public String encMsg(Object msg){
//		String str=this.encMsg(msg);
//		return str;
//	}

	public static void main(String[] args){
		Test t=new Test();
//	     t.initRsaHelper("D:\\zmcf\\cmpay\\", "cmpay.cer", "cmpay.pfx", "gkk@cmpay");
//merchantId, inchannel, userId, amount, cardNo, origiOrderId, payCode, transType, orderIp, idNo, idType, name, bankMobile, bankCode

		//代扣
		//		Map<String,String> map=new HashMap<String,String>();
//        map.put("merchantId", "800010000001");
//        map.put("inchannel", "0000");
//        map.put("userId", "123456");
//        map.put("amount", "16.01");
//        map.put("cardNo", "6227001217450011115");
//        map.put("origiOrderId", "20170414003");
//        map.put("payCode", "");
//        map.put("transType", "01");
//        map.put("orderIp", "10.17.1.30");
//        map.put("idNo", "410123199005168936");
//        map.put("idType", "I");
//        map.put("name", "测试");
//        map.put("bankMobile", "18721359153");
//        map.put("bankCode", "0005");

//交通银行
//        map.put("merchantId", "800010000001");
//        map.put("inchannel", "0000");
//        map.put("userId", "888888");
//        map.put("amount", "6.6");
//        map.put("cardNo", "6222620140009946277");
//        map.put("origiOrderId", "2016121822362");
//        map.put("payCode", "");
//        map.put("transType", "01");
//        map.put("orderIp", "10.17.1.30");
//        map.put("idNo", "110106198412232437");
//        map.put("idType", "I");
//        map.put("name", "王五");
//        map.put("bankMobile", "13621766042");
//        map.put("bankCode", "0006");

//退款参数
//        map.put("merId", "800010000001");
//        map.put("inchannel", "0000");
//        map.put("userId", "888888");
//        map.put("refundAmt", "6.6");
//        map.put("origiOrderId", "2016121822362");
//        map.put("orderIp", "10.17.1.30");



		//代付参数

//		Map<String,String> map=new HashMap<String,String>();
//        map.put("merchantId", "800010000001");
//        map.put("inchannel", "0000");
//        map.put("userId", "666666");
//        map.put("amount", "15.01");
//        map.put("cardNo", "6222620140009946277");
//        map.put("origiOrderId", "201704170008");
//        map.put("payCode", "CMPAY0001");
//        map.put("transType", "02");
//        map.put("orderIp", "10.17.1.30");
//        map.put("province", "上海");
//        map.put("city", "黄浦区");
//        map.put("name", "测试");
//        map.put("bankName", "建设银行");
//        map.put("bankCode", "0005");


        //订单查询
//    	Map<String,String> map=new HashMap<String,String>();
//        map.put("merId", "800010000001");
//        map.put("origOrderNo", "201704170008");
//
//
//        JSONObject jsonObject=(JSONObject) JSON.toJSON(map);
//        String sign=SignUtil.genMd5Sign(jsonObject, "123456");
//        System.out.println("sign="+sign);
//        map.put("sign", sign);
//        System.out.println(JSON.toJSONString(map));

		//交易限额查询
		Map<String,String> map=new HashMap<String,String>();
        map.put("merId", "800010000001");
        map.put("inchannel", "0000");
        map.put("cardNo", "6222620140009946277");



        JSONObject jsonObject=(JSONObject) JSON.toJSON(map);
        String sign=SignUtil.genMd5Sign(jsonObject, "123456");
        System.out.println("sign="+sign);
        map.put("sign", sign);
        System.out.println(JSON.toJSONString(map));

	}

}
