package com.cmpay.service.chinapay.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

//import com.unionpay.acp.sdk.CertUtil;
//import com.unionpay.acp.sdk.LogUtil;

public class ChinapayUtil {

	protected static char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z' };
	protected static final Random random = new Random();
	public static String encoding = "GBK";

	public static String send(String url, Map<String, String> data,
			String encoding, int connectionTimeout, int readTimeout) {
		HttpClient hc = new HttpClient(url, connectionTimeout, readTimeout);
		String res = "";
		try {
			int status = hc.send(data, encoding);
			if (200 == status) {
				res = hc.getResult();
			}
		} catch (Exception e) {
			// // LogUtil.writeErrorLog("通信异常", e);
		}
		return res;
	}

	// public static boolean sign(Map<String, String> data, String encoding) {
	// if (isEmpty(encoding)) {
	// encoding = "UTF-8";
	// }
	// // data.put("certId", CertUtil.getSignCertId());
	//
	// String stringData = coverMap2String(data);
	//
	// byte[] byteSign = null;
	// String stringSign = null;
	// try {
	// byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
	// byteSign =
	// SecureUtil.base64Encode(SecureUtil.signBySoft(CertUtil.getSignCertPrivateKey(),
	// signDigest));
	//
	// stringSign = new String(byteSign);
	//
	// data.put("signature", stringSign);
	// return true;
	// } catch (Exception e) {
	// // LogUtil.writeErrorLog("签名异常", e);
	// }
	// return false;
	// }

	// public static boolean signByCertInfo(Map<String, String> data, String
	// encoding, String certPath, String certPwd) {
	// if (isEmpty(encoding)) {
	// encoding = "UTF-8";
	// }
	// if ((isEmpty(certPath)) || (isEmpty(certPwd))) {
	// // LogUtil.writeLog("Invalid Parameter:CertPath=[" + certPath +
	// "],CertPwd=[" + certPwd + "]");
	//
	// return false;
	// }
	// data.put("certId", CertUtil.getCertIdByThreadLocal(certPath, certPwd));
	//
	// String stringData = coverMap2String(data);
	//
	// byte[] byteSign = null;
	// String stringSign = null;
	// try {
	// byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
	// byteSign =
	// SecureUtil.base64Encode(SecureUtil.signBySoft(CertUtil.getSignCertPrivateKeyByThreadLocal(certPath,
	// certPwd), signDigest));
	//
	// stringSign = new String(byteSign);
	//
	// data.put("signature", stringSign);
	// return true;
	// } catch (Exception e) {
	// // LogUtil.writeErrorLog("签名异常", e);
	// }
	// return false;
	// }

	// public static boolean validate(Map<String, String> resData, String
	// encoding) {
	// // LogUtil.writeLog("验签处理开始.");
	// if (isEmpty(encoding)) {
	// encoding = "UTF-8";
	// }
	// String stringSign = (String) resData.get("signature");
	//
	// String certId = (String) resData.get("certId");
	//
	// String stringData = coverMap2String(resData);
	// try {
	// return SecureUtil.validateSignBySoft(CertUtil.getValidateKey(certId),
	// SecureUtil.base64Decode(stringSign.getBytes(encoding)),
	// SecureUtil.sha1X16(stringData, encoding));
	// } catch (UnsupportedEncodingException e) {
	// // LogUtil.writeErrorLog(e.getMessage(), e);
	// } catch (Exception e) {
	// // LogUtil.writeErrorLog(e.getMessage(), e);
	// }
	// return false;
	// }

