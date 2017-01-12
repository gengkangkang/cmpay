package com.cmpay.service.quartz.util;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cmpay.common.util.CmpayUtils;
import com.cmpay.common.util.HttpClientUtil;
import com.cmpay.service.quartz.model.CMAPI0045;
import com.cmpay.service.quartz.model.CMAPI0045Rs;
import com.cmpay.service.quartz.model.CommonRqHdr;
import com.cmpay.service.quartz.model.CommonRsHdr;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2017年1月11日 下午4:34:42
 *
 */
public class CMAPI0045Util {

	   public static String toXML(CMAPI0045 cmpai0045) {

	        XStream xstream = new XStream(new DomDriver("UTF-8"));
	        xstream.processAnnotations(cmpai0045.getClass());
	        String xml=xstream.toXML(cmpai0045);
	        return AcctConstant.xmlhead+xml+AcctConstant.xmltail;
	    }

	   public static CMAPI0045Rs fromXML(String xml) {
	        try {
	        	CMAPI0045Rs core =new CMAPI0045Rs();
	            Document doc=DocumentHelper.parseText(xml);
	            Element rootElt = doc.getRootElement();
	            Iterator it_cmpay0045rs=rootElt.elementIterator("CMAPI0045Rs");
	            Element  el_cmpay0045rs =(Element)it_cmpay0045rs.next();

	            CommonRsHdr rs=new CommonRsHdr();
	            Iterator it_rs=el_cmpay0045rs.elementIterator("CommonRsHdr");
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


	        CMAPI0045 pay=new CMAPI0045();
	        pay.setCommonRqHdr(com);
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


	        CMAPI0045Rs core=fromXML(back);
	        System.out.println("core========="+core.toString());
	    }


}
