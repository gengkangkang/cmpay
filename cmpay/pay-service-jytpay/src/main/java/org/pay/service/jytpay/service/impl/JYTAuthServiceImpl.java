package org.pay.service.jytpay.service.impl;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.pay.service.jytpay.model.CpJYTRespDef;
import org.pay.service.jytpay.service.JYTAuthService;
import org.pay.service.jytpay.utils.AppException;
import org.pay.service.jytpay.utils.CryptoUtils;
import org.pay.service.jytpay.utils.DESHelper;
import org.pay.service.jytpay.utils.DateTimeUtils;
import org.pay.service.jytpay.utils.HttpClient431Util;
import org.pay.service.jytpay.utils.RZRSAHelper;
import org.pay.service.jytpay.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;

/**
 * 金运通认证
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月25日 下午4:45:31
 *
 */
@Service
public class JYTAuthServiceImpl implements JYTAuthService {

private Logger logger = LoggerFactory.getLogger(getClass());


//	@Value("#{env['jytRZPrivateKEY']}")
//	private String jytRZPrivateKEY;
//
//	@Value("#{env['jytRZPublicKEY']}")
//	private String jytRZPublicKEY;

	@Value("#{env['jytRZAuthSeverUrl']}")
	private String jytRZAuthSeverUrl;

//	@Value("#{env['jytRZMerchantId']}")
//	private String jytRZMerchantId;

//	@Value("#{env['pfxPasswd']}")
//	private String pfxPasswd;
//
//	@Value("#{env['jytPath']}")
//	private String jytPath;
	@Value("#{env['jytRespMsgParamSeparator']}")
	private String jytRespMsgParamSeparator;
	@Value("#{env['jytRespMsgParamPrefixXmlEnc']}")
	private String jytRespMsgParamPrefixXmlEnc;
	@Value("#{env['jytRespMsgParamPrefixKeyEnc']}")
	private String jytRespMsgParamPrefixKeyEnc;
	@Value("#{env['jytRespMsgParamPrefixSign']}")
	private String jytRespMsgParamPrefixSign;
	@Value("#{env['jytRespMsgParamPrefixMerchantId']}")
	private String jytRespMsgParamPrefixMerchantId;


//	private RZRSAHelper rsaHelper;

	public  String TRAN_CODE_4003 = "TR4003";

	public  String RESP_MSG_PARAM_SEPARATOR = "&";

	/**返回报文merchant_id字段前缀*/
	public  String RESP_MSG_PARAM_PREFIX_MERCHANT_ID = "merchant_id=";

	/**返回报文xml_enc字段前缀*/
	public  String RESP_MSG_PARAM_PREFIX_XML_ENC = "xml_enc=";
	/**返回报文xml_enc字段前缀*/
	public  String RESP_MSG_PARAM_PREFIX_KEY_ENC = "key_enc=";

	/**返回报文sign字段前缀*/
	public  String RESP_MSG_PARAM_PREFIX_SIGN = "sign=";

	public  String CLIENT_PRIVATE_KEY="";

	public  String SERVER_PUBLIC_KEY= "";

	public String tran_flowid = "";
	public String tran_date = "";
	public String tran_time = "";

	public String payChanndelCode = null;

	private BufferedReader br;

//	@PostConstruct
//	public void initRZRsaHelper() {
//		rsaHelper = new RZRSAHelper();
//		logger.info("金运通认证初始化秘钥===================");
//		try {
//					rsaHelper.initKey(jytPath+jytRZPrivateKEY, pfxPasswd, jytPath+jytRZPublicKEY);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}



