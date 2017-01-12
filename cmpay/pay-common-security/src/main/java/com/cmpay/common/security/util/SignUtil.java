package com.cmpay.common.security.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 签名工具类
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月5日 上午10:25:15
 *
 */
public class SignUtil {


	   public static String genMd5Sign(JSONObject reqObj,String md5_key)
	    {
	        if (reqObj == null || StringUtils.isBlank(md5_key))
	        {
	            return "";
	        }

	       return addSignMD5(reqObj, md5_key);


	    }

	    private static String addSignMD5(JSONObject reqObj, String md5_key)
	    {

	        // 生成待签名串
	        String sign_src = genSignData(reqObj);
	        System.out.println("加签原串:" + sign_src);
	        sign_src += "&key=" + md5_key;
	        try
	        {
	        	return DigestUtils.md5Hex(sign_src);
	        } catch (Exception e)
	        {
	            System.out.println( "MD5加签名异常" + e.getMessage());
	            return "";
	        }
	    }

	    public static String genSignData(JSONObject jsonObject)
	    {
	        StringBuffer content = new StringBuffer();

	        // 按照key做首字母升序排列
	        List<String> keys = new ArrayList<String>(jsonObject.keySet());
	        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
	        for (int i = 0; i < keys.size(); i++)
	        {
	            String key = keys.get(i);
	            if ("sign".equals(key))
	            {
	                continue;
	            }
	            String value = jsonObject.getString(key);
	            // 空串不参与签名
	            if (StringUtils.isBlank(value))
	            {
	                continue;
	            }
	            content.append((i == 0 ? "" : "&") + key + "=" + value);

	        }
	        String signSrc = content.toString();
	        if (signSrc.startsWith("&"))
	        {
	            signSrc = signSrc.replaceFirst("&", "");
	        }
	        return signSrc;
	    }

	public static void main(String[] args) {
//		String jons="{\"name\":\"sex\",\"sex\":\"男\"}";
		String jons="{\"cede\":\"0000\",\"data\":{\"name\":\"sex\",\"sex\":\"男\"}}";

		JSONObject obj=JSONObject.parseObject(jons);
		String sign=genMd5Sign(obj,"123");
		System.out.println("sign="+sign);

	}

}
