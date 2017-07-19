package com.cmpay.service.htpay.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.PayStatusEnum;
import com.cmpay.common.util.Constants;
import com.cmpay.common.util.HttpClientUtil;
import com.cmpay.service.htpay.conf.HtPayPara;
import com.cmpay.service.htpay.dao.CmpayHtCutOrderMapper;
import com.cmpay.service.htpay.model.CmpayHtCutOrder;
import com.cmpay.service.htpay.model.HtCutParaReq;
import com.cmpay.service.htpay.model.HtCutQueryParaReq;
import com.cmpay.service.htpay.model.HtSinCutQueryResp;
import com.cmpay.service.htpay.model.HtSinCutResp;
import com.cmpay.service.htpay.service.HtpayService;
import com.cmpay.service.htpay.util.HtpayUtil;

/**
 *  航天支付相关功能
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年6月26日 下午4:33:02
 *
 */
@Service
public class HtpayServiceImpl implements HtpayService {

	private Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	CmpayHtCutOrderMapper cmpayHtCutOrderMapper;
    @Autowired
	HtPayPara htPayPara;


	@Override
	public HtSinCutResp sinCut(String orderId, String cardNo, String custId, String bankCode,String bankName, BigDecimal transAmt,
			String custName, String idNo,IdTypeEnum idType,String mobile,String cardType,String cardAttribute,
			String description,String merId,String privkey,String pubKey){
		HtSinCutResp res=new HtSinCutResp();

		//创建代扣订单
		CmpayHtCutOrder cmpayHtCutOrder=new CmpayHtCutOrder();
		cmpayHtCutOrder.setOrderNo(orderId);
		cmpayHtCutOrder.setAmount(transAmt);
		cmpayHtCutOrder.setBankCode(bankCode);
		cmpayHtCutOrder.setBankName(bankName);
		cmpayHtCutOrder.setCardName(custName);
		cmpayHtCutOrder.setCardNo(cardNo);
		cmpayHtCutOrder.setCardType(cardType);
		cmpayHtCutOrder.setCardAttribute(cardAttribute);
		cmpayHtCutOrder.setCertId(idNo);
		cmpayHtCutOrder.setCertType(idType.toString());
		cmpayHtCutOrder.setMobile(mobile);
		cmpayHtCutOrder.setCreateTime(new Date());
		cmpayHtCutOrder.setCuryId("");
		cmpayHtCutOrder.setCustId(custId);
		cmpayHtCutOrder.setDescription(description);
		cmpayHtCutOrder.setJpaVersion((long)0);
		cmpayHtCutOrder.setMerId(merId);
		cmpayHtCutOrder.setTransDate(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		cmpayHtCutOrder.setTransStatus(PayStatusEnum.NEW.name());

		cmpayHtCutOrderMapper.insert(cmpayHtCutOrder);

		//准备代扣请求
		HtCutParaReq req=new HtCutParaReq();
		req.setService("create_instant_trade");
		req.setVersion(htPayPara.getHtpay_sinCutVersion());
		req.setPartner_id(merId);
		req.set_input_charset(htPayPara.getHtpay_encoding());
		req.setSign_type(htPayPara.getHtpay_signType());
		req.setMemo("单笔代扣");

		req.setRequest_no(orderId);
		req.setAmount(transAmt.toString());
		try{
		   req.setBankcard(Base64.encodeBase64String(encryptByPublicKey(cardNo.getBytes(htPayPara.getHtpay_encoding()),pubKey)));
		   req.setBankname(Base64.encodeBase64String(encryptByPublicKey(bankName.getBytes(htPayPara.getHtpay_encoding()),pubKey)));
		   req.setAccname(Base64.encodeBase64String(encryptByPublicKey(custName.getBytes(htPayPara.getHtpay_encoding()),pubKey)));
		   req.setCertno(Base64.encodeBase64String(encryptByPublicKey(idNo.getBytes(htPayPara.getHtpay_encoding()),pubKey)));
		   req.setCerttype("ZR01");
		   req.setMobile(Base64.encodeBase64String(encryptByPublicKey(mobile.getBytes(htPayPara.getHtpay_encoding()),pubKey)));

		}catch(Exception e){
          logger.error("加密航天支付参数异常！",e);
		}
		req.setRemark("代扣");
		req.setCardtype(cardType);
		req.setCardattribute(cardAttribute);

		logger.info("htpay代扣参数为："+req.toString());
//        logger.info("htpay配置信息【1】=【{}】，【2】=【{}】，【3】=【{}】",merId,privkey,pubKey);
//	     String signType = "RSA";//"MD5";
//	     String inputCharset = "UTF-8";
//	     String url = "http://pay.cm-dev.cn/mag/gateway/receiveOrderExt.do"; // mag地址

	     try{

	       Map<String, String> reqMap = HtpayUtil.buildRequestPara(req.convertToMap(), req.getSign_type(), privkey,req.get_input_charset());

	    	String result= HttpClientUtil.post(htPayPara.getHtpay_sinCutReqUrl(), reqMap, htPayPara.getHtpay_encoding(), htPayPara.getHtpay_encoding());
	    	logger.info("[{}]返回报文：{}",orderId,result);
	    	//解析返回报文，处理返回数据
            JSONObject obj=JSON.parseObject(result);
            String is_success=obj.getString("is_success");
            String error_code=null;
            String error_msg=null;
            if(StringUtils.equals("T", is_success)){
            	logger.info("htpay pay success!");
            	 res.setRespCode(Constants.SUCCESS_CODE);
    	    	 res.setRespMsg(Constants.SUCCESS_MSG);
    	    	 res.setTransStat(PayStatusEnum.SUCC.toString());

    	    	 cmpayHtCutOrder.setTransStatus(PayStatusEnum.SUCC.toString());
    	    	 cmpayHtCutOrder.setResTransStat(is_success);

            }else if(StringUtils.equals("F", is_success)){
            	logger.info("htpay pay failed!");
            	error_code=obj.getString("error_code");
            	error_msg=obj.getString("error_message");

            	 res.setRespCode(error_code);
    	    	 res.setRespMsg(error_msg);
    	    	 res.setTransStat(PayStatusEnum.FAIL.toString());

    	    	 cmpayHtCutOrder.setTransStatus(PayStatusEnum.FAIL.toString());
    	    	 cmpayHtCutOrder.setResTransStat(is_success);
    	    	 cmpayHtCutOrder.setResCode(error_code);
    	    	 cmpayHtCutOrder.setResMsg(error_msg);


            }else{
           	 res.setRespCode(Constants.PROCESS_CODE);
	    	 res.setRespMsg(Constants.PROCESS_MSG);
	    	 res.setTransStat(PayStatusEnum.DEALING.toString());

	    	 cmpayHtCutOrder.setTransStatus(PayStatusEnum.DEALING.toString());
	    	 cmpayHtCutOrder.setResTransStat(is_success);
            }


	     }catch(Exception e){
	    	 logger.error("请求航天支付异常！！",e);
	    	 res.setRespCode(Constants.EXCEPTION_CODE);
	    	 res.setRespMsg(Constants.EXCEPTION_MSG);
	    	 res.setTransStat(PayStatusEnum.DEALING.toString());


	     }
	     cmpayHtCutOrderMapper.updateByPrimaryKeySelective(cmpayHtCutOrder);

		return res;

	}

	@Override
	public HtSinCutQueryResp sinCutQuery(String orderNo,String merId,String privkey,String pubKey){

		HtSinCutQueryResp res=new HtSinCutQueryResp();

		//1.查询ht代扣表，看记录是否存在
		CmpayHtCutOrder cmpayHtCutOrder=cmpayHtCutOrderMapper.selectByPrimaryKey(orderNo);
		if(cmpayHtCutOrder==null){
           logger.info("ht表无此交易！[{}]",orderNo);
           return null;
		}
		//准备请求报文
		HtCutQueryParaReq req=new HtCutQueryParaReq();
          //测试用静态参数
//	     String signType = "RSA";
//	     String inputCharset = "UTF-8";
//	     String url = "http://pay.cm-dev.cn/mag/gateway/receiveOrderExt.do";
//	     String version="1.0";


				req.setService("query_trade");
				req.setVersion(htPayPara.getHtpay_sinCutVersion());
				req.setPartner_id(merId);
				req.set_input_charset(htPayPara.getHtpay_encoding());
				req.setSign_type(htPayPara.getHtpay_signType());
				req.setMemo("交易查询");

				req.setOuter_trade_no(orderNo);
				req.setTrade_type("INSTANT");


				logger.info("htpay代扣查询参数为："+req.toString());
			     try{
			    	  Map<String, String> reqMap = HtpayUtil.buildRequestPara(req.convertToMap(),req.getSign_type(), privkey,req.get_input_charset());

				    	String result= HttpClientUtil.post(htPayPara.getHtpay_sinCutQueryReqUrl(), reqMap,req.get_input_charset(),req.get_input_charset());

				    	logger.info("[{}]返回报文：{}",orderNo,result);
				    	//处理航天返回报文
				    	//1.若trade_list为空，则ht没有该笔订单，支付平台可认为该笔订单交易失败
				    	// 2. 交易成功的订单状态：PAY_FINISHED ， TRANSFER_SUCCESS ， TRADE_FINISHED
				    	// 3. 失败的状态：        TRADE_CLOSED
				    	// 4. else  处理中

				    	JSONObject obj=JSON.parseObject(result);
				    	if(!obj.containsKey("trade_list")){
				    		//无此订单，置失败
				    		logger.info("[{}]航天支付无此订单，订单状态置为失败",orderNo);
				    		 res.setRespCode(Constants.FAILED_CODE);
			    	    	 res.setRespMsg("航天支付无此订单");
			    	    	 res.setTransStat(PayStatusEnum.FAIL.toString());

			    	    	 cmpayHtCutOrder.setTransStatus(PayStatusEnum.FAIL.toString());
			    	    	 cmpayHtCutOrder.setResCode(res.getRespCode());
			    	    	 cmpayHtCutOrder.setResMsg(res.getRespMsg());


				    	}else{
				    		logger.info("[{}]航天支付查到订单，根据状态做进一步的判断",orderNo);
                            JSONArray arr=obj.getJSONArray("trade_list");
                            if(arr.size()==0 || arr.size()>1){
                            	logger.info("[{}]航天支付返回的订单list异常，暂时不做处理",orderNo);
                            }else{
                              JSONObject obj2=arr.getJSONObject(0);
                              String trade_status=obj2.getString("trade_status");
                              if(StringUtils.equals(trade_status, "TRADE_FINISHED")||StringUtils.equals(trade_status, "TRANSFER_SUCCESS")||StringUtils.equals(trade_status, "PAY_FINISHED")){
                            	  logger.info("[{}]航天支付订单交易成功",orderNo);

                            	 res.setRespCode(Constants.SUCCESS_CODE);
     			    	    	 res.setRespMsg(Constants.SUCCESS_MSG);
     			    	    	 res.setTransStat(PayStatusEnum.SUCC.toString());

     			    	    	 cmpayHtCutOrder.setTransStatus(PayStatusEnum.SUCC.toString());
     			    	    	 cmpayHtCutOrder.setResTransStat("T");
     			    	    	 cmpayHtCutOrder.setResCode(res.getRespCode());
     			    	    	 cmpayHtCutOrder.setResMsg(res.getRespMsg());
                              }else if(StringUtils.equals(trade_status, "TRADE_CLOSED")){
                            	  logger.info("[{}]航天支付订单交易失败",orderNo);
                            	 res.setRespCode(Constants.FAILED_CODE);
     			    	    	 res.setRespMsg(Constants.FAILED_MSG);
     			    	    	 res.setTransStat(PayStatusEnum.FAIL.toString());

     			    	    	 cmpayHtCutOrder.setTransStatus(PayStatusEnum.FAIL.toString());
     			    	    	 cmpayHtCutOrder.setResTransStat("F");
     			    	    	 cmpayHtCutOrder.setResCode(res.getRespCode());
     			    	    	 cmpayHtCutOrder.setResMsg(res.getRespMsg());
                              }else{
                            	  logger.info("[{}]航天支付订单交易处理中",orderNo);
                              }


                            }

				    	}
				    	cmpayHtCutOrderMapper.updateByPrimaryKeySelective(cmpayHtCutOrder);

				    	return res;

			     }catch(Exception e){
			    	 logger.error("提交代扣查询订单[{}]异常",orderNo,e);
			     }

	    	return null;
	}

    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static void main(String[] args){
    	String privkey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALjnEJwlMLT3WeQfg2dpb6e50RNrpE1A9tuEfkMo9/iopClUWjnOiOp+OPHeIMN5TvI53ZZHvU9gGVhVO7nFd9r7K/tp0cvXj55NwzXSyCOHyySqFVeozMdprIZy6JAc6ewqvACGbe7KaW/p8eBT9smZbt5FKetQ/iTzSAQy/Y0fAgMBAAECgYEAlZuIaf8BePtGhXkmFm9OQ5Tq4V7QMx3kX1vF2ct1UIILSdGRq4I8cBevNax4MBGgWgeODkF/oJDuEPXt/HMVdP59K0AHpWOP2h7RLahGhRUW+tbASKYsQvN6deHzcIFc93MHlaNENfNaLG78Uf90Bp873XXwg2AidBAdE8pXZVkCQQDbTBTFy/Vn0b/W7N9cHhmlF7Ssy+kO0VwRLWE7vzw0biph4c6a+Ylq1DG9+unpvHV/TOrpkbnaqErxgcILi+NFAkEA19lTq1AsSXiaLrHvM3dYgcsqhQmEC0eZrICCh1a8NRUtpBHXTJWhCB+MdlZJQUsdOpZLqGxROb+I0jOKE1hjEwJAcW9NX3M9IdLRlO86KoA3a7pzbt6bU1rgcNI0dZ9aGRTTP4Z6RJfHiUbx4/+id5FQheTXrrH+nShoe7xPD4HwsQJAGo1zi3l7WTZq0R+2a0BdNOpHOsJm6fRO5TeR1xBGaslGKg6/y34EpzkqUExhCpUw7Uob2IYqn83+CM2qSKLw3QJAARu4f44sWoFfpB/isQuLTJoK+SrATpaumkerzLyN8CkIzdfaQJkAF3+WARQuDmONdu4rJildw9SDzC/88VT6/g==";
    	String pubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv0rdsn5FYPn0EjsCPqDyIsYRawNWGJDRHJBcdCldodjM5bpve+XYb4Rgm36F6iDjxDbEQbp/HhVPj0XgGlCRKpbluyJJt8ga5qkqIhWoOd/Cma1fCtviMUep21hIlg1ZFcWKgHQoGoNX7xMT8/0bEsldaKdwxOlv3qGxWfqNV5QIDAQAB";
    	HtpayServiceImpl ht=new HtpayServiceImpl();
//    	ht.sinCut("20170711001", "6226090107757948", "6666", "0012", "招商银行", new BigDecimal("6.6"), "张三丰", "370923198908071518", IdTypeEnum.I, "15810806355", "DEBIT", "C", "代扣", "200000400063", privkey, pubKey);

        ht.sinCutQuery("20170629006", "200000400063", privkey, pubKey);
    }


}
