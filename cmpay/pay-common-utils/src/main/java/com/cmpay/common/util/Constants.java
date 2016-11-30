package com.cmpay.common.util;

public class Constants {


	//定义统一返回码
	   public static String SUCCESS_CODE="0000";
	   public static String SUCCESS_MSG="交易成功";
	   public static String FAILED_CODE="9901";
	   public static String FAILED_MSG="交易失败";
	   public static String EXCEPTION_CODE="9999";
	   public static String EXCEPTION_MSG="系统异常";
	   public static String PROCESS_CODE="8801";
	   public static String PROCESS_MSG="处理中";
	   //参数相关
	   public static String PARA_ERROR_CODE="8901";
	   public static String PARA_ERROR_MSG="参数异常";
	   public static String PARA_ERROR_8902_CODE="8902";
	   public static String PARA_ERROR_8902_MSG="不支持的认证渠道";
	   public static String PARA_ERROR_8903_CODE="8903";
	   public static String PARA_ERROR_8903_MSG="不支持的证件类型";
	   public static String PARA_ERROR_8904_CODE="8904";
	   public static String PARA_ERROR_8904_MSG="不支持的卡类型";
	   //业务相关
	   public static String TRADE_ERROR_8801_CODE="8801";
	   public static String TRADE_ERROR_8801_MSG="不支持的支付渠道";
	   public static String TRADE_ERROR_8802_CODE="8802";
	   public static String TRADE_ERROR_8802_MSG="认证渠道已关闭";
	   public static String TRADE_ERROR_8803_CODE="8803";
	   public static String TRADE_ERROR_8803_MSG="支付渠道不支持该银行";
	   public static String TRADE_ERROR_8804_CODE="8804";
	   public static String TRADE_ERROR_8804_MSG="支付渠道该银行已关闭";
	   public static String TRADE_ERROR_8805_CODE="8805";
	   public static String TRADE_ERROR_8805_MSG="非法的渠道";

	 //定义统一返回key
	   public static String CODE_KEY="code";
	   public static String MSG_KEY="msg";

	   public static String ON="ON";
	   public static String OFF="OFF";


    public static final String DES_KEY_ALGORITHM="DESede";
    public static final String DES_CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	public static final String key="efiKLwiMTK0CAu89pKI95ThPDi8HZgkk";
	public static final String UTF8="UTF-8";

    //RSA数字签名
    public static final String ENCODING="UTF-8";
    public static final String RSA_KEY_ALGORITHM="RSA";
    public static final String SIGNATURE_ALGORITHM="MD5withRSA";
    public static final String RSA_PUBLIC_KEY="CMRSAPublicKey";
    public static final String RSA_PRIVATE_KEY="CMRSAPrivateKey";
    public static final int RSA_KEY_SIZE=512;


    public static final String PUBKEY="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAI9uYbb3LDRvOQI1zbei2Doig7xfOmEmj8DvJNAG5xFHjbaXvieyOxM8Gs9bsg6Sqol61dSCnlLT8xNiFpIXJwsCAwEAAQ==";
    public static final String PRIKEY="MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAj25htvcsNG85AjXNt6LYOiKDvF86YSaPwO8k0AbnEUeNtpe+J7I7Ezwaz1uyDpKqiXrV1IKeUtPzE2IWkhcnCwIDAQABAkA50KOgrddqt16Cbo+iswh3vovddYFBwdp2Sa9MD0w64UKha/IF50pDURzdrg+GK8uc+OlzqFuOEPMrbH8VnRNRAiEA0w8tWT+YFyCgF93ec5jbQy8gB0qjjrordK/idhYuVtMCIQCt+M1t2ke0YdOHJXEjZqxeOf4GDR1eyOw9Q2pwIsq76QIhALuFXJFTQ1opDpSl+CNUyFsk35wa4L7LAhkmvFqW0o0pAiBlsYs7zJ2flpOUa1GQNQNK7TFNDjt93YsiYJcGR4AkiQIgdPhkKsyDXOudv0WTu2JO65HRoSh0OhsIKia1e9Z46e4=";


}
