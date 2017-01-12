package com.cmpay.common.util;

public class Constants {

	public static long PERIOD=24*60*60*1000;


	//定义统一返回码
	   public static String SUCCESS_CODE="0000";
	   public static String SUCCESS_MSG="交易成功";
	   public static String FAILED_CODE="9901";
	   public static String FAILED_MSG="交易失败";
	   public static String EXCEPTION_CODE="9999";
	   public static String EXCEPTION_MSG="系统异常";
	   public static String PROCESS_CODE="1001";
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
	   public static String PARA_ERROR_8905_CODE="8905";
	   public static String PARA_ERROR_8905_MSG="缺少必要的参数";
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

	   public static String TRADE_ERROR_8806_CODE="8806";
	   public static String TRADE_ERROR_8806_MSG="商户缺少配置信息";
	   public static String TRADE_ERROR_8807_CODE="8807";
	   public static String TRADE_ERROR_8807_MSG="商户缺少interacctno配置信息";
	   public static String TRADE_ERROR_8808_CODE="8808";
	   public static String TRADE_ERROR_8808_MSG="支付渠道无响应信息";
	   public static String TRADE_ERROR_8809_CODE="8809";
	   public static String TRADE_ERROR_8809_MSG="不支持的交易类型";
	   public static String TRADE_ERROR_8810_CODE="8810";
	   public static String TRADE_ERROR_8810_MSG="不支持的证件类型";
	   public static String TRADE_ERROR_8811_CODE="8811";
	   public static String TRADE_ERROR_8811_MSG="商户不支持所选的支付渠道";
	   public static String TRADE_ERROR_8812_CODE="8812";
	   public static String TRADE_ERROR_8812_MSG="该支付渠道已关闭";
	   public static String TRADE_ERROR_8813_CODE="8813";
	   public static String TRADE_ERROR_8813_MSG="不支持该银行";
	   public static String TRADE_ERROR_8814_CODE="8814";
	   public static String TRADE_ERROR_8814_MSG="订单号已存在，请勿重复下单";
	   public static String TRADE_ERROR_8815_CODE="8815";
	   public static String TRADE_ERROR_8815_MSG="商户无支付渠道可用";
	   public static String TRADE_ERROR_8816_CODE="8816";
	   public static String TRADE_ERROR_8816_MSG="银行关闭或订单金额超出限制";
	   public static String TRADE_ERROR_8817_CODE="8817";
	   public static String TRADE_ERROR_8817_MSG="签名验证失败";

	   public static String TRADE_ERROR_88187_CODE="8818";
	   public static String TRADE_ERROR_8818_MSG="订单超时，系统自动设置为失败";

	   public static String TRADE_ERROR_8819_CODE="8819";
	   public static String TRADE_ERROR_8819_MSG="不支持该银行卡";
	   public static String TRADE_ERROR_8820_CODE="8820";
	   public static String TRADE_ERROR_8820_MSG="只支持借记卡";

	   public static String TRADE_ERROR_8821_CODE="8821";
	   public static String TRADE_ERROR_8821_MSG="无此订单信息";
	   public static String TRADE_ERROR_8822_CODE="8822";
	   public static String TRADE_ERROR_8822_MSG="该订单不符合退款条件";
	   public static String TRADE_ERROR_8823_CODE="8823";
	   public static String TRADE_ERROR_8823_MSG="退款金额不能大于原订单金额";
	   public static String TRADE_ERROR_8824_CODE="8824";
	   public static String TRADE_ERROR_8824_MSG="退款申请人和订单用户不一致";


	   public static String TRADE_ERROR_C9999_CODE="C9999";
	   public static String TRADE_ERROR_C9999_MSG="未知错误";

	 //定义统一返回key
	   public static String CODE_KEY="code";
	   public static String MSG_KEY="msg";
	   public static String RESPCODE_KEY="respCode";
	   public static String RESPMSG_KEY="respMsg";
	   public static String PAYCODE_KEY="payCode";
	   public static String PAYMSG_KEY="payMsg";
	   public static String PAYSTATUS_KEY="payStatus";
	   public static String PAYDATA_KEY="data";
	   public static String SIGN_KEY="sign";



		public static String WXPAY="10";
		public static String KJPAY="20";
		public static String ALIPAY="30";


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
