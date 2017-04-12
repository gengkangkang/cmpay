package com.cmpay.service.quartz.util;

import java.util.UUID;

import com.cmpay.common.util.HttpClientUtil;

/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年3月14日 上午9:49:17
 *
 */
public class HttpTest {

	public static void main(String[] args) {

		String str="<CMCORE xmlns=\"http://www.cm-inv.com/CMINV/2015/10\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.cm-inv.com/CMINV/2015/10 CMCORE1.0.xsd\"><CMAPI0004Rq><CommonRqHdr><RqUID>";

		String str1="</RqUID><SPName>i投</SPName><NumTranCode>666</NumTranCode><ClearDate>20160518</ClearDate><TranDate>20160518</TranDate><TranTime>174212</TranTime><ChannelId>0000</ChannelId><CntId>222</CntId></CommonRqHdr><userId>1c3276bf95e2406bb7085bec504ce26e</userId><merchantNo>S0004</merchantNo><amount>66.66</amount><channelOrderNo>";

		String str2="</channelOrderNo></CMAPI0004Rq></CMCORE>";
		StringBuffer sb=null;

       for(int i=0;i<6000;i++){

		String uuid=UUID.randomUUID().toString();
		 sb=new StringBuffer();

		String xml=sb.append(str).append(uuid).append(str1).append(uuid).append(str2).toString();
		System.out.println("[Test["+i+"]]待发送报文："+xml);
	       String back = null;
				try {
					back = HttpClientUtil.postString(AcctConstant.coreurl, xml, AcctConstant.UTF8, AcctConstant.UTF8);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println("[Test["+i+"]]back===="+back);

//		System.out.println(uuid);
	}
	}
}
