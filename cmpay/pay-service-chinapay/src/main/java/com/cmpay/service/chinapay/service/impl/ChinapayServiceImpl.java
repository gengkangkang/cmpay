package com.cmpay.service.chinapay.service.impl;

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
import com.cmpay.service.chinapay.dao.CpCutOrderMapper;
import com.cmpay.service.chinapay.model.ChinapaySfjReq;
import com.cmpay.service.chinapay.model.CpAuthBgRespDef;
import com.cmpay.service.chinapay.model.CpCutOrder;
import com.cmpay.service.chinapay.model.CpSinCutReq;
import com.cmpay.service.chinapay.model.CpSinCutResp;
import com.cmpay.service.chinapay.model.CpSinCutRespDef;
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

	@Value("#{env['pubId']}")
	private String pubId;

	@Autowired
	CpCutOrderMapper cpCutOrderMapper;

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
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
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
			} else if (cpSinCutRespDef.getResponseCode().equals("45") || cpSinCutRespDef.getResponseCode().equals("") || cpSinCutRespDef.getResponseCode().equals("09")) {
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

}
