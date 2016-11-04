package com.cmpay.common.security;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.cmpay.common.security.util.DESHelper;
import com.cmpay.common.security.util.RSAHelper;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年10月18日 下午3:28:33
 *
 */
public class Test {

	 public static void main(String[] args){
    	 RSAHelper rsaHelper=new RSAHelper();
    	 //初始化秘钥
    	 try {
			rsaHelper.initKey("D:/zmcf/cmpay/cmpay.pfx", "gkk@cmpay", "D:/zmcf/cmpay/cmpay.cer");
		} catch (Exception e) {
			System.out.println("初始化秘钥失败");
			e.printStackTrace();
		}

       String msg="核心工作会议加密演示报文";
		try {
           System.out.println("///////////////////////////////////////////加密流程演示///////////////////////////////////////////////");
			System.out.println("【需要加密的报文数据】："+msg);
           //2.生成随机DES密码
			byte[] des_key = DESHelper.generateDesKey() ;
			System.out.println("第一步：随机生成DES密钥 ："+Base64.encodeBase64String(des_key));
            //3.用随机密码对报文msg进行加密
            String encBase64=DESHelper.desEncryptToBase64(msg, des_key);
            System.out.println("第二步：2.	使用DES秘钥对报文明文进行加密："+encBase64);

            //4.对DES秘钥进行加密
            byte[] enc_key = rsaHelper.encryptRSA(des_key, false, "UTF-8") ;
            String enc_key_base64=Base64.encodeBase64String(enc_key);
            System.out.println("第三步：对DES密钥进行RSA加密："+enc_key_base64);

            //5.对密文报文签名
			byte[] sign = rsaHelper.signRSA(Base64.decodeBase64(encBase64), false, "UTF-8") ;
			String signBase64=Base64.encodeBase64String(sign);
			System.out.println("第四步：对密文进行RSA签名："+signBase64);

			Map<String,String> encmap=new HashMap<String,String>();
             encmap.put("encMsg", encBase64);
             encmap.put("encKey", enc_key_base64);
             encmap.put("sign", signBase64);
             encmap.put("merchantid", "0001");

             String encstrJson=JSON.toJSONString(encmap);
             System.out.println("【需要发送的明文】="+encstrJson);
             String encstrJsonToBase64=Base64.encodeBase64String(encstrJson.getBytes("UTF-8"));
             System.out.println("第五步：对传输报文进行Base64编码="+encstrJsonToBase64);

             System.out.println("///////////////////////////////////////////加密流程演示///////////////////////////////////////////////");


		}  catch (Exception e) {
			System.out.println("签名失败");
			e.printStackTrace();
		}


     }


}
