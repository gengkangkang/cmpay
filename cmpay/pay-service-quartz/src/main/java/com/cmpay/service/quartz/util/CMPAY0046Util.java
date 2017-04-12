package com.cmpay.service.quartz.util;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.HttpClientUtil;
import com.cmpay.service.quartz.model.CMPAY0046;
import com.cmpay.service.quartz.model.CMPAY0046Rs;
import com.cmpay.service.quartz.model.CommonRqHdr;
import com.cmpay.service.quartz.model.CommonRsHdr;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 退款
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月9日 下午2:58:50
 *
 */
public class CMPAY0046Util {

	   public static String toXML(CMPAY0046 cmpay0046) {

	        XStream xstream = new XStream(new DomDriver("UTF-8"));
	        xstream.processAnnotations(cmpay0046.getClass());
	        String xml=xstream.toXML(cmpay0046);
	        return AcctConstant.xmlhead+xml+AcctConstant.xmltail;
	    }

	   public static CMPAY0046Rs fromXML(String xml) {
	        try {
	        	CMPAY0046Rs core =new CMPAY0046Rs();
	            Document doc=DocumentHelper.parseText(xml);
	            Element rootElt = doc.getRootElement();
	            Iterator it_cmpay0046rs=rootElt.elementIterator("CMAPI0046Rs");
	            Element  el_cmpay0046rs =(Element)it_cmpay0046rs.next();

	            CommonRsHdr rs=new CommonRsHdr();
	            Iterator it_rs=el_cmpay0046rs.elementIterator("CommonRsHdr");
	            Element el_rs=(Element)it_rs.next();
	            String StatusCode=el_rs.elementTextTrim("StatusCode");
	            rs.setStatusCode(StatusCode);
	            String ServerStatusCode=el_rs.elementTextTrim("ServerStatusCode");
	            rs.setServerStatusCode(ServerStatusCode);
	            String SPRsUID=el_rs.elementTextTrim("SPRsUID");
	            rs.setSPRsUID(SPRsUID);
	            String RqUID=el_rs.elementTextTrim("RqUID");
	            rs.setRqUID(RqUID);
	            core.setCommonRsHdr(rs);
	            return core;
	        } catch (Exception ex) {
	            System.out.println("解析xml异常");
	            ex.printStackTrace();
	        }
	        return null;
	    }

	   public static void main(String[] args){
	        CommonRqHdr com=new CommonRqHdr();
	        com.setRqUID(CmpayUtils.getUUID());
	        com.setSPName(AcctConstant.spName);
	        com.setNumTranCode(AcctConstant.NumTranCode);
	        com.setClearDate(CmpayUtils.getCurrentTime("yyyyMMdd"));
	        com.setTranDate(CmpayUtils.getCurrentTime("yyyyMMdd"));
	        com.setTranTime(CmpayUtils.getCurrentTime("HHmmss"));
	        com.setChannelId(AcctConstant.channelId);
	        com.setVersion(AcctConstant.version);


	        CMPAY0046 pay=new CMPAY0046();
	        pay.setCommonRqHdr(com);
           pay.setUserId("66666669");
           pay.setAmount("1");

	        String xml=toXML(pay);
	        System.out.println("xml===="+xml);

	        String back = null;
			try {
				back = HttpClientUtil.postString(AcctConstant.coreurl, xml, AcctConstant.UTF8, AcctConstant.UTF8);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("back===="+back);


	        CMPAY0046Rs core=fromXML(back);
	        System.out.println("core========="+core.toString());
	    }


}
