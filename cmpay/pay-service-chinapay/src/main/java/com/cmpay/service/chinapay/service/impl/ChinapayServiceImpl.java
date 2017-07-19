package com.cmpay.service.chinapay.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cmpay.common.enums.IdTypeEnum;
import com.cmpay.common.enums.SignCardTypeEnum;
import com.cmpay.common.util.CmpayUtils;
import com.cmpay.service.chinapay.conf.ChinaPaySinPay;
import com.cmpay.service.chinapay.dao.CpCutOrderMapper;
import com.cmpay.service.chinapay.dao.CpPayOrderMapper;
import com.cmpay.service.chinapay.model.ChinapaySfjReq;
import com.cmpay.service.chinapay.model.CpAuthBgRespDef;
import com.cmpay.service.chinapay.model.CpCutOrder;
import com.cmpay.service.chinapay.model.CpPayOrder;
import com.cmpay.service.chinapay.model.CpSinCutQueryReq;
import com.cmpay.service.chinapay.model.CpSinCutQueryResp;
import com.cmpay.service.chinapay.model.CpSinCutQueryRespDef;
import com.cmpay.service.chinapay.model.CpSinCutReq;
import com.cmpay.service.chinapay.model.CpSinCutResp;
import com.cmpay.service.chinapay.model.CpSinCutRespDef;
import com.cmpay.service.chinapay.model.CpSinPayQueryReq;
import com.cmpay.service.chinapay.model.CpSinPayQueryRespDef;
import com.cmpay.service.chinapay.model.CpSinPayReq;
import com.cmpay.service.chinapay.model.CpSinPayRespDef;
import com.cmpay.service.chinapay.model.TransStatusDef;
import com.cmpay.service.chinapay.service.ChinapayService;
import com.cmpay.service.chinapay.util.ChinapayUtil;
import com.cmpay.service.chinapay.util.CpAuthSignUtil;
import com.cmpay.service.chinapay.util.HttpClient;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;

/**
 *  银联相关服务
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月25日 下午4:01:27
 *
 */
@Service
public class ChinapayServiceImpl implements ChinapayService {

	private Logger logger = LoggerFactory.getLogger(getClass());

//	@Value("#{env['appSysId']}")
//	private String appSysId;
//	@Value("#{env['authPrivateKey']}")
//	private String authPrivateKey;
	@Value("#{env['backgroudAuthReqUrl']}")
	private String backgroudAuthReqUrl;
	@Value("#{env['authEncoding']}")
	private String authEncoding;


	@Value("#{env['sfjSinCutVersion']}")
	private String sfjSinCutVersion;
	@Value("#{env['sfjGateId']}")
	private String sfjGateId;
	@Value("#{env['sfjSinCutReqUrl']}")
	private String sfjSinCutReqUrl;
	@Value("#{env['sfjEncoding']}")
	private String sfjEncoding;
	@Value("#{env['sfjSinCutQueryReqUrl']}")
	private String sfjSinCutQueryReqUrl;

	@Value("#{env['pubId']}")
	private String pubId;

	@Autowired
	CpCutOrderMapper cpCutOrderMapper;
	@Autowired
	CpPayOrderMapper cpPayOrderMapper;

	@Autowired
	ChinaPaySinPay chinaPaySinPay;

	/**
	 * 卡号或存折号标识位 (0表示卡,1表示折)
	 */
	private static final String CARD_TYPE1 = "0";
	/**
	 * 人民币币种代码
	 */
	private static final String CURY_RMB = "156";
	/**
	 * 固定，交易类型
	 */
	private static final String TRANS_TYPE = "0003";

	/**
	 * 金额以分为单位
	 */
	private static final DecimalFormat AMT_FORMAT = new DecimalFormat("000000000000");

	/**
	 * 对公对私标记。“00”对私，“01”对公。
	 */
	private static final String PRIVATE_FLAG = "00";

	@Override
	public CpAuthBgRespDef bgAuth(String appSysId,String authPrivateKey,String cardNo, IdTypeEnum idType, String idNo, SignCardTypeEnum cardType, String custName, String cardPhone) {
		CpAuthBgRespDef authBgRespDef = new CpAuthBgRespDef();
		Map<String, String> paramMap = new HashMap<String, String>();
		// 系统编号
		paramMap.put("appSysId", appSysId);
		// paramMap.put("serviceType", "1028"); // shenxw 该字段没有在接口文档里
		paramMap.put("signMethod", "MD5");
		paramMap.put("save", "false");
		paramMap.put("cardNo", cardNo);
		String cpIdType = getCpIdType(idType);
		paramMap.put("certType", cpIdType);
		paramMap.put("certNo", idNo);
		paramMap.put("usrName", custName);
		paramMap.put("cardPhone", cardPhone);

		// FIXME:Tiger
		String dcType = "";
		if (SignCardTypeEnum.DEBIT == cardType) {
			dcType = "0";
		} else if (SignCardTypeEnum.CREDIT == cardType) {
			dcType = "1";
		}
		paramMap.put("dcType", dcType);

		// 不参与签名的参数
		Set<String> removeKey = new HashSet<String>();
		removeKey.add("signMethod");
		removeKey.add("signature");
		String signedMsg = CpAuthSignUtil.getURLParam(paramMap, true, removeKey);
		// 测试密钥88888888
		paramMap.put("signature", CpAuthSignUtil.sign(signedMsg, authPrivateKey));

		// String usrName = (String) paramMap.get("usrName");

		logger.info("请求信息Map:{}" + paramMap);
		Map<String, String> resMap = submitUrl(paramMap, backgroudAuthReqUrl);
		logger.info("返回信息Map:" + resMap);

		String signedMsgResp = CpAuthSignUtil.getURLParam(resMap, true, removeKey);
		// 测试密钥88888888
		String verify = CpAuthSignUtil.sign(signedMsgResp, authPrivateKey);
		if (verify.equalsIgnoreCase(resMap.get("signature"))) {
			logger.info("验签通过");
			authBgRespDef.setSignVerified(true);
		} else {
			logger.error("验签失败");
			authBgRespDef.setSignVerified(false);
		}
		if ("0000".equals(resMap.get("respcode"))) {
			logger.info("认证成功");
		} else {
			logger.info("认证失败; respcode:{}; respmsg:{}", resMap.get("respcode"), resMap.get("respmsg"));
		}

		authBgRespDef.setRespCode(resMap.get("respcode"));
		authBgRespDef.setRespMsg(resMap.get("respmsg"));

		return authBgRespDef;
	}

