package com.cmpay.gateway.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.cmpay.common.security.util.SignUtil;
import com.cmpay.common.util.RedisConstants;
import com.cmpay.common.util.RedisUtil;
import com.cmpay.gateway.dao.MerConfigMapper;
import com.cmpay.gateway.model.MerConfig;
import com.cmpay.gateway.model.MerConfigExample;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年9月27日 下午2:22:54
 *
 */
public class BaseAction {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
//	private RSAHelper rsaHelper;

//	@Value("#{env['PrivateKEY']}")
//	private String PrivateKEY;
//
//	@Value("#{env['PublicKEY']}")
//	private String PublicKEY;
//
//	@Value("#{env['pfxPasswd']}")
//	private String pfxPasswd;
//
//	@Value("#{env['capath']}")
//	private String capath;

    @Autowired
    private MerConfigMapper merConfigMapper;
    @Autowired
    private RedisUtil redisUtil;

//	@PostConstruct
//	public void initRZRsaHelper() {
//		rsaHelper = new RSAHelper();
//		logger.info("统一支付平台初始化秘钥===================");
//		try {
//				rsaHelper.initKey(capath+PrivateKEY, pfxPasswd, capath+PublicKEY);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}

//	public void initRsaHelper(String path,String pubKey,String priKey,String pwd) {
//		rsaHelper = new RSAHelper();
//		logger.info("手动初始化统一支付平台秘钥===================");
//		try {
//				rsaHelper.initKey(path+priKey, pwd, path+pubKey);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}

	//加密报文
//	public String encMsg(Object obj){
//
//		String result=null;
//
//		try {
//			String encMsg=JSON.toJSONString(obj);
//            logger.info("需要加密的报文为：{}",encMsg);
//			byte[] des_key = DESHelper.generateDesKey() ;
//	        String encBase64=DESHelper.desEncryptToBase64(encMsg, des_key);
//
//	         byte[] enc_key = rsaHelper.encryptRSA(des_key, false, "UTF-8");
//	        String enc_key_base64=Base64.encodeBase64String(enc_key);
//
//			byte[] sign = rsaHelper.signRSA(Base64.decodeBase64(encBase64), false, "UTF-8");
//			String signBase64=Base64.encodeBase64String(sign);
//
//			Map<String,String> encmap=new HashMap<String,String>();
//            encmap.put("encMsg", encBase64);
//            encmap.put("encKey", enc_key_base64);
//            encmap.put("sign", signBase64);
//
//            String encstrJson=JSON.toJSONString(encmap);
//            String encstrJsonToBase64=Base64.encodeBase64String(encstrJson.getBytes("UTF-8"));
//             result=encstrJsonToBase64;
//             logger.info("加密返回报文：{}",result);
//		} catch (Exception e) {
//			logger.error("加密报文失败");
//			e.printStackTrace();
//		}
//
//		return result;
//	}

   //验证签名
//	public boolean verifySign(String sign,String msg){
//		boolean signFlag=false;
//        try {
//			 signFlag=rsaHelper.verifyRSA(Base64.decodeBase64(msg), Base64.decodeBase64(sign), false, "UTF-8");
//		} catch (Exception e) {
//			logger.error("验证签名失败");
//			e.printStackTrace();
//		}
//
//		return signFlag;
//	}

	public boolean verifyMD5Sign(String sign,JSONObject msg,String merId){
        try {
            	String key=(String) redisUtil.get(RedisConstants.CMPAY_MD5KEY_+merId);
            	if(StringUtils.isBlank(key)){
            		logger.info("redis中没有key值，从数据库中读取");
                    MerConfigExample merConfigExample=new MerConfigExample();
                    merConfigExample.or().andMerchantidEqualTo(merId);
            		List<MerConfig> merConfList=merConfigMapper.selectByExample(merConfigExample);
            		if(merConfList!=null && merConfList.size()>0){
            			key=merConfList.get(0).getPartnerkey();
            			//添加到缓存中
            			logger.info("数据库中查到数据，加入缓存中【{}】",key);
            			try{
                    	redisUtil.set(RedisConstants.CMPAY_MD5KEY_+merId, key);
            			}catch(Exception e){
            				e.printStackTrace();
            			}
            		}else{
            			logger.info("数据库中无商户key信息");
            			return false;
            		}
            	}
              String msgSign=SignUtil.genMd5Sign(msg, key);
              if(StringUtils.equals(msgSign, sign)){
            	  return true;
              }else{
            	  return false;
              }

		} catch (Exception e) {
			logger.error("验证签名失败");
			e.printStackTrace();
			return false;
		}
	}


	public String genMD5Sign(JSONObject msg,String merId){
        try {
            	String key=(String) redisUtil.get(RedisConstants.CMPAY_MD5KEY_+merId);
            	if(StringUtils.isBlank(key)){
            		logger.info("redis中没有key值，从数据库中读取");
                    MerConfigExample merConfigExample=new MerConfigExample();
                    merConfigExample.or().andMerchantidEqualTo(merId);
            		List<MerConfig> merConfList=merConfigMapper.selectByExample(merConfigExample);
            		if(merConfList!=null && merConfList.size()>0){
            			key=merConfList.get(0).getPartnerkey();
            			//添加到缓存中
            			logger.info("数据库中查到数据，加入缓存中【{}】",key);
            			try{
                    	redisUtil.set(RedisConstants.CMPAY_MD5KEY_+merId, key);
            			}catch(Exception e){
            				e.printStackTrace();
            			}
            		}else{
            			logger.info("数据库中无商户key信息");
            			return null;
            		}
            	}
              String msgSign=SignUtil.genMd5Sign(msg, key);

             return msgSign;
		} catch (Exception e) {
			logger.error("生成签名失败");
			e.printStackTrace();
			return null;
		}

	}

	//解密报文
//	public String decMsg(String encmsg,String enckey){
//
//		String decmsg=null;
//        try {
//			byte[] dec_key=rsaHelper.decryptRSA(Base64.decodeBase64(enckey), false, "UTF-8");
//			decmsg=DESHelper.desDecryptFromBase64(encmsg, dec_key);
//		} catch (Exception e) {
//			logger.error("解密报文异常！");
//			e.printStackTrace();
//		}
//		return decmsg;
//	}




}