	/**
	 * 金运通四要素后台认证
	 *
	 * @param bank_card_no
	 * @param bank_code
	 * @param id_num
	 * @param id_name
	 * @param terminal_type 认证请求终端类型 商户使用验证接口的终端类型：01 APP，02 WAP，03 WEB，04 SIM卡，05 VI-POS，06 SD卡
	 * @param bank_card_type
	 * @param phone_no
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> JYTAuth(String merNo,String pubKey,String privKey,String privPwd,String bank_card_no, String bank_code, String id_num, String id_name,
			String terminal_type, SignCardTypeEnum bank_card_type, String phone_no, IdTypeEnum idType) {
		CpJYTRespDef cpJYTRespDef = new CpJYTRespDef();
		String code="";
		String msg="";
		Map<String,String> rmap=new HashMap<String,String>();
		String query_tran_flowid = getTranFlow(merNo);
    	tran_date =DateTimeUtils.getNowDateStr(DateTimeUtils.DATE_FORMAT_YYYYMMDD);
    	tran_time = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_HHMMSS);
    	cpJYTRespDef.setTran_flowid(query_tran_flowid);
    	cpJYTRespDef.setTran_date(tran_date);
    	cpJYTRespDef.setTran_time(tran_time);
    	cpJYTRespDef.setTran_code(TRAN_CODE_4003);
//    	terminal_type = paramFactory.getJytTerminalType();
    	try {
    		// 卡类型转换

    		String dcType = "D";
    		if (SignCardTypeEnum.DEBIT == bank_card_type) {
    			dcType = "D";
    		} else if (SignCardTypeEnum.CREDIT == bank_card_type) {
    			dcType = "C";
    		}
//			String dcType =bank_card_type;


			String xml= getXmlAccount(TRAN_CODE_4003,query_tran_flowid,bank_card_no, bank_code,
					id_num, id_name, terminal_type, dcType, phone_no,getCpIdType(idType),merNo);

           //后续可考虑把不同商户秘钥放入redis中
			RZRSAHelper rsaHelper=new RZRSAHelper();
			rsaHelper.initKey(privKey, privPwd, pubKey);

			// 把XML对象转换成HEX
			String mac=signMsg(xml,rsaHelper);
			// 发送报文
			String respXml = sendMsg(xml, mac,jytRZAuthSeverUrl,merNo,rsaHelper);

	        // 获取报文头的响应代码
	        String respCode = getMsgRespCode(respXml);
	        // 获取报文交易状态码
//	        String IsBcnAndIdnConform = getIsBcnAndIdnConform(respXml);
	        // 获取报文交易状态码
	        String respDesc = getMsgRespDesc(respXml);

		    code=respCode;
		    msg=respDesc;
//	        cpJYTRespDef.setResp_code(respCode);
//	        cpJYTRespDef.setDescription(respDesc);

    	} catch (Exception e) {
		    logger.error("金运通认证异常");
		    code="C9999";
		    msg="金运通认证异常";
			e.printStackTrace();
		}finally{
			rmap.put("code", code);
			rmap.put("msg", msg);
		}
		return rmap;
	}

	public String getTranFlow(String merNo){

		return merNo+ DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS)+RandomStringUtils.randomNumeric(6);
	}

	/**
	 * 映射jyt的证件类型编号[ 01:身份证; 02:军官证; 03:护照; 04:回乡证; 05:台胞证; 06:警官证; 07:士兵证;
	 * 99:其他证件; ]
	 *
	 * @param idType
	 * @return
	 */
	private String getCpIdType(IdTypeEnum idType) {
		String cpIdType = "01";
		if (IdTypeEnum.I == idType) {
			cpIdType = "01";
		} else if (IdTypeEnum.B == idType) {
			cpIdType = "02";
		} else if (IdTypeEnum.P == idType) {
			cpIdType = "03";
		} else if (IdTypeEnum.H == idType) {
			cpIdType = "04";
		} else if (IdTypeEnum.W == idType) {
			cpIdType = "05";
		} else if (IdTypeEnum.C == idType) {
			cpIdType = "06";
		} else if (IdTypeEnum.S == idType) {
			cpIdType = "07";
		} else {
			cpIdType = "99";
		}
		return cpIdType;
	}

    public String getXmlAccount(String tran_code,String query_tran_flowid,String bank_card_no, String bank_code,
    		String id_num, String id_name, String terminal_type, String bank_card_type, String phone_no,String cert_type,String merNo){
		StringBuffer xml = new StringBuffer();
		xml.append(getMsgHeadXml(tran_code,query_tran_flowid,merNo));
		xml.append("<body>");
		xml.append("<bank_card_no>").append(bank_card_no).append("</bank_card_no>");
		xml.append("<bank_code>").append(bank_code).append("</bank_code>");
		xml.append("<cert_type>").append(cert_type).append("</cert_type>");
		xml.append("<id_num>").append(id_num).append("</id_num>");
		xml.append("<id_name>").append(id_name).append("</id_name>");
		xml.append("<terminal_type>").append(terminal_type).append("</terminal_type>");
		xml.append("<bank_card_type>").append(bank_card_type).append("</bank_card_type>");
		xml.append("<phone_no>").append(phone_no).append("</phone_no>");
		xml.append("</body></message>");
		return xml.toString();
	}