	/**
	 * 映射到银联代扣的证件类型编号[ 01:身份证; 02:军官证; 03:护照; 04:回乡证; 05:台胞证; 06:警官证; 07:士兵证;
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

	private Map<String, String> submitUrl(Map<String, String> submitFromData, String requestUrl) {
		String resultString = "";
		logger.info("requestUrl=[{}]", requestUrl);
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 60000, 60000);
		try {
			int status = hc.send(submitFromData, authEncoding);
			if (200 == status) {
				resultString = hc.getResult();
			}
			logger.info("resultString=[{}]", resultString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();

		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = ChinapayUtil.convertResultStringToMap(resultString);

		}
		return resData;
	}

	@Override
	public CpSinCutRespDef sinCut(String orderId, String cardNo, String custId, String bankCode, BigDecimal transAmt,
			String custName, String idNo,IdTypeEnum idType, String description,String merId,String privkey,String pubKey) {
		CpSinCutRespDef cpSinCutRespDef = new CpSinCutRespDef();
		CpSinCutResp resp = null;

		// 登记代扣记录
		String cpOrderId=CmpayUtils.createCPOrderId("10");
		CpCutOrder cutOrder = new CpCutOrder();
		cutOrder.setCutOrderNo(orderId);
		cutOrder.setProcDate(new Date());  //联机日期
		cutOrder.setAmount(transAmt);
		cutOrder.setBankCode(bankCode);
		cutOrder.setCardName(custName);
		cutOrder.setCardNo(cardNo);
		cutOrder.setCardType(CARD_TYPE1);
		cutOrder.setCertId(idNo);
		cutOrder.setCertType(getCpIdType(idType));
		cutOrder.setCreateTime(new Date());
		cutOrder.setCuryId(CURY_RMB);
		cutOrder.setCustId(custId);
		cutOrder.setDescription(description);
		cutOrder.setMerId(merId);
		cutOrder.setTransDate(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		cutOrder.setTransId(cpOrderId);
		cutOrder.setTransStatus(TransStatusDef.U.name());
		cutOrder.setDescription(description);
		cutOrder.setJpaVersion(0);
		cpCutOrderMapper.insert(cutOrder);

		// 准备代扣请求
		CpSinCutReq req = new CpSinCutReq();
		req.setMerId(cutOrder.getMerId());// 固定,银联商户号
		req.setTransDate(cutOrder.getTransDate());
		req.setOrderNo(cutOrder.getTransId()); // getOrderNo根据txnNo格式化
		req.setTransType(TRANS_TYPE); // 固定
		req.setOpenBankId(cutOrder.getBankCode());
		req.setCardType(cutOrder.getCardType()); // 卡号或存折号标识位 (0表示卡,1表示折)
		req.setCardNo(cutOrder.getCardNo());
		req.setUsrName(toUnicode(cutOrder.getCardName()));
		req.setCertType(cutOrder.getCertType());
		req.setCertId(cutOrder.getCertId());
		req.setCuryId(cutOrder.getCuryId()); // 固定
		// TODO 暂时不上送purpose字段，上送会签名失败
//		req.setPurpose(toUnicode(cutOrder.getDescription()));
		req.setPurpose("");
		req.setPriv1("");
		req.setTransAmt(AMT_FORMAT.format(transAmt.multiply(BigDecimal.valueOf(100)))); // 根据transAmt格式化
		req.setVersion(sfjSinCutVersion); // 固定
		req.setGateId(sfjGateId); // 固定

		Map<String, String> resmap = null;
		try {
			resmap = send(sfjSinCutReqUrl, req, true,merId,privkey,pubKey);
			resp = new CpSinCutResp();

			// 把map封装到bean里
			try {
				BeanUtils.populate(resp, resmap);
			} catch (Exception e) {
				logger.error("BeanUtils.populate:{}" + e);
			}
			if (StringUtils.isNotBlank(resp.getMessage())) {
				resp.setMessage(tozhCN(resp.getMessage()));
				logger.info("response message=[{}]", resp.getMessage());
			}
			if (resp != null) {
				try {
					BeanUtils.copyProperties(cpSinCutRespDef, resp);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			//记录返回信息
			cutOrder.setResCode(cpSinCutRespDef.getResponseCode());
			cutOrder.setResTransStat(cpSinCutRespDef.getTransStat());
			if (cpSinCutRespDef.getResponseCode().equals("00")) {
				cutOrder.setTransStatus(TransStatusDef.S.name());
				cpSinCutRespDef.setTransStat(TransStatusDef.S.toString());
			} else if (cpSinCutRespDef.getResponseCode().equals("45") || cpSinCutRespDef.getResponseCode().equals("") || cpSinCutRespDef.getResponseCode().equals("09") || cpSinCutRespDef.getResponseCode().equals("94")) {
				cutOrder.setTransStatus(TransStatusDef.U.name());
				cpSinCutRespDef.setTransStat(TransStatusDef.U.toString());
			} else {
				cutOrder.setTransStatus(TransStatusDef.F.name());
				cpSinCutRespDef.setTransStat(TransStatusDef.F.toString());
			}
		} catch (Exception e) {
			//自定义错误响应码，代表交易出现异常,订单状态为处理中,具体结果交由轮询处理
			cpSinCutRespDef.setResponseCode("99");
			cutOrder.setTransStatus(TransStatusDef.U.name());
			cpSinCutRespDef.setTransStat(TransStatusDef.U.toString());
		}
		String msg="";
		if(StringUtils.isNotBlank(cpSinCutRespDef.getMessage())){
			msg=cpSinCutRespDef.getMessage();
		}
		String message = "银联响应码:["+cpSinCutRespDef.getResponseCode()+"],响应信息:"+msg;
		cpSinCutRespDef.setMessage(message);
		cpSinCutRespDef.setCutOrderNo(cutOrder.getCutOrderNo());
		cpSinCutRespDef.setThirdPartyOrderNo(req.getOrderNo());

		cpCutOrderMapper.updateByPrimaryKeySelective(cutOrder);

		return cpSinCutRespDef;
	}


	/**
	 * 格式化为银联的订单号格式
	 *
	 * @param txnNo
	 * @return
	 */
	private String formatOrderNo(Integer txnNo) {
		// ---- FOR TEST --- BEGIN --- 测试用，需删除 ！ 为避免测试时，银联报[重复订单号]，用随机数做订单号
//		 txnNo = randomOrderNo();
		// ---- FOR TEST --- END

		String orderNo = StringUtils.leftPad(String.valueOf(txnNo), 16, '0');
		logger.info("订单号=[{}]", orderNo);
		return orderNo;
	}

