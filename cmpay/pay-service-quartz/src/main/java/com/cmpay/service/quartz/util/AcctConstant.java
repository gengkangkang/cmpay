package com.cmpay.service.quartz.util;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月22日 下午6:06:11
 *
 */
public class AcctConstant {

	public static int ACCTPOOLSIZE=1;
	public static int SLEEPTIME=5*1000;

	public static boolean inacct_start = true;


    //-----------------------------------------核心支付---------------------------------------------------
    public static  String coreurl="http://10.17.5.62:8082/xmlGateway/service";
//    public static  String coreurl="http://localhost:8888/core-rest/xmlGateway/service";
    public static final String xmlhead="<CMCORE xmlns=\"http://www.cm-inv.com/CMINV/2015/10\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.cm-inv.com/CMINV/2015/10 CMCORE1.0.xsd\">";
    public static final String xmltail="</CMCORE>";
    public static  String version="1.0";
    public static  String channelId="0013";
    public static  String spName="CMPAY";
    public static  String NumTranCode="000823";
    public static  String UTF8="UTF-8";

    //-----------------------------------------核心支付---------------------------------------------------


}
