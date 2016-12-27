package com.cmpay.common.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年9月8日 下午3:30:17
 *
 * 常用工具类
 *
 */
public class CmpayUtils {

	public static int orderCount=0;
	public static int orderMaxCount=9999;
	public static int merLength=4;

	private static Logger LOG=Logger.getLogger(CmpayUtils.class);

    /**
     * 获取Ip地址
     * @param request
     * @return
     */
	public static String getIpAddr(HttpServletRequest request){

		String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
	}

	/**
	 * 生成订单号(WX+0001+01+日期+四位数)
	 * @param merNo
	 * @param payChannel
	 * @param transType
	 * @return
	 */
	public static String createOrderId(String payChannel,String merNo,String transType){
		StringBuffer sb=new StringBuffer();
		sb.append(payChannel);
		if(merNo.length()>merLength){
           sb.append(StringUtils.substring(merNo, merNo.length()-merLength));
		}else{
			sb.append(merNo);
		}
		sb.append(transType).append(getCurrentTime("yyyyMMddHHmmss"));
		String res=sb.append(getOrderCount()).toString();
		return res;

	}
/**
 * 生产订单号（payType+transType+0001+日期+三位随机数）
 * @param merNo
 * @param transType
 * @return
 */
	public static String createOrderId(String payType,String transType){
		StringBuffer sb=new StringBuffer();
		sb.append(payType);
		sb.append(transType).append(getOrderCount());
		String res=sb.append(getCurrentTime("yyyyMMddHHmmss")).append(getRandom(999,100)).toString();
		return res;

	}

	public static String createCPOrderId(String transType){
		StringBuffer sb=new StringBuffer();
		sb.append(transType).append(getCurrentTime("HHmmss"));
		String res=sb.append(getRandom(99999999,10000000)).toString();
		return res;

	}

	public static String getRandom(int max,int min){

		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;

		return String.valueOf(s);
	}

	/**
	 * 获取当前时间
	 * @param formatstr
	 * @return
	 */
	public static String getCurrentTime(String formatstr){
		SimpleDateFormat  sdf=new SimpleDateFormat(formatstr);
		return sdf.format(new Date());
	}

	public static String getOrderCount(){

		System.out.println("orderCount================"+orderCount);
		String res=null;
		String ord=String.valueOf(orderCount);
		if(ord.length()==1){
			res="000"+ord;
		}else if(ord.length()==2){
			res="00"+ord;
		}else if(ord.length()==3){
			res="0"+ord;
		}else{
			res=ord;
		}
       //暂时计数器放在内存中，后续可以放在缓存redis中
		if(orderCount<orderMaxCount){
			orderCount++;
		}else{
			orderCount=0;
		}

		return res;
	}

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String u=StringUtils.replace(str, "-", "");
        return u;
    }

	public static String formatDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        return !(obj instanceof Number) ? false : false;
    }

    //MD5相关
    private static final String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String encodeMD5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = md5.digest(password.getBytes("utf-8"));
            String passwordMD5 = byteArrayToHexString(byteArray);
            return passwordMD5;
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return password;
    }

    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        for (byte b : byteArray) {
            sb.append(byteToHexChar(b));
        }
        return sb.toString();
    }

    private static Object byteToHexChar(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hex[d1] + hex[d2];
    }


	public static void threadSleep(int time) {
		// 最小的轮询间隔，休眠。
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
//		System.out.println(createOrderId("WX","98765","00"));
//		System.out.println(PayWayEnum.WX.toString());
//		System.out.println(PayWayEnum.WX.getValue());

		System.out.println(getRandom(999, 100));
		System.out.println(createOrderId("80001","01"));
		System.out.println(createCPOrderId("01"));

		System.out.println(getCurrentTime("yyyyMMdd"));
	}

}
