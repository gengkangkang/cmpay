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
	 Map<String,String> map=new HashMap<String,String>();
        map.put("merchantId", "300010000001");
        map.put("inchannel", "0000");
        map.put("userId", "666666");
        map.put("amount", "16.01");
        map.put("cardNo", "6227001217450011115");
        map.put("origiOrderId", "20170719004");
        map.put("payCode", "CMPAY0005");
        map.put("transType", "01");
        map.put("orderIp", "10.17.1.30");
        map.put("idNo", "410123199005168936");
        map.put("idType", "I");
        map.put("name", "测试");
        map.put("bankMobile", "18721359153");
        map.put("bankCode", "0005");



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
//        map.put("origiOrderId", "201705050001");
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
//        map.put("merId", "300010000001");
//        map.put("origOrderNo", "877706403630809089");
//
//
//        JSONObject jsonObject=(JSONObject) JSON.toJSON(map);
//        String sign=SignUtil.genMd5Sign(jsonObject, "123456");
//        System.out.println("sign="+sign);
//        map.put("sign", sign);
//        System.out.println(JSON.toJSONString(map));

		//交易限额查询
//		Map<String,String> map=new HashMap<String,String>();
//        map.put("merId", "300010000001");
//        map.put("inchannel", "0014");
//        map.put("cardNo", "6226620607792207");



//		 Map<String,String> map=new HashMap<String,String>();
//	        map.put("merchantId", "800010000001");
//	        map.put("inchannel", "0000");
//	        map.put("userId", "123456");
//	        map.put("amount", "10");
//	        map.put("cardNo", "6214830215110593");
//	        map.put("origiOrderId", "20170519004");
//	        map.put("payCode", "");
//	        map.put("transType", "05");
//	        map.put("orderIp", "10.17.1.30");
//	        map.put("idNo", "410221199005169930");
//	        map.put("idType", "I");
//	        map.put("name", "耿康康");
//	        map.put("bankMobile", "18721359153");
//	        map.put("bankCode", "0005");


//----------------------------------生产测试------------------------------------------------------

		//代扣
//		 Map<String,String> map=new HashMap<String,String>();
//	        map.put("merchantId", "800010000002");
//	        map.put("inchannel", "0014");
//	        map.put("userId", "123456");
//	        map.put("amount", "0.2");
//	        map.put("cardNo", "6230580000038051343");
//	        map.put("origiOrderId", "20170613003");
//	        map.put("payCode", "");
//	        map.put("transType", "05");
//	        map.put("orderIp", "10.17.1.30");
//	        map.put("idNo", "370205197802015545");
//	        map.put("idType", "I");
//	        map.put("name", "董一芳");
//	        map.put("bankMobile", "13065000501");
//	        map.put("bankCode", "0014");

	      //代付
//			Map<String,String> map=new HashMap<String,String>();
//	        map.put("merchantId", "800010000003");
//	        map.put("inchannel", "0014");
//	        map.put("userId", "13065000501");
//	        map.put("amount", "1");
//	        map.put("cardNo", "6230580000038051343");
//	        map.put("origiOrderId", "20170613008");
//	        map.put("payCode", "CMPAY0001");
//	        map.put("transType", "02");
//	        map.put("orderIp", "10.17.1.30");
//	        map.put("province", "上海");
//	        map.put("city", "黄浦区");
//	        map.put("name", "董一芳");
//	        map.put("bankName", "平安银行");
//	        map.put("bankCode", "0014");

//	    	Map<String,String> map=new HashMap<String,String>();
//	        map.put("merchantId", "800010000003");
//	        map.put("inchannel", "0014");
//	        map.put("userId", "13681861579");
//	        map.put("amount", "1");
//	        map.put("cardNo", "6216910210651078");
//	        map.put("origiOrderId", "20170613007");
//	        map.put("payCode", "CMPAY0001");
//	        map.put("transType", "02");
//	        map.put("orderIp", "10.17.1.30");
//	        map.put("province", "上海");
//	        map.put("city", "黄浦区");
//	        map.put("name", "张海萍");
//	        map.put("bankName", "民生银行");
//	        map.put("bankCode", "0009");

	        //验卡
//			Map<String,String> map=new HashMap<String,String>();
//	        map.put("merchantId", "800010000002");
//	        map.put("inchannel", "0014");
//	        map.put("userId", "13065000501");
//	        map.put("authChannel", "CMPAY0001");
//	        map.put("cardNo", "6230580000038051343");
//	        map.put("cardType", "DEBIT");
//	        map.put("idNo", "370205197802015545");
//	        map.put("idType", "I");
//	        map.put("name", "董一芳");
//	        map.put("bankMobile", "13065000501");
//	        map.put("bankCode", "0014");
//	        map.put("terminalType", "01");


        JSONObject jsonObject=(JSONObject) JSON.toJSON(map);
        String sign=SignUtil.genMd5Sign(jsonObject, "123456");
        System.out.println("sign="+sign);
        map.put("sign", sign);
        System.out.println(JSON.toJSONString(map));

	}

}