	public String getMsgHeadXml(String tranCode,String tran_flowid,String merNo){
		StringBuffer headXml = new StringBuffer();
		headXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><message><head><version>1.0.2</version>");
		headXml.append("<tran_type>01</tran_type>")
			   .append("<merchant_id>").append(merNo).append("</merchant_id>");
		headXml.append("<tran_date>").append(tran_date)
		       .append("</tran_date>");
		headXml.append("<tran_time>").append(tran_time)
		       .append("</tran_time><tran_flowid>").append(tran_flowid)
		       .append("</tran_flowid><tran_code>").append(tranCode).append("</tran_code>");
		headXml.append("</head>");

		return headXml.toString();
	}

	public String signMsg( String xml ,RZRSAHelper rsaHelper){
		String hexSign = null ;

		try {
			byte[] sign = rsaHelper.signRSA(xml.getBytes("UTF-8"), false, "UTF-8") ;

			hexSign = StringUtil.bytesToHexString(sign) ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hexSign;
	}

	public String sendMsg(String xml,String sign,String appSeverUrl,String merNo,RZRSAHelper rsaHelper) throws Exception{
		logger.info("实名认证上送地址："+appSeverUrl);
		logger.info("实名认证上送报文："+xml);
		logger.info("实名认证上送签名："+sign);

		byte[] des_key = DESHelper.generateDesKey() ;

		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("merchant_id", merNo);
		paramMap.put("xml_enc", encryptXml(xml,des_key));
		paramMap.put("key_enc", encryptKey(des_key,rsaHelper));
		paramMap.put("sign", sign);

		// 获取执行结果

		String res = HttpClient431Util.doPost(paramMap, appSeverUrl);

		if(res  == null){
			logger.error("服务器连接失败");

			throw new AppException("服务器连接失败");
		}else{
			logger.info("连接服务器成功,返回结果"+res);
		}
		String []respMsg = res.split(jytRespMsgParamSeparator);

		String merchantId = respMsg[0].substring(jytRespMsgParamPrefixMerchantId.length()+1);
		String respXmlEnc = respMsg[1].substring(jytRespMsgParamPrefixXmlEnc.length()+1);
		String respKeyEnc = respMsg[2].substring(jytRespMsgParamPrefixKeyEnc.length()+1);
		String respSign = respMsg[3].substring(jytRespMsgParamPrefixSign.length()+1);

		byte respKey[] = decryptKey(respKeyEnc,rsaHelper) ;

		String respXml = decrytXml( respXmlEnc,respKey ) ;


		logger.info("返回报文merchantId:"+merchantId);
		logger.info("返回报文XML:"+respXml);
		logger.info("返回报文签名:"+respSign);

		return respXml;
	}

	private String encryptXml( String xml, byte[] key){

		String enc_xml = CryptoUtils.desEncryptToHex(xml, key) ;

		return enc_xml;
	}

	private String encryptKey( byte[] key,RZRSAHelper rsaHelper){

		byte[] enc_key = null ;
		try {
			enc_key = rsaHelper.encryptRSA(key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return StringUtil.bytesToHexString(enc_key) ;
	}

	private byte[] decryptKey(String hexkey,RZRSAHelper rsaHelper){
		byte[] key = null ;
		byte[] enc_key = StringUtil.hexStringToBytes(hexkey) ;

		try {
			key = rsaHelper.decryptRSA(enc_key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return key ;
	}

	public String decrytXml(String xml_enc, byte[] key) {
		String xml = CryptoUtils.desDecryptFromHex(xml_enc, key) ;
		return xml;
	}

	public String getMsgRespCode(String respMsg) throws Exception{
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));

		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/head");

		Node respCodeNode = packageHead.selectSingleNode("resp_code");

		return respCodeNode.getText();
	}

	public String getMsgRespDesc(String respMsg) throws Exception{
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));

		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/head");

		Node respCodeNode = packageHead.selectSingleNode("resp_desc");

		return respCodeNode.getText();
	}

}
