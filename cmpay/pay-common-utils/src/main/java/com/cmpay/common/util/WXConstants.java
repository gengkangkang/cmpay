package com.cmpay.common.util;
/**
 * 微信支付相关常量
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月16日 下午1:52:22
 *
 */
public class WXConstants {

	public static long PERIOD=24*60*60*1000;
	public static String TRADETYPE_JSAPI="JSAPI";  //公众号支付
	public static String TRADETYPE_NATIVE="NATIVE";  //原生扫码支付
	public static String TRADETYPE_APP="APP";  //APP支付
	public static String TRADETYPE_MICROPAY="MICROPAY";  //刷卡支付

	public static String WXSUCCESS="SUCCESS";

	public static String WXPAY="10";
	public static String KJPAY="20";
	public static String ALIPAY="30";


//微信返回报文常量
	public static String WX_RETURN_SUCCESS = "<xml>\n" +
            "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
            "  <return_msg><![CDATA[OK]]></return_msg>\n" +
            "</xml>";
	public static String WX_RETURN_FAIL = "<xml>\n" +
            "  <return_code><![CDATA[FAIL]]></return_code>\n" +
            "  <return_msg><![CDATA[接受失败]]></return_msg>\n" +
            "</xml>";


	//定义统一返回码
   public static String SUCCESS_CODE="0000";
   public static String SUCCESS_MSG="交易成功";
   public static String FAILED_CODE="9901";
   public static String FAILED_MSG="交易失败";
   public static String EXCEPTION_CODE="9999";
   public static String EXCEPTION_MSG="系统异常";
   public static String PROCESS_CODE="8801";
   public static String PROCESS_MSG="处理中";




}