	/**
	 * 将字符串转换为Unicode码
	 *
	 * @param zhStr
	 * @return
	 */
	private String toUnicode(String zhStr) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < zhStr.length(); i++) {
			char c = zhStr.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		logger.debug(unicode.toString());
		logger.debug(zhStr + "转换为unicode码成功！");
		return unicode.toString();
	}

	/**
	 * 发报文
	 *
	 * @param url
	 * @param req
	 * @param orgDataWithChkValueFlag
	 *            验签时原字符串是否包含[chkValue=]
	 * @return
	 */
	private Map<String, String> send(String url, ChinapaySfjReq req, boolean orgDataWithChkValueFlag,String merId,String privkey,String pubKey) {
		// 初始化key文件：
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		boolean flag1;
		String msg;
		// 将要签名的数据传给msg
		msg = req.getString4Chk();
		logger.info("要签名的数据:" + msg);

		// 对签名的数据进行Base64编码
		String msg1 = "";
		try {
			msg1 = new String(Base64.encode(msg.toString().getBytes(sfjEncoding)));
		} catch (UnsupportedEncodingException e) {
			logger.error("Base64 encode Error:{}" + e);
		}
		logger.debug("要签名的数据进行Base64编码后为:" + msg1);
		try {
			flag1 = key.buildKey(merId, 0, privkey);
			 logger.debug("merPrk文件绝对路径:{},商户号:{}",privkey,merId);
		} catch (Exception e1) {
			logger.error("build key error!", e1);
			return null;
		}
		if (flag1 == false) {
			logger.error("build key error!--");
			return null;
		}

		// 签名
		SecureLink s = new SecureLink(key);

		String chkValue = s.Sign(msg1);

		logger.debug("签名内容:" + chkValue);

		req.setChkValue(chkValue);
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			// reqMap = BeanUtils.describe(req);
			reqMap = req.convertToMap();
		} catch (Exception e) {
			logger.error("BeanUtils.describe:{}" + e);
		}

		Map<String, String> resmap = submitUrl(reqMap, url, orgDataWithChkValueFlag,pubKey); // 如果所有字段都出现,用reqMap替换req.convertToMap()

		logger.debug("resmap内容:" + resmap);

		return resmap;
	}

	/**
	 * HTTP提交
	 *
	 * @param submitFromData
	 * @param requestUrl
	 * @param orgDataWithChkValueFlag
	 *            验签是原字符串是否包含[chkValue=]
	 * @return
	 */
	private Map<String, String> submitUrl(Map<String, String> submitFromData, String requestUrl, boolean orgDataWithChkValueFlag,String pubKey) {
		String resultString = "";
		logger.info("requestUrl=[" + requestUrl + "]");
		logger.info("requestMap=[" + submitFromData + "]");
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, sfjEncoding);
//			int status = hc.send(submitFromData, "GBK");
			if (200 == status) {
				resultString = hc.getResult();
				logger.info("responseString=[" + resultString + "]");
			}
		} catch (Exception e) {
			logger.error("hc.send err,{}", e);
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = ChinapayUtil.convertResultStringToMap(resultString);
			if (sfjValidate(resultString, orgDataWithChkValueFlag,pubKey)) {
				logger.debug("验证签名成功");
			} else {
				logger.error("验证签名失败,resultString:{}" + resultString);
			}
		}
		return resData;
	}

	/**
	 * 验证签名
	 *
	 * @param resMes
	 * @param orgDataWithChkValueFlag
	 *            验签是原字符串是否包含[chkValue=]
	 * @return
	 */
	private boolean sfjValidate(String resMes, boolean orgDataWithChkValueFlag,String pubKey) {
		int dex = resMes.lastIndexOf("=");
		String orgStr;
		if (orgDataWithChkValueFlag) { // 代扣交易的验签,结尾包括[&chkValue=],等号后面是空
			orgStr = resMes.substring(0, dex + 1);
		} else { // 代付交易的验签，不包含结尾的[&chkValue=......]
			int dex2 = resMes.lastIndexOf("&");
			orgStr = resMes.substring(0, dex2);
		}

		String strBase64 = "";
		try {
			strBase64 = new String(Base64.encode(orgStr.getBytes(sfjEncoding)));
		} catch (UnsupportedEncodingException e) {
			logger.info("(Base64 encode Error:{}" + e);
		}

		// 对收到的ChinaPay应答传回的域段进行验签
		boolean buildOK = false;
		boolean res = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			buildOK = key.buildKey(pubId, KeyUsage, pubKey);
			// logger.info("pgPubk文件绝对路径"+pgPubk.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.info("bulidKey Error:{}" + e);
		}
		if (!buildOK) {
			// errorList.add("build error!");
			logger.info("bulidKey Error");
			return false;
		}
		// if (!errorList.isEmpty()) {
		// request.setAttribute("errors", errorList);
		// return;
		// }

		SecureLink sl = new SecureLink(key);
		String chkValue = resMes.substring(dex + 1);
		res = sl.verifyAuthToken(strBase64, chkValue);
		return res;
	}

	/**
	 * 将Unicode码转换为中文
	 *
	 * @param unicode
	 * @return
	 */
	private String tozhCN(String unicode) {
		StringBuffer gbk = new StringBuffer();
		String hex[] = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) { // 注意要从 1 开始，而不是从0开始。第一个是空。
			int data = Integer.parseInt(hex[i], 16); // 将16进制数转换为 10进制的数据。
			gbk.append((char) data); // 强制转换为char类型就是我们的中文字符了。
		}
		logger.debug("这是从 Unicode编码 转换为 中文字符: " + gbk.toString());
		return gbk.toString();
	}

	/**
	 * chinapay支付捷 - 单笔代扣交易查询 transDate 固定格式'yyyyMMdd'
	 *
	 * @return
	 */
	@Override
	public CpSinCutQueryRespDef sinCutQuery(String orderNo,String merId,String privkey,String pubKey) {
		CpSinCutQueryRespDef cpSinCutQueryRespDef = new CpSinCutQueryRespDef();
		CpCutOrder cutOrder=cpCutOrderMapper.selectByPrimaryKey(orderNo);
		if(cutOrder==null){
			logger.info("cp表无此交易");
			return null;
		}

		// 组装查询交易
		CpSinCutQueryReq req = new CpSinCutQueryReq();
		req.setMerId(merId);
		req.setTransType("0003");
//		req.setOrderNo(formatOrderNo(Integer.valueOf(orderNo)));
		req.setOrderNo(cutOrder.getTransId());
		req.setTransDate(cutOrder.getTransDate());
		req.setVersion(sfjSinCutVersion);
		req.setPriv1("");

		CpSinCutQueryResp cpSinCutQueryResp = sendQuerySinCut(sfjSinCutQueryReqUrl, req,merId,privkey,pubKey);
		cutOrder.setResCode(cpSinCutQueryResp.getResponseCode());
		cutOrder.setResTransStat(cpSinCutQueryResp.getTransStat());
		if (cpSinCutQueryResp.getResponseCode().equals("00")) {// 更新订单状态
			cutOrder.setTransStatus(TransStatusDef.S.name());
		} else if (cpSinCutQueryResp.getResponseCode().equals("45") || cpSinCutQueryResp.getResponseCode().equals("") || cpSinCutQueryResp.getResponseCode().equals("09")) {
			cutOrder.setTransStatus(TransStatusDef.U.name());
		} else {
			cutOrder.setTransStatus(TransStatusDef.F.name());
		}
		if (cpSinCutQueryResp != null) {
			try {
				BeanUtils.copyProperties(cpSinCutQueryRespDef, cpSinCutQueryResp);
				if (StringUtils.isNotBlank(cpSinCutQueryRespDef.getMessage())) {
					cpSinCutQueryRespDef.setMessage(tozhCN(cpSinCutQueryRespDef.getMessage()));
					logger.info("response message=[{}]", cpSinCutQueryRespDef.getMessage());
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		cpCutOrderMapper.updateByPrimaryKeySelective(cutOrder);

		return cpSinCutQueryRespDef;

	}

	public CpSinCutQueryResp sendQuerySinCut(String url, CpSinCutQueryReq req,String merId,String prvKey,String pubKey) {
		// 初始化key文件：
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		boolean flag1;
		String msg;
		// 将要签名的数据传给msg
		msg = req.getString4Chk();
		logger.debug("要签名的数据：" + msg);
		// 对签名的数据进行Base64编码
		String msg1 = "";
		try {
			msg1 = new String(Base64.encode(msg.toString().getBytes(sfjEncoding)));
		} catch (UnsupportedEncodingException e) {
			logger.error("Base64 encode Error:{}" + e);
		}
		logger.debug("要签名的数据进行Base64编码后为:" + msg1);
		try {
			flag1 = key.buildKey(merId, 0, prvKey);
			// logger.info("merPrK据对路径"+merPrK.getFile().getAbsolutePath());
		} catch (Exception e1) {
			logger.error("build key error!", e1);
			return null;
		}
		if (flag1 == false) {
			logger.error("build key error!");
			return null;
		}
		// 签名
		SecureLink s = new SecureLink(key);

		String chkValue = s.Sign(msg1);

		logger.debug("签名内容:" + chkValue);

		req.setChkValue(chkValue);
		Map<String, String> reqMap = new HashMap<String, String>();
		try {
			// reqMap = BeanUtils.describe(req);
			reqMap = req.convertToMap();
		} catch (Exception e) {
			logger.error("BeanUtils.describe:{}" + e);
		}
		CpSinCutQueryResp res = submitUrlQuerySinCut(reqMap, url,pubKey);
		return res;
	}

	private CpSinCutQueryResp submitUrlQuerySinCut(Map<String, String> submitFromData, String requestUrl,String pubKey) {
		CpSinCutQueryResp cpSinCutQueryResp = new CpSinCutQueryResp();
		String resultString = "";
		logger.info("requestUrl=[" + requestUrl + "]");
		logger.info("requestMap=[" + submitFromData + "]");
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, sfjEncoding);
			if (200 == status) {
				resultString = hc.getResult();
				logger.info("responseString=[" + resultString + "]");
			}
		} catch (Exception e) {
			logger.error("hc.send err,{}", e);
		}
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = ChinapayUtil.convertResultStringToMap(resultString);
			if (sfjValidate(resultString, true,pubKey)) {
				logger.debug("验证签名成功");
			} else {
				logger.error("验证签名失败,resultString:{}" + resultString);
			}
		}
		// 把map封装到bean里
		try {
			BeanUtils.populate(cpSinCutQueryResp, resData);
		} catch (Exception e) {
			logger.error("BeanUtils.populate:{}" + e);
		}
		return cpSinCutQueryResp;
	}



	@Override
	public CpSinPayRespDef sinPay(String payOrderNo, String transId, String cardNo, String custId, String bankId,String bankName,
			BigDecimal transAmt, String purpose, String custName, String province, String city, String subBank,
			String merId) {

		CpSinPayRespDef cpSinPayRespDef = new CpSinPayRespDef();
		// 1.创建银联代付订单 10代扣，11代付
		String cpOrderId=CmpayUtils.createCPOrderId("11");
		CpPayOrder payOrder = new CpPayOrder();
		payOrder.setPayOrderNo(payOrderNo);
		payOrder.setProcDate(new Date());
		payOrder.setAmount(transAmt);
		payOrder.setBankCode(bankId);
		payOrder.setCardName(custName);
		payOrder.setCardNo(cardNo);
		payOrder.setCardType("");
		payOrder.setCertId("");
		payOrder.setCertType("");
		payOrder.setCity(city);
		payOrder.setCreateTime(new Date());
		payOrder.setCuryId("");
		payOrder.setCustId(custId);
		payOrder.setDescription(purpose);
		payOrder.setFlag(PRIVATE_FLAG);
		payOrder.setMerId(merId);
		payOrder.setProv(province);
		payOrder.setSubBank(subBank);
		payOrder.setTransDate(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		payOrder.setTransId(cpOrderId);
		payOrder.setTransStatus(TransStatusDef.U.toString());
		payOrder.setJpaVersion((long)0);
		cpPayOrderMapper.insert(payOrder);

		//2.组装请求报文
		// ---- 封装请求
		CpSinPayReq req = new CpSinPayReq();
		req.setMerId(payOrder.getMerId());//req.setMerId("808080211389034");
		req.setMerDate(payOrder.getTransDate());//req.setMerDate("20151019");
		req.setMerSeqId(payOrder.getTransId());//req.setMerSeqId("0000000000123454");
		req.setCardNo(cardNo);//req.setCardNo("6226620607792207");
		req.setUsrName(custName);//req.setUsrName(toUnicode("刘敏"));
		//FIXME   bankId应该对应的是银行全称
		req.setOpenBank(bankName);//req.setOpenBank(toUnicode("光大银行"));
		//FIXME   province连连支付中为省编码，在银联为全称
		if(StringUtils.isBlank(province)){
			province="上海市";
		}
		req.setProv(province);//req.setProv(toUnicode("湖北"));
		//FIXME   city连连支付中为省编码，在银联为全称
		if(StringUtils.isBlank(city)){
			city="黄浦区";
		}
		req.setCity(city);//req.setCity(toUnicode("上海市"));
		req.setTransAmt(AMT_FORMAT.format(transAmt.multiply(new BigDecimal(100)))); // 根据transAmt格式化
		req.setPurpose(toUnicode(purpose));//req.setPurpose(toUnicode("测试代付"));  可空
		//req.setSubBank(toUnicode(subBank));//req.setSubBank(toUnicode("光大银行宜昌支行"));
		// FIXME 若走到”交行大小额“和”人行大小额“通道上，是会验省市的，故贵司的省市字段要传正确，且支行字段也要传正确
		req.setSubBank("");
		req.setFlag(PRIVATE_FLAG);//req.setFlag("00");
		req.setVersion(chinaPaySinPay.getPay_sfjSinPayVersion());//req.setVersion("20090501");
		req.setSignFlag("1");//固定
		//获得签名
		String chkValue = getCpSinPayCheckValue(req);
		req.setChkValue(chkValue);
		//发送请求
		String resMes = null;
		try {
			resMes = sendCpSinPay1(req);

			//解析结果并验签
			cpSinPayRespDef = praseCpSinPayRes(resMes,payOrder.getMerId());
			//记录订单信息
			payOrder.setResCode(cpSinPayRespDef.getResponseCode());
			payOrder.setResStat(cpSinPayRespDef.getStat());
			payOrder.setCpSeqid(cpSinPayRespDef.getCpSeqId());
			payOrder.setCpDate(cpSinPayRespDef.getCpDate());
			//订单状态更新
			if(cpSinPayRespDef.getResponseCode().equals("0000")){//交易接收成功
				if(cpSinPayRespDef.getStat().equals("s")){//代付成功
					payOrder.setTransStatus(TransStatusDef.S.toString());
					cpSinPayRespDef.setTransStat(TransStatusDef.S.toString());
				} else if (cpSinPayRespDef.getStat().equals("9") || cpSinPayRespDef.getStat().equals("6")) {
					payOrder.setTransStatus(TransStatusDef.F.toString());
					cpSinPayRespDef.setTransStat(TransStatusDef.F.toString());
				} else {
					payOrder.setTransStatus(TransStatusDef.U.toString());
					cpSinPayRespDef.setTransStat(TransStatusDef.U.toString());
				}
			}
			//临时方案，修正银联处理状态
			else if(StringUtils.equals(cpSinPayRespDef.getResponseCode(), "0100") || StringUtils.equals(cpSinPayRespDef.getResponseCode(), "0101")
					|| StringUtils.equals(cpSinPayRespDef.getResponseCode(), "0102") || StringUtils.equals(cpSinPayRespDef.getResponseCode(), "0103") || StringUtils.equals(cpSinPayRespDef.getResponseCode(), "0104")){
				payOrder.setTransStatus(TransStatusDef.F.toString());
				cpSinPayRespDef.setTransStat(TransStatusDef.F.toString());
			}else{
				payOrder.setTransStatus(TransStatusDef.U.toString());
				cpSinPayRespDef.setTransStat(TransStatusDef.U.toString());
			}


		} catch (Exception e) {
			logger.error("银联代付出现异常！{}",e);
			payOrder.setTransStatus(TransStatusDef.U.toString());
			cpSinPayRespDef.setTransStat(TransStatusDef.Q.toString());
		}

		cpPayOrderMapper.updateByPrimaryKeySelective(payOrder);

		String message = "银联响应码:["+cpSinPayRespDef.getResponseCode()+"],响应信息:"+cpSinPayRespDef.getRespMsg();
		cpSinPayRespDef.setRespMsg(message);
		cpSinPayRespDef.setThirdPartOrderNo(req.getMerSeqId());
		return cpSinPayRespDef;

	}

	@Override
	public CpSinPayQueryRespDef sinPayQuery(String orderNo, String merId) {
		//1.查代付订单
		CpPayOrder payOrder=cpPayOrderMapper.selectByPrimaryKey(orderNo);
		if(payOrder==null){
			logger.info("订单号【{}】无代付订单记录",orderNo);
			return null;
		}
		CpSinPayQueryRespDef cpSinPayQueryRespDef = new CpSinPayQueryRespDef();
		//封装查询请求
		CpSinPayQueryReq req = new CpSinPayQueryReq();
		req.setMerId(payOrder.getMerId());// 固定
		req.setMerDate(payOrder.getTransDate());
		req.setMerSeqId(payOrder.getTransId());
		req.setVersion(chinaPaySinPay.getPay_sfjSinPayVersion());
		req.setSignFlag("1");// 固定
		//获得签名值
		String chkValue = getCpSinPayQueryCheckValue(req);
		req.setChkValue(chkValue);
		//发送请求
				String resMes = null;
				try {
					resMes = sendCpSinPayQuery(req);
				} catch (Exception e) {
					logger.error("发送请求失败！{}",e);
				}
				//解析结果并验签
				cpSinPayQueryRespDef = praseCpSinPayQueryRes(resMes,payOrder.getMerId());
				//记录订单信息
				payOrder.setResStat(cpSinPayQueryRespDef.getStat());
				//订单状态更新
				if(cpSinPayQueryRespDef.getCode().equals("000")){//查询交易成功
					if(cpSinPayQueryRespDef.getStat().equals("s")){
						payOrder.setTransStatus(TransStatusDef.S.toString());
					}else if(cpSinPayQueryRespDef.getStat().equals("6") || cpSinPayQueryRespDef.getStat().equals("9")){
						payOrder.setTransStatus(TransStatusDef.F.toString());
					}else{
						payOrder.setTransStatus(TransStatusDef.U.toString());
					}
				}else if(cpSinPayQueryRespDef.getCode().equals("001")){
					logger.info("银联查询无此代付订单，[银联代付订单号={}]", payOrder.getPayOrderNo());
				}else{
					logger.info("银联查询代付订单出错.");
				}

				cpPayOrderMapper.updateByPrimaryKeySelective(payOrder);

				return cpSinPayQueryRespDef;

	}

	/**
	 * 发送单笔代付查询请求报文
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public String sendCpSinPayQuery(CpSinPayQueryReq req) throws Exception{
		logger.info("代付查询报文:"+req.toString());
		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, chinaPaySinPay.getPay_sfjEncoding());
		PostMethod postMethod = new PostMethod(chinaPaySinPay.getPay_sfjSinPayQueryReqUrl());
		//填入各个表单域的值
		NameValuePair[] data = {
				new NameValuePair("merId", req.getMerId()),
				new NameValuePair("merDate", req.getMerDate()),
				new NameValuePair("merSeqId", req.getMerSeqId()),
		 		new NameValuePair("version", req.getVersion()),
				new NameValuePair("chkValue",req.getChkValue()),
				new NameValuePair("signFlag", "1")
		};

		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} catch (Exception e) {
			logger.error("发送代付请求异常,{}", e);
		}
		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 处理内容
//		BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
		BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream,"GBK"));

		String tempBf = null;
		StringBuffer html=new StringBuffer();
		while((tempBf = reader.readLine()) != null){

			html.append(tempBf);
		}
		String resMes = html.toString();
		logger.info("代付查询返回报文：" + resMes);
		return resMes;
	}

	/**
	 * 获得代付查询签名值
	 * @param req
	 * @return
	 */
	public String getCpSinPayQueryCheckValue(CpSinPayQueryReq req){
		String Data = req.getString4Chk();
		String plainData = "";
		try {
			plainData = new String(Base64.encode(Data.getBytes("GBK")));
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("转换成Base64后数据："+plainData);
		//签名
		String chkValue = null;
		int KeyUsage = 0;
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		boolean flag = false;
		try {
			flag = key.buildKey(req.getMerId(), 0, chinaPaySinPay.getPay_merPrK()+req.getMerId()+"_MerPrK.key");
		} catch (Exception e) {
			logger.error("build key error!{}",e);
			return null;
		}
		if(flag == false){
			logger.error("build key error!");
			return null;
		}else{
			logger.debug("build key success!");
		}
		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(plainData);
		logger.debug("签名内容："+chkValue);
		return chkValue;
	}

	/**
	 * 获得单笔代付签名值
	 * @param req
	 * @return
	 */
	public String getCpSinPayCheckValue(CpSinPayReq req){
		String Data = req.getString4Chk();
		String plainData = "";
		try {
			plainData = new String(Base64.encode(Data.getBytes("GBK")));
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		logger.info("转换成Base64后数据：" + plainData);
		String chkValue = null;
		// 初始化key文件：
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		boolean flag = false;
		try {
			logger.info("key path:{}",chinaPaySinPay.getPay_merPrK()+req.getMerId()+"_MerPrk.key");
			flag = key.buildKey(req.getMerId(), 0, chinaPaySinPay.getPay_merPrK()+req.getMerId()+"_MerPrK.key");
		} catch (Exception e) {
			logger.error("build key error!{}", e);
			return null;
		}
		if(flag == false){
			logger.info("build key error!");
			return null;
		}else{
			logger.info("build key success!");
		}
		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(plainData);
		logger.debug("签名内容:"+ chkValue);
		return chkValue;
	}

	/**
	 * 发送单笔代付请求报文,加超时
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public String sendCpSinPay1(CpSinPayReq req) throws Exception,IOException{
		logger.info("代付请求报文:"+req.toString());
		//连接Chinapay控台
		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, chinaPaySinPay.getPay_sfjEncoding());
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(300000);//链接超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(360000);//读取超时
		PostMethod postMethod = new PostMethod(chinaPaySinPay.getPay_sfjSinPayReqUrl());
		//填入各个表单域的值
		NameValuePair[] data = {
				new NameValuePair("merId", req.getMerId()),
				new NameValuePair("merDate", req.getMerDate()),
				new NameValuePair("merSeqId", req.getMerSeqId()),
				new NameValuePair("cardNo", req.getCardNo()),
				new NameValuePair("usrName", req.getUsrName()),
				new NameValuePair("openBank", req.getOpenBank()),
				new NameValuePair("prov", req.getProv()),
				new NameValuePair("city", req.getCity()),
				new NameValuePair("transAmt", req.getTransAmt()),
				new NameValuePair("purpose", req.getPurpose()),
				new NameValuePair("subBank", req.getSubBank()),
				new NameValuePair("flag", req.getFlag()),
				new NameValuePair("version", req.getVersion()),
				new NameValuePair("chkValue",req.getChkValue()),
				new NameValuePair("signFlag", req.getSignFlag())
		};
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} catch (Exception e) {
			logger.error("发送代付请求异常,{}", e);
		}
		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 处理内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
		String tempBf = null;
		StringBuffer html=new StringBuffer();
		while((tempBf = reader.readLine()) != null){

			html.append(tempBf);
		}
		String resMes = html.toString();
		logger.info("代付交易返回报文：" + resMes);
		return resMes;
	}

	/**
	 * 解析代付请求结果报文并验签
	 * @param resMes
	 * @return
	 */
	public CpSinPayRespDef praseCpSinPayRes(String resMes,String merId){
		CpSinPayRespDef cpSinPayRespDef = new CpSinPayRespDef();
		int dex = resMes.lastIndexOf("&");
		//拆分页面应答数据
		String str[] = resMes.split("&");
		//提取返回数据
		if(str.length == 10){//返回成功
			int Res_Code = str[0].indexOf("=");
			int Res_merId = str[1].indexOf("=");
			int Res_merDate = str[2].indexOf("=");
			int Res_merSeqId = str[3].indexOf("=");
			int Res_cpDate = str[4].indexOf("=");
			int Res_cpSeqId = str[5].indexOf("=");
			int Res_transAmt = str[6].indexOf("=");
			int Res_stat = str[7].indexOf("=");
			int Res_cardNo = str[8].indexOf("=");
			int Res_chkValue = str[9].indexOf("=");

			String responseCode = str[0].substring(Res_Code+1);
			cpSinPayRespDef.setResponseCode(responseCode);
			String MerId = str[1].substring(Res_merId+1);
			String MerDate = str[2].substring(Res_merDate+1);
			String MerSeqId = str[3].substring(Res_merSeqId+1);
//			cpSinPayRespDef.setPayOrderNo(MerSeqId);
			String CpDate = str[4].substring(Res_cpDate+1);
			cpSinPayRespDef.setCpDate(CpDate);
			String CpSeqId = str[5].substring(Res_cpSeqId+1);
			cpSinPayRespDef.setCpSeqId(CpSeqId);
			String TransAmt = str[6].substring(Res_transAmt+1);
			String Stat = str[7].substring(Res_stat+1);
			cpSinPayRespDef.setStat(Stat);
			String CardNo = str[8].substring(Res_cardNo+1);
			String ChkValue = str[9].substring(Res_chkValue+1);
			String msg = resMes.substring(0,dex);
			String plainData1 = new String(Base64.encode(msg.getBytes()));
			//对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			chinapay.PrivateKey key1 = new chinapay.PrivateKey();
			try {
				buildOK = key1.buildKey(chinaPaySinPay.getPay_pubId(), KeyUsage, chinaPaySinPay.getPay_pgPubk()+merId+"_PgPub.key");
			} catch (Exception e) {
				logger.error("build key error,{}", e);
			}
			if (!buildOK) {
				logger.error("build key error!");
			}else{
				logger.debug("build key success!");
			}
			SecureLink sl1 = new SecureLink(key1);
			res=sl1.verifyAuthToken(plainData1,ChkValue);
			if (res){
				logger.debug("验签数据正确!");
			}else {
				logger.error("签名数据不匹配,签名数据:{}"+msg);
			}
			return cpSinPayRespDef;
		}
		if(str.length == 2){//交易失败应答
			int Res_Code = str[0].indexOf("=");
			int Res_chkValue = str[1].indexOf("=");

			String responseCode = str[0].substring(Res_Code+1);
			String ChkValue = str[1].substring(Res_chkValue+1);
			cpSinPayRespDef.setResponseCode(responseCode);

			String plainData2 = str[0];
			String plainData3 = new String(Base64.encode(plainData2.getBytes()));

			//对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			chinapay.PrivateKey key2 = new chinapay.PrivateKey();
			try {
				buildOK = key2.buildKey(chinaPaySinPay.getPay_pubId(), KeyUsage,chinaPaySinPay.getPay_pgPubk()+merId+"_PgPub.key");
			} catch (Exception e) {
				logger.error("build key error,{}", e);
			}
			if (!buildOK) {
				logger.error("build key error!");
			}else{
				logger.debug("build key success!");
			}
			SecureLink sl2 = new SecureLink(key2);
			res=sl2.verifyAuthToken(plainData3,ChkValue);
			if (res){
				logger.debug("验签数据正确!");
			}else {
				logger.error("签名数据不匹配,签名数据:{}"+plainData2);
			}
			return cpSinPayRespDef;
		}
		return cpSinPayRespDef;
	}

	/**
	 *  解析代付查询请求结果报文并验签
	 * @param resMes
	 * @return
	 */
	public CpSinPayQueryRespDef praseCpSinPayQueryRes(String resMes,String merId){
		CpSinPayQueryRespDef cpSinPayQueryRespDef = new CpSinPayQueryRespDef();
		int dex = resMes.lastIndexOf("|");
		String Res_Code = resMes.substring(0,3);
		cpSinPayQueryRespDef.setCode(Res_Code);
		//提取返回数据
		if(Res_Code.equals("000")){
			String nResMes = resMes.substring(0,dex);
			int nDex = nResMes.lastIndexOf("|");
			//String Res_stat = resMes.substring(dex-2,dex-1);
			String Res_stat = nResMes.substring(nDex-1,nDex);
			String Res_chkValue = resMes.substring(dex+1);
			cpSinPayQueryRespDef.setStat(Res_stat);
			cpSinPayQueryRespDef.setChkValue(Res_chkValue);
			String plainData = resMes.substring(0,dex+1);
			String Data = "";
			try {
				Data = new String(Base64.encode(plainData.getBytes("GBK")));
			} catch (Exception e) {
				logger.error("签名编码转换失败:"+e.getMessage());
				e.printStackTrace();
			}
			//对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			chinapay.PrivateKey key1 = new chinapay.PrivateKey();
			try {
				buildOK = key1.buildKey(chinaPaySinPay.getPay_pubId(), KeyUsage, chinaPaySinPay.getPay_pgPubk()+merId+"_PgPub.key");
			} catch (Exception e) {
				logger.error("build key error,{}", e);
			}
			if (!buildOK) {
				logger.error("build key error!");
			}else{
				logger.debug("build key success!");
			}
			SecureLink sl1 = new SecureLink(key1);
			res=sl1.verifyAuthToken(Data,Res_chkValue);
			if (res){
				logger.debug("验签数据正确!");
			}else {
				logger.error("签名数据不匹配,签名数据:{}"+Data);
			}
//			logger.info("cpSinPayQueryRespDef----------{}",cpSinPayQueryRespDef.toString());
			return cpSinPayQueryRespDef;
		}else{
			String Res_chkValue = resMes.substring(dex+1);
			cpSinPayQueryRespDef.setChkValue(Res_chkValue);
			String plainData = resMes.substring(0,dex+1);
			String Data = "";
			try {
				Data = new String(Base64.encode(plainData.getBytes("GBK")));
			} catch (Exception e) {
				logger.error("签名编码转换失败:"+e.getMessage());
				e.printStackTrace();
			}
			//对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			chinapay.PrivateKey key2 = new chinapay.PrivateKey();
			try {
				buildOK = key2.buildKey(chinaPaySinPay.getPay_pubId(), KeyUsage, chinaPaySinPay.getPay_pgPubk()+merId+"_PgPub.key");
			} catch (Exception e) {
				logger.error("build key error,{}", e);
			}
			if (!buildOK) {
				logger.error("build key error!");
			}else{
				logger.debug("build key success!");
			}
			SecureLink sl2 = new SecureLink(key2);
			res=sl2.verifyAuthToken(Data,Res_chkValue);
			if (res){
				logger.debug("验签数据正确!");
			}else {
				logger.error("签名数据不匹配,签名数据:{}"+Data);
			}
			return cpSinPayQueryRespDef;
		}
	}


}