	public static String coverMap2String(Map<String, String> data) {
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			if (!"signature".equals(en.getKey().trim())) {
				tree.put(en.getKey(), en.getValue());
			}
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			sf.append(en.getKey() + "=" + en.getValue() + "&");
		}
		return sf.substring(0, sf.length() - 1);
	}

	public static Map<String, String> coverResultString2Map(String result) {
		return convertResultStringToMap(result);
	}

	public static Map<String, String> convertResultStringToMap(String result) {
		Map<String, String> map = null;
		try {
			if (StringUtils.isNotBlank(result)) {
				if ((result.startsWith("{")) && (result.endsWith("}"))) {
					System.out.println(result.length());
					result = result.substring(1, result.length() - 1);
				}
				map = parseQString(result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> parseQString(String str)
			throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();

		String key = null;
		boolean isKey = true;
		boolean isOpen = false;
		char openName = '\000';
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				char curChar = str.charAt(i);
				if (isKey) {
					if (curChar == '=') {
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else {
					if (isOpen) {
						if (curChar == openName) {
							isOpen = false;
						}
					} else {
						if (curChar == '{') {
							isOpen = true;
							openName = '}';
						}
						if (curChar == '[') {
							isOpen = true;
							openName = ']';
						}
					}
					if ((curChar == '&') && (!isOpen)) {
						putKeyValueToMap(temp, isKey, key, map);
						temp.setLength(0);
						isKey = true;
					} else {
						temp.append(curChar);
					}
				}
			}
			putKeyValueToMap(temp, isKey, key, map);
		}
		return map;
	}

	private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
			String key, Map<String, String> map)
			throws UnsupportedEncodingException {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}

	// public static String encryptPin(String card, String pwd, String encoding)
	// {
	// return SecureUtil.EncryptPin(pwd, card, encoding,
	// CertUtil.getEncryptCertPublicKey());
	// }
	//
	// public static String encryptCvn2(String cvn2, String encoding) {
	// return SecureUtil.EncryptData(cvn2, encoding,
	// CertUtil.getEncryptCertPublicKey());
	// }
	//
	// public static String decryptCvn2(String base64cvn2, String encoding) {
	// return SecureUtil.DecryptedData(base64cvn2, encoding,
	// CertUtil.getSignCertPrivateKey());
	// }
	//
	// public static String encryptAvailable(String date, String encoding) {
	// return SecureUtil.EncryptData(date, encoding,
	// CertUtil.getEncryptCertPublicKey());
	// }
	//
	// public static String decryptAvailable(String base64Date, String encoding)
	// {
	// return SecureUtil.DecryptedData(base64Date, encoding,
	// CertUtil.getSignCertPrivateKey());
	// }
	//
	// public static String encryptPan(String pan, String encoding) {
	// return SecureUtil.EncryptData(pan, encoding,
	// CertUtil.getEncryptCertPublicKey());
	// }
	//
	// public static String decryptPan(String base64Pan, String encoding) {
	// return SecureUtil.DecryptedData(base64Pan, encoding,
	// CertUtil.getSignCertPrivateKey());
	// }
	//
	// public static String encryptTrack(String trackData, String encoding) {
	// return SecureUtil.EncryptData(trackData, encoding,
	// CertUtil.getEncryptTrackCertPublicKey());
	// }

	public static boolean isEmpty(String s) {
		return (null == s) || ("".equals(s.trim()));
	}

	public static String generateTxnTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static String generateOrderId() {
		StringBuilder sb = new StringBuilder();
		int len = random.nextInt(18);
		for (int i = 0; i < len; i++) {
			sb.append(letter[i]);
		}
		return generateTxnTime() + sb.toString();
	}

	public static String createAutoSubmitForm(String url,
			Map<String, String> data) {
		StringBuffer sf = new StringBuffer();
		sf.append("<form id = \"sform\" action=\"" + url
				+ "\" method=\"post\">");
		if ((null != data) && (0 != data.size())) {
			Set<Map.Entry<String, String>> set = data.entrySet();
			Iterator<Map.Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.getElementById(\"sform\").submit();\n");
		sf.append("</script>");
		return sf.toString();
	}

	public static boolean sign4Contract(Map<String, String> data,
			String encoding) {
		if (isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		// data.put("certId", CertUtil.getSignCertId());

		String stringData = coverMap2String4Contract(data, true);

		// byte[] byteSign = null;
		String stringSign = null;
		try {
			// byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
			// byteSign =
			// SecureUtil.base64Encode(SecureUtil.signBySoft(CertUtil.getSignCertPrivateKey(),
			// signDigest));
			byte[] signDigest = SecureUtil.md5X16(stringData, encoding);

			stringSign = new String(signDigest);
			// stringSign = SecureUtil.Hex2Str(signDigest);
			data.put("signature", stringSign);
			return true;
		} catch (Exception e) {
			// LogUtil.writeErrorLog("签名异常", e);
		}
		return false;
	}

	// public static boolean validate(Map<String, String> resData, String
	// encoding) {
	// // LogUtil.writeLog("验签处理开始.");
	// if (isEmpty(encoding)) {
	// encoding = "UTF-8";
	// }
	// String stringSign = (String) resData.get("signature");
	//
	// String certId = (String) resData.get("certId");
	//
	// String stringData = coverMap2String4Contract(resData, true);
	// try {
	// return SecureUtil.validateSignBySoft(CertUtil.getValidateKey(certId),
	// SecureUtil.base64Decode(stringSign.getBytes(encoding)),
	// SecureUtil.sha1X16(stringData, encoding));
	// } catch (UnsupportedEncodingException e) {
	// // LogUtil.writeErrorLog(e.getMessage(), e);
	// } catch (Exception e) {
	// // LogUtil.writeErrorLog(e.getMessage(), e);
	// }
	// return false;
	// }

	// TODO 存在signMethod也不参与签名的情况
	public static String coverMap2String4Contract(Map<String, String> data,
			boolean excludeSignMethod) {
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			if (!"signature".equals(en.getKey().trim())) {
				tree.put(en.getKey(), en.getValue());
			}
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			sf.append(en.getKey() + "=" + en.getValue() + "&");
		}
		return sf.substring(0, sf.length() - 1);
	}

	public static String coverMap2StringQuick(Map<String, String> data) {
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			// if (!"signature".equals(((String) en.getKey()).trim())) {
			tree.put(en.getKey(), en.getValue());
			// }
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			sf.append(en.getKey() + "=" + en.getValue() + "&");
		}
		return sf.substring(0, sf.length() - 1);
	}

	public static void main(String[] args) {
		// System.out.println(encryptTrack("12", "utf-8"));
	}
}
